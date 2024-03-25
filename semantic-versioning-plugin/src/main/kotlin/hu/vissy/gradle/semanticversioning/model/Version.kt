package hu.vissy.gradle.semanticversioning.model

import com.google.gson.JsonObject
import hu.vissy.gradle.semanticversioning.SemanticVersioningException
import hu.vissy.gradle.semanticversioning.util.jsonObject

data class Version(val prefix: String, val major: Int, val minor: Int, val patch: Int, val info: String = "") : Comparable<Version> {
    constructor(old: Version, prefix: String = old.prefix, major: Int = old.major, minor: Int = old.minor, patch: Int = old.patch, info: String = old.info) :
            this(prefix, major, minor, patch, info)

    companion object {

        const val JSON_NEW_VERSION = "newVersion"

        fun fromString(versionString: String, prefix: String): Version {
            var vs = versionString
            if (vs.startsWith(prefix)) vs = vs.substring(prefix.length)
            var info = ""
            if (vs.contains("-")) {
                info = vs.substringAfter("-")
                vs = vs.substringBefore("-")
            }
            val ss = vs.split(".")
            if (ss.size != 3) throw SemanticVersioningException("Malformatted version: $versionString")
            return Version(prefix, Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]), info)
        }

        fun fromJson(json: JsonObject, prefix: String): Version {
            return fromString(json[JSON_NEW_VERSION].asString, prefix)
        }
    }

    fun incPatch() = Version(this, patch = patch + 1)
    fun incMinor() = Version(this, minor = minor + 1)
    fun incMajor() = Version( this, major = major + 1)

    fun updateInfo(info: String) = Version(this, info = info)

    val versionSequence = "$major.$minor.$patch"+ if (info.isNotBlank()) "-$info" else ""
    val releaseString = "$prefix$versionSequence"

    override fun toString() = releaseString
    fun toJson(root: JsonObject) = jsonObject(root) {
        JSON_NEW_VERSION += releaseString
    }

    override fun compareTo(other: Version): Int {
        if (major != other.major) return major - other.major
        if (minor != other.minor) return minor - other.minor
        return patch - other.patch
    }
}