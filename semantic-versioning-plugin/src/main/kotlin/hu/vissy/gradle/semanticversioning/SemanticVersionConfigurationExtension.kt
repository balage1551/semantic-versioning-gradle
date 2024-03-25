package hu.vissy.gradle.semanticversioning

import java.io.Serializable

enum class GitLogEntryTypes : Serializable {
    BREAKING,
    NEW_FEATURE,
    BUGFIXES
}

@Suppress("DuplicatedCode")
open class LogEntryPrefixes {
    /** Prefixes used for detecting bugfix notes in commit messages. Default: &#91;`fix]` and `%` */
    val bugfix = mutableSetOf<String>()
    /** Prefixes used for detecting new feature notes in commit messages. Default: &#91;`new]` and `+` */
    val newFeature = mutableSetOf<String>()
    /** Prefixes used for detecting compatibility breaking change notes in commit messages. Default: &#91;`breaking]` and `!` */
    val breaking = mutableSetOf<String>()
    /** Whether the check for prefixes handled case-insensitive mode. */
    val caseInsensitive = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogEntryPrefixes

        if (bugfix != other.bugfix) return false
        if (newFeature != other.newFeature) return false
        if (breaking != other.breaking) return false
        if (caseInsensitive != other.caseInsensitive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bugfix.hashCode()
        result = 31 * result + newFeature.hashCode()
        result = 31 * result + breaking.hashCode()
        result = 31 * result + caseInsensitive.hashCode()
        return result
    }


}

@Suppress("unused", "DuplicatedCode")
open class SemanticVersionConfigurationExtension {

    /** Ignore state check and execute the tasks each time. Default is false. */
    var forced = false

    /** The branches on which a new release could be created. Default is `[main, master]` */
    var acceptedBranches = mutableSetOf<String>()

    /** The tag prefix used for marking version data. Default is `release`, meaning a release
     * tag will look like `release-1.2.5` */
    var releaseTagPrefix: String? = "release"

    /** The prefixes used to detect and categorize commit message entries. */
    val logEntryPrefixes = LogEntryPrefixes()

    /** Allow release when there are uncommitted changes. Default is false. */
    var allowDirtyLocal = false

    /** Initial version used when no version tag is available. Should match the `major.minor.patch` format. */
    var initialVersion = "1.0.0"

    /** Optional postfix appended to the version name, like -RC1 ör -SNAPSHOT. Default is empty. */
    var versionSuffix = ""

    /** Whether commitVersion task should automatically push the created release tag */
    var pushTag = false

    /** The version to be used instead of the calculated one. Use with caution! */
    var overrideVersion = ""

    /** The prefixes used to detect and categorize commit message entries. */
    fun logPrefixes(op: LogEntryPrefixes.() -> Unit) {
        logEntryPrefixes.apply(op)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SemanticVersionConfigurationExtension

        if (forced != other.forced) return false
        if (acceptedBranches != other.acceptedBranches) return false
        if (releaseTagPrefix != other.releaseTagPrefix) return false
        if (logEntryPrefixes != other.logEntryPrefixes) return false
        if (allowDirtyLocal != other.allowDirtyLocal) return false
        if (initialVersion != other.initialVersion) return false
        if (versionSuffix != other.versionSuffix) return false
        if (overrideVersion != other.overrideVersion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = forced.hashCode()
        result = 31 * result + acceptedBranches.hashCode()
        result = 31 * result + (releaseTagPrefix?.hashCode() ?: 0)
        result = 31 * result + logEntryPrefixes.hashCode()
        result = 31 * result + allowDirtyLocal.hashCode()
        result = 31 * result + initialVersion.hashCode()
        result = 31 * result + versionSuffix.hashCode()
        result = 31 * result + overrideVersion.hashCode()
        return result
    }


}


