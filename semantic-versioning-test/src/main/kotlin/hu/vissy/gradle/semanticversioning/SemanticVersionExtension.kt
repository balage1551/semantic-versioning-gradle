package hu.vissy.gradle.semanticversioning

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import java.time.format.DateTimeFormatterBuilder

enum class GitLogEntryTypes {
    INCOMPABILITIES,
    NEW_FEATURE,
    BUGFIXES
}

enum class GitState {
    VALID,
    BEHIND,
    AHEAD,
    INVALID_BRANCH
}

abstract class SemanticVersionExtension {

//    internal val rootDir = project.rootDir
//    internal val logger = project.logger

    val logger: Logger = LoggerFactory.getLogger("FooPlugin")


    var acceptedBranches = mutableSetOf("master")

    var releaseTagPrefix: String = "release"

    val logEntryPrefixes = mutableMapOf(
            "!" to GitLogEntryTypes.INCOMPABILITIES,
            "+" to GitLogEntryTypes.NEW_FEATURE,
            "%" to GitLogEntryTypes.BUGFIXES)

    val logEntryLabels = mutableMapOf(
            GitLogEntryTypes.INCOMPABILITIES to "Backward incompatibilities",
            GitLogEntryTypes.NEW_FEATURE to "New features",
            GitLogEntryTypes.BUGFIXES to "Bugfixes")

    var allowDirtyLocal = false


    var releaseNotesFile: String? = null
        set(value) {
            field = value
            if (value == null)
                releaseNotesHtmlFile = null
            else
                releaseNotesHtmlFile = value.replaceAfterLast(".", "html")
        }

    var releaseNotesHtmlFile: String? = null
        private set

    var versionFile: String? = "version.txt"

    var internalReleaseLabel = "*Internal release*"
    var keepInternalReleases = false
    var releaseTimeFormatter: DateTimeFormatter = DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(ISO_LOCAL_TIME)
            .toFormatter()

//    val state: SemanticVersionState by lazy {
//        SemanticVersionState(project)
//    } //? = null
//
//    val hasNewVersion by lazy { state.newVersion != state.currentVersion }

}


