package hu.vissy.gradle.semanticversioning.model

import com.google.gson.JsonObject
import hu.vissy.gradle.semanticversioning.GitLogEntryTypes
import hu.vissy.gradle.semanticversioning.LogEntryPrefixes
import hu.vissy.gradle.semanticversioning.util.jsonArray
import hu.vissy.gradle.semanticversioning.util.jsonObject

class CommitInfo(val messages: Map<GitLogEntryTypes, List<String>>) {

    fun toJson(root: JsonObject) = jsonObject(root) {
        JSON_MESSAGES += jsonObject {
            messages.entries.forEach { (k, v) ->
                k.name += jsonArray(v)
            }
        }
    }

    val statistics = messages.entries.associate { it.key to it.value.size }

    companion object {
        const val JSON_MESSAGES = "messages"

        fun fromList(commitMessages: List<String>, prefixes: LogEntryPrefixes) : CommitInfo {
            val messages: Map<GitLogEntryTypes, MutableList<String>> = GitLogEntryTypes.entries.associateWith { mutableListOf() }

                commitMessages.forEach { m ->
                    var trimmed = checkAndTrim(m, prefixes.breaking, prefixes.caseInsensitive)
                    if (trimmed != null) messages.getValue(GitLogEntryTypes.BREAKING) += trimmed

                    trimmed = checkAndTrim(m, prefixes.newFeature, prefixes.caseInsensitive)
                    if (trimmed != null) messages.getValue(GitLogEntryTypes.NEW_FEATURE) += trimmed

                    trimmed = checkAndTrim(m, prefixes.bugfix, prefixes.caseInsensitive)
                    if (trimmed != null) messages.getValue(GitLogEntryTypes.BUGFIXES) += trimmed
                }
            return CommitInfo(messages)
        }

        private fun checkAndTrim(message: String, prefixes: Set<String>, caseInsensitive: Boolean): String? {
            val checkTarget = if (caseInsensitive) message.lowercase() else message
            prefixes.forEach { prefix ->
                val p = if (caseInsensitive) prefix.lowercase() else prefix
                if (checkTarget.trim().startsWith(p)) {
                    val i = checkTarget.indexOf(p)+ p.length
                    return message.substring(i)
                }
            }
            return null
        }

        fun fromJson(json: JsonObject) : CommitInfo {
            val messages: Map<GitLogEntryTypes, MutableList<String>> = GitLogEntryTypes.entries.associateWith { mutableListOf() }

            val root = json[JSON_MESSAGES].asJsonObject
            root.keySet().forEach { k ->
                val key = GitLogEntryTypes.valueOf(k)
                val list = messages.getValue(key)
                val source = root.getAsJsonArray(k)
                source.forEach { m -> list.add(m.asString) }
            }

            return CommitInfo(messages)
        }

    }
}