package hu.vissy.gradle.semanticversioning.task

import hu.vissy.gradle.semanticversioning.GROUP_NAME
import hu.vissy.gradle.semanticversioning.SemanticVersionConfigurationExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal

open class SemanticVersioningTask : DefaultTask() {
    @Internal
    val config: SemanticVersionConfigurationExtension = project.extensions.getByType(SemanticVersionConfigurationExtension::class.java)

    init {
        group = GROUP_NAME
    }

}