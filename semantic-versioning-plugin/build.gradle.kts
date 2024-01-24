plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    id("com.gradle.plugin-publish") version "1.2.1"
    signing
    kotlin("jvm") version "1.9.22"
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies
    mavenCentral()
}

version="0.9"
group="hu.vissy.gradle"

dependencies {
    // Use JUnit test framework for unit tests
    api("com.google.code.gson:gson:2.10.1")
    testImplementation("junit:junit:4.13.1")
    implementation("com.github.sya-ri:kgit:1.0.6")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val signingKeyId = project.findProperty("signing.keyId")


gradlePlugin {
    website.set("https://bitbucket.org/balage42/semantic-versioning-gradle")
    vcsUrl.set("https://bitbucket.org/balage42/semantic-versioning-gradle")
    plugins {
        create("semanticVersioningPlugin") {
            id="hu.vissy.gradle.semanticVersioning"
            implementationClass = "hu.vissy.gradle.semanticversioning.SemanticVersioningPlugin"
            displayName = "Semantic Versioning plugin"
            description = "Generate and update versioning based on specially tagged git commit message lines. " +
                    "Optionally generates change logs, too."
            tags.set(listOf("semantic versioning"))
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri(rootDir.resolve("local-plugin-repository"))
            name="localPluginRepository"
        }
    }
}

// Add a source set and a task for a functional test suite
//val functionalTest by sourceSets.creating
//gradlePlugin.testSourceSets(functionalTest)
//
//configurations[functionalTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
//
//val functionalTestTask = tasks.register<Test>("functionalTest") {
//    testClassesDirs = functionalTest.output.classesDirs
//    classpath = configurations[functionalTest.runtimeClasspathConfigurationName] + functionalTest.output
//}
//
//tasks.check {
//    // Run the functional tests as part of `check`
//    dependsOn(functionalTestTask)
//}
