package hu.vissy.gradle.semanticversioning.task

import hu.vissy.gradle.semanticversioning.GitLogEntryTypes
import hu.vissy.gradle.semanticversioning.SemanticVersioningException
import hu.vissy.gradle.semanticversioning.model.CommitInfo
import hu.vissy.gradle.semanticversioning.model.Version
import hu.vissy.gradle.semanticversioning.util.GitTools
import hu.vissy.gradle.semanticversioning.util.jsonObject
import hu.vissy.gradle.semanticversioning.util.toPretty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.util.regex.Pattern


abstract class InfoTask : SemanticVersioningTask() {

    //    @get:Input
    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    init {
        description = "Collects info from git, but does not alter anything."
        @Suppress("LeakingThis")
        outputFile.convention(project.layout.buildDirectory.file("versionState.json"))
    }

    @TaskAction
    fun action() {

        logger.info("Branches:   ${config.acceptedBranches}")
        logger.info("Dirty:      ${config.allowDirtyLocal}")
        logger.info("Tag prefix: ${config.releaseTagPrefix}")
        logger.info("Log prefix (${config.logEntryPrefixes.caseInsensitive}):")
        logger.info("     BUG: ${config.logEntryPrefixes.bugfix.joinToString()}")
        logger.info("     NEW: ${config.logEntryPrefixes.newFeature.joinToString()}")
        logger.info("     INC: ${config.logEntryPrefixes.breaking.joinToString()}")

        val git = GitTools(project.rootProject.rootDir)
        logger.lifecycle("Current branch: " + git.getCurrentBranch())

        if (!config.acceptedBranches.contains(git.getCurrentBranch())) {
            throw SemanticVersioningException("Creating release on current branch (${git.getCurrentBranch()}) is not allowed.")
        }
        if (!config.allowDirtyLocal && git.isDirty()) {
            throw SemanticVersioningException("There are uncommitted changes on the branch.")
        }

        val tagRegex = "refs/tags/${Pattern.quote(config.releaseTagPrefix)}[0-9]+\\.[0-9]+\\.[0-9]+(-.*)?".toRegex()
        val versionTags = git.getTags { it.name.matches(tagRegex) }

        val lastTag = versionTags.lastOrNull()
        val lastVersion: Version =
            if (lastTag == null) {
                Version.fromString("${config.releaseTagPrefix}${config.initialVersion}", config.releaseTagPrefix!!)
            } else {
                Version.fromString(lastTag, config.releaseTagPrefix ?: "")
            }
        logger.info("Last version: ${lastVersion.versionSequence}")
        logger.lifecycle("Last version string: ${lastVersion.releaseString}")

        var newVersion = Version(lastVersion, info = config.versionSuffix)

        val commits = git.getCommitsFrom(lastTag)
        logger.info("Commits:\n" + commits.joinToString("\n   ", "   "))
        val commitInfo = CommitInfo.fromList(commits, config.logEntryPrefixes)
        logger.info("Stat: " + commitInfo.statistics)

        newVersion = when {
            commitInfo.statistics.getValue(GitLogEntryTypes.BREAKING) > 0 -> {
                newVersion.incMajor()
            }
            commitInfo.statistics.getValue(GitLogEntryTypes.NEW_FEATURE) > 0 -> {
                newVersion.incMinor()
            }
            else -> {
                newVersion.incPatch()
            }
        }

        logger.quiet("New version string: ${newVersion.releaseString}")

        val data = jsonObject {}
        newVersion.toJson(data)
        commitInfo.toJson(data)
        outputFile.get().asFile.writeText(data.toPretty())

        project.rootProject.version = newVersion.versionSequence
    }
}

