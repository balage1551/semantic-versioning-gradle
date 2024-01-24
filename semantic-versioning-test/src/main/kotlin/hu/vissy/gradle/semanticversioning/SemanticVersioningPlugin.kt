package hu.vissy.gradle.semanticversioning

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

class SemanticVersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("semanticVersion", SemanticVersionExtension::class.java)

        project.tasks.register("versionInfo", InfoTask::class.java)
//                create("showReleaseNotes", ReleaseNotesTask::class.java) { group = TASK_GROUP }
//                create("updateReleaseNotes", UpdateReleaseNotes::class.java) { group = TASK_GROUP }
//                create("updateVersion", UpdateVersioning::class.java) { group = TASK_GROUP }
//                create("release", ReleaseTask::class.java) { group = TASK_GROUP }
//                create("closeVersion", CloseVersioningTask::class.java) { group = "releasing" }
//            }
    }
}


open class SemanticVersioningTask : DefaultTask() {

    init {
        group = "semantic versioning"
    }

//    @Internal
//    protected val ext = project.extensions["semanticVersion"] as SemanticVersionExtension
//
//    @Internal
//    protected val state: SemanticVersionState = ext.state
}

open class InfoTask : SemanticVersioningTask() {

    @Suppress("unused")
    @TaskAction
    fun action() {
//        logger.lifecycle("Current branch: {} ({})", state.currentBranch, if (state.isBranchAccepted) "accepted" else "rejected")
//        logger.lifecycle("Git state: {} -> {}", state.gitState, when (state.gitState) {
//            GitState.VALID -> "releasing is possible"
//            GitState.BEHIND -> "local branch is behind remote"
//            GitState.AHEAD -> "local repo is dirty (there are uncommitted changes)"
//            GitState.INVALID_BRANCH -> "creating release from current branch (${state.currentBranch}) is prohibited"
//        })
//        logger.lifecycle("Current version: {} ({})", state.currentVersion.releaseString, state.currentReleaseHash
//            ?: "<no hash>")
//        logger.lifecycle("Release stats:\n   incompabilities: {}\n   new features:    {}\n   bugfixes:        {}",
//            state.releaseLogs[GitLogEntryTypes.INCOMPABILITIES]?.size,
//            state.releaseLogs[GitLogEntryTypes.NEW_FEATURE]?.size,
//            state.releaseLogs[GitLogEntryTypes.BUGFIXES]?.size)
//        logger.lifecycle("New semantic version: {}", state.newVersion.releaseString)
    }
}

