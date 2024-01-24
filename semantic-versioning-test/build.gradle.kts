plugins {
    kotlin("jvm") version "1.9.22"
    id("hu.vissy.gradle.semanticVersioning") version "0.9"
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit test framework for unit tests
}

semanticVersion {
    forced = true
    allowDirtyLocal = true
    releaseTagPrefix= "test"
    versionPrefix = "RC1"
    logPrefixes {
        bugfix.addAll(listOf("%", "[fix]"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

