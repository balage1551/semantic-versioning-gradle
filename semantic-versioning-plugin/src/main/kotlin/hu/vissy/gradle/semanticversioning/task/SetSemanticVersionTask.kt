package hu.vissy.gradle.semanticversioning.task

import com.google.gson.JsonParser
import hu.vissy.gradle.semanticversioning.model.Version
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction


abstract class SetSemanticVersionTask : SemanticVersioningTask() {

    @get:InputFile
    abstract val versionInfoFile: RegularFileProperty

    init {
        description = "Commits a tag with the new version."
        val infoTask = project.tasks.withType(InfoTask::class.java).singleOrNull()
        if (infoTask != null) {
            @Suppress("LeakingThis")
            versionInfoFile.set(infoTask.outputFile)
        } else {
            logger.warn("InfoTask not found in the project.")
        }
    }

    @TaskAction
    fun action() {
        val file = versionInfoFile.asFile.get()
        val content = file.readText()
        val json = JsonParser.parseString(content).asJsonObject
        val version = Version.fromJson(json, config.releaseTagPrefix!!)
        logger.quiet("New version string: ${version.releaseString}")

        project.version = version.versionSequence
    }
}

