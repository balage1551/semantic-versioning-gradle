package hu.vissy.gradle.semanticversioning.util

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.Status
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import java.util.*

class GitTools(dir: File) {


    val repository: Repository
    private val git: Git

    init {
        val builder = FileRepositoryBuilder()
        repository = builder
            .readEnvironment()
            .findGitDir(dir)
            .setMustExist(true)
            .build()
        git = Git(repository)
    }

    fun getCurrentBranch(): String {
        return repository.branch
    }

    fun isDirty(): Boolean {
        val status: Status = git.status().call()
        return !status.isClean
    }

    fun getTags(prefix: String = "") = getTags { it.name.startsWith("refs/tags/$prefix") }

    fun getTags(filter: (Ref) -> Boolean): List<String> {
        val tags: List<Ref> = git.tagList().call()
        return tags.filter(filter).map { it.name.substring("refs/tags/".length) }
    }

    fun getCommitsFrom(tagName: String?): List<String> {
        val revWalk = RevWalk(repository)
        val head = repository.resolve("HEAD^{commit}") ?: throw IllegalStateException("Repository is empty")
        val headCommit = revWalk.parseCommit(head)
        var stopCommit: RevCommit? = null
        if (tagName != null) {
            val tagRef = repository.exactRef("refs/tags/$tagName")
            val stopCommitObjectId = tagRef?.objectId ?: throw IllegalArgumentException("Tag not found: $tagName")
            stopCommit = revWalk.parseCommit(stopCommitObjectId)
        }

        val commitMessages = LinkedList<String>()
        revWalk.markStart(headCommit)
        for (revCommit in revWalk) {
            if (revCommit.id == stopCommit?.id) break
            revCommit.fullMessage.split("\n").filter { it.isNotBlank() }.reversed().forEach { commitMessages.add(it) }
        }

        revWalk.dispose()
        return commitMessages.reversed()
    }


    fun createTag(tagName: String, tagMessage: String = tagName) {
        git.tag().setName(tagName).setMessage(tagMessage).setForceUpdate(true).call()
    }

    fun createCommit(tagMessage: String) {
        git.commit().setMessage(tagMessage).call()
    }

    fun getLatestCommit(): RevCommit {
        val revWalk = RevWalk(repository)
        val head = repository.resolve(Constants.HEAD)
        return revWalk.parseCommit(head)
    }


}