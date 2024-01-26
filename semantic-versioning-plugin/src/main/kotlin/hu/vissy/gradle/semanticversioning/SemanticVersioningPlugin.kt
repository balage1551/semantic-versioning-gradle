package hu.vissy.gradle.semanticversioning

import hu.vissy.gradle.semanticversioning.task.CommitVersionTask
import hu.vissy.gradle.semanticversioning.task.ConfigureSemanticVersioningTask
import hu.vissy.gradle.semanticversioning.task.InfoTask
import hu.vissy.gradle.semanticversioning.task.SetSemanticVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.time.LocalDateTime

const val GROUP_NAME = "semantic versioning"

class SemanticVersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val configExtension = project.extensions.create("semanticVersion", SemanticVersionConfigurationExtension::class.java)

        val configure = project.tasks.register("configureExtension", ConfigureSemanticVersioningTask::class.java)

        fun addInputs(task: Task) {
            task.inputs.property("configHash", configExtension.hashCode())
            if (configExtension.forced) {
                task.inputs.property("touch", LocalDateTime.now().hashCode())
            }
        }

        val versionInfo = project.tasks.register("versionInfo", InfoTask::class.java) {
            it.dependsOn(configure)
            addInputs(it)
        }

        val setVersion = project.tasks.register("setSemanticVersion", SetSemanticVersionTask::class.java) {
            it.dependsOn(versionInfo)
        }

        val commitVersion = project.tasks.register("commitVersion", CommitVersionTask::class.java) {
            it.dependsOn(setVersion)
            addInputs(it)
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


