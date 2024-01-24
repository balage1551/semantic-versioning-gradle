package hu.vissy.gradle.semanticversioning.task

import hu.vissy.gradle.semanticversioning.SemanticVersioningException
import org.gradle.api.tasks.TaskAction


abstract class ConfigureSemanticVersioningTask : SemanticVersioningTask() {

    init {
        description = "Extends and validates the configuration."
    }

    @TaskAction
    fun action() {
        if (config.acceptedBranches.isEmpty()) {
            config.acceptedBranches.add("master")
            config.acceptedBranches.add("main")
        }

        if (config.logEntryPrefixes.bugfix.isEmpty()) {
            config.logEntryPrefixes.bugfix.add("%")
            config.logEntryPrefixes.bugfix.add("[fix]")
        }

        if (config.logEntryPrefixes.newFeature.isEmpty()) {
            config.logEntryPrefixes.newFeature.add("+")
            config.logEntryPrefixes.newFeature.add("[new]")
        }

        if (config.logEntryPrefixes.incompatibility.isEmpty()) {
            config.logEntryPrefixes.incompatibility.add("!")
            config.logEntryPrefixes.incompatibility.add("[breaking]")
            config.logEntryPrefixes.incompatibility.add("[incompatibility]")
        }

        if (!config.initialVersion.matches("[0-9]+\\.[0-9]+\\.[0-9]+".toRegex()))
            throw SemanticVersioningException("Initial version is not well-formatted: ${config.initialVersion}")

        if (config.releaseTagPrefix == null) {
            config.releaseTagPrefix = ""
        } else if (!config.releaseTagPrefix!!.endsWith("-")) {
            config.releaseTagPrefix += "-"
        }
    }
}
