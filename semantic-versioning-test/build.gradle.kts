plugins {
    kotlin("jvm") version "1.9.22"
    id("hu.vissy.gradle.semanticVersioning") version "0.9"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // Use JUnit test framework for unit tests
}

semanticVersion {
    forced = false
    acceptedBranches.addAll(listOf("master", "main"))
    allowDirtyLocal = true
    releaseTagPrefix= "v"
    versionSuffix = "RC1"
    initialVersion = "1.0.0"
    logPrefixes {
        bugfix.addAll(listOf("%", "[fix]"))
        bugfix.addAll(listOf("%", "[fix]"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}



tasks.named("jar") {
    dependsOn("setSemanticVersion")
    doLast {
        println("++++V: ${project.version}")
    }
}

application {
    mainClass = "hu.vissy.hello.HelloWorldKt"
}
