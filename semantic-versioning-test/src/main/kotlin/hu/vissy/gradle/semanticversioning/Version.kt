package hu.vissy.gradle.semanticversioning

data class Version(val major: Int, val minor: Int, val patch: Int) {
    companion object {
        fun fromString(versionString: String): Version {
            val ss = versionString.split("-")[1].split(".")
            return Version(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2]))
        }
    }

    fun incPatch() = Version(major, minor, patch + 1)
    fun incMinor() = Version(major, minor + 1, 0)
    fun incMajor() = Version(major + 1, 0, 0)

    val versionSequence = "$major.$minor.$patch"
    val releaseString = versionSequence

    override fun toString() = releaseString
}