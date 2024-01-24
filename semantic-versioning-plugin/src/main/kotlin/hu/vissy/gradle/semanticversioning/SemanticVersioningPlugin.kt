package hu.vissy.gradle.semanticversioning

import hu.vissy.gradle.semanticversioning.task.CommitVersionTask
import hu.vissy.gradle.semanticversioning.task.ConfigureSemanticVersioningTask
import hu.vissy.gradle.semanticversioning.task.InfoTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.time.LocalDateTime

const val GROUP_NAME = "semantic versioning"

class SemanticVersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val configExtension = project.extensions.create("semanticVersion", SemanticVersionConfigurationExtension::class.java)
//        val runtimeExtension = project.extensions.create("semanticVersionRuntime", SemanticVersionRuntimeExtension::class.java)

//
        val configure = project.tasks.register("configureExtension", ConfigureSemanticVersioningTask::class.java)


        val versionInfo = project.tasks.register("versionInfo", InfoTask::class.java) {
            it.dependsOn(configure)
            it.inputs.property("configHash", configExtension.hashCode())
            it.inputs.property("touch", LocalDateTime.now().hashCode())
        }

        val commitVersion = project.tasks.register("commitVersion", CommitVersionTask::class.java) {
            it.dependsOn(versionInfo)
            it.inputs.property("configHash", configExtension.hashCode())
            it.inputs.property("touch", LocalDateTime.now().hashCode())
        }

//        val releaseWithVersion = project.tasks.register("releaseWithVersion", ReleaseWithVersionTask::class.java) {
//            it.dependsOn(versionInfo)
//            it.finalizedBy(commitVersion)
//            it.inputs.property("configHash", configExtension.hashCode())
//            it.inputs.property("touch", LocalDateTime.now().hashCode())
//        }


    //                create("showReleaseNotes", ReleaseNotesTask::class.java) { group = TASK_GROUP }
//                create("updateReleaseNotes", UpdateReleaseNotes::class.java) { group = TASK_GROUP }
//                create("updateVersion", UpdateVersioning::class.java) { group = TASK_GROUP }
//                create("release", ReleaseTask::class.java) { group = TASK_GROUP }
//                create("closeVersion", CloseVersioningTask::class.java) { group = "releasing" }
//            }
    }
}


