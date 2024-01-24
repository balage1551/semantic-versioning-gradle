package hu.vissy.gradle.semanticversioning

import org.gradle.api.GradleScriptException
import org.gradle.api.Project
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class SemanticVersionState(project: Project) {

    val ext = project.extensions.getByName("semanticVersion") as SemanticVersionExtension
    val logger = ext.logger

//    val currentVersion: Version by lazy {
//        var output = runExternalCommand("git", "describe", "--tags", "--long", "--match=\"${ext.releaseTagPrefix}-*\"")
//        if (output.isEmpty())
//            Version(1, 0, 0)
//        else {
//            val l = output[0]
//            val p = l.indexOf('-', ext.releaseTagPrefix.length + 2)
//            Version.fromString(if (p == -1) l else l.substring(0, p))
//        }
//    }
//
//    val currentBranch: String by lazy {
//        val branchFilter = Regex("^On branch (.*)")
//        var bs = runExternalCommand("git", "status", "-uno", timeout = 5)[0]
//        branchFilter.find(bs)?.groupValues?.get(1) ?: "<unknown>"
//    }
//
//    val isBranchAccepted: Boolean by lazy {
//        ext.acceptedBranches.contains(currentBranch)
//    }
//
//    val currentReleaseHash by lazy {
//        val output = runExternalCommand("git", "rev-parse", "--verify", currentVersionReleaseTag)
//        val res = if (output.isNotEmpty()) output[0] else null
//        logger.debug("Current release hash: {}", res)
//        res
//    }
//
//    val releaseLogs by lazy {
//        val res = GitLogEntryTypes.values().map { it to mutableListOf<String>() }.toMap()
//
//        val output = runExternalCommand("git", "log", "--pretty=\"%B\"", if (currentReleaseHash != null) "$currentReleaseHash..." else "")
//        output.forEach { ol ->
//            val comment = ol.trim()
//            if (comment.isNotEmpty()) {
//                logger.debug("Commit comment: $comment")
//                ext.logEntryPrefixes.entries.firstOrNull { comment.startsWith(it.key) }?.apply {
//                    res.getValue(this.value).add(comment.substring(this.key.length).trim())
//                }
//            }
//        }
//        res
//    }
//
//    val isInternalRelease by lazy { releaseLogs.values.sumBy { it.size } == 0 }
//
//    val newVersion by lazy {
//        when {
//            releaseLogs.getValue(GitLogEntryTypes.INCOMPABILITIES).isNotEmpty() -> currentVersion.incMajor()
//            releaseLogs.getValue(GitLogEntryTypes.NEW_FEATURE).isNotEmpty() -> currentVersion.incMinor()
//            else -> currentVersion.incPatch()
//        }
//    }
//
//    val hasNewVersion by lazy { newVersion != currentVersion }
//
//    val currentVersionReleaseTag by lazy { "${ext.releaseTagPrefix}-${currentVersion.releaseString}" }
//    val newVersionReleaseTag by lazy { "${ext.releaseTagPrefix}-${newVersion.releaseString}" }
//
//    val gitState by lazy {
//        if (!isBranchAccepted) GitState.INVALID_BRANCH
//        else {
//            val output = runExternalCommand("git", "status", "-uno").joinToString("\n")
//            if (!ext.allowDirtyLocal && !output.contains("nothing to commit")) {
//                GitState.AHEAD
//                //throw GradleException("Local repo is dirty (there are uncommited changes).")
//            } else if (output.contains("branch is behind")) {
//                GitState.BEHIND
////                throw GradleException("Local branch is behind remote.")
//            } else GitState.VALID
//        }
//    }
//
//
//    internal fun runExternalCommand(vararg params: String, timeout: Int = 30): List<String> {
//        val cmd = params.joinToString(separator = " ") { it }
//        logger.debug("External command: ${cmd} (dir: ${ext.rootDir.absolutePath})")
//        try {
//            val p = ProcessBuilder(*params)
//                    .directory(ext.rootDir)
//                    .redirectError(ProcessBuilder.Redirect.PIPE)
//                    .redirectOutput(ProcessBuilder.Redirect.PIPE)
//                    .start()
//            p.waitFor(timeout.toLong(), TimeUnit.SECONDS)
//            val res = p.inputStream.reader(StandardCharsets.UTF_8).buffered().readLines()
//            logger.debug("Result:\n ${res.joinToString("\n")}")
//            return res
//        } catch (e: Exception) {
//            logger.error("Error executing the command: $cmd", e)
//            throw GradleScriptException("Error executing git command: $cmd", e)
//        }
//    }

}