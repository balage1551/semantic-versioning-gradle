package hu.vissy.gradle.semanticversioning

import org.gradle.testkit.runner.GradleRunner
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files

class SemanticVersioningFunctionalTest {
    @Test
    @Throws(IOException::class)
    fun canRunTask() {
        // Setup the test build
        val projectDir = File("build/functionalTest")
        Files.createDirectories(projectDir.toPath())
        writeString(File(projectDir, "settings.gradle"), "")
        writeString(
            File(projectDir, "build.gradle"),
            "plugins {" +
                    "  id('com.example.plugin.greeting')" +
                    "}"
        )

        // Run the build
        val result = GradleRunner.create()
            .forwardOutput()
            .withPluginClasspath()
            .withArguments("greet")
            .withProjectDir(projectDir)
            .build()

        // Verify the result
        Assert.assertTrue(result.output.contains("Hello from plugin 'com.example.plugin.greeting'"))
    }

    @Throws(IOException::class)
    private fun writeString(file: File, string: String) {
        FileWriter(file).use { writer ->
            writer.write(string)
        }
    }
}
