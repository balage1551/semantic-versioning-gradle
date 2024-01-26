# Semantic versioning gradle plugin

This plugin provides automated semantic versioning based on specially prefixed
lines in the commit messages. The plugin handles proper version increment and
marks the versions in git by tags.

## Usage

### Import

See https://plugins.gradle.org/plugin/hu.vissy.gradle.semanticVersioning  

### Configuration

Example:

```kotlin
semanticVersion {
    forced = false
    acceptedBranches.addAll(listOf("master", "main","release"))
    allowDirtyLocal = true
    releaseTagPrefix= "v"
    versionPrefix = "RC1"
    initialVersion = "1.0.0"
    logPrefixes {
        bugfix.addAll(listOf("%", "[fix]"))
        newFeature.addAll(listOf("+", "[new]"))
        breaking.addAll(listOf("!", "[breaking]"))
    }
    pushTag = true
}
```

| Parameter                   |    Type     | Default               | Description                                                                               |
|-----------------------------|:-----------:|:----------------------|:------------------------------------------------------------------------------------------|
| forced                      |   boolean   | false                 | When set, the tasks will execute regardless the input state.                              |
| acceptedBranches            | Set<String> | {master, main}        | On which branches the new releases could be made.                                         |
| allowDirtyLocal             |   boolean   | false                 | Whether release creation is allowed when there are any uncommitted changes.               |
| releaseTagPrefix            |   string    | `"release"`           | The tag prefix used in git tags marking releases.                                         |
| versionSuffix               |   string    | `""`                  | Optional suffix (like `SNAPSHOT` or `RC1`) placed after the version number.               |
| initialVersion              |   string    | `"1.0.0"`             | Initial version number used when no version tag found in the git.                         |
| pushTag                     |   boolean   | true                  | The generated tag is automatically pushed by commitVersion task.                          |
| logPrefixes.bugfix          | set<String> | `{"%", "[fix]"}`      | The log entry prefixes used to locate bugfix related git commit message entries.          |
| logPrefixes.newFeature      | set<String> | `{"+", "[new]"}`      | The log entry prefixes used to locate new feature related git commit message entries.     |
| logPrefixes.breaking        | set<String> | `{"!", "[breaking]"}` | The log entry prefixes used to locate breaking change related git commit message entries. |
| logPrefixes.caseInsensitive |   boolean   | true                  | Whether handle log prefixes in a case-insensitive way.                                    |


### Tasks

#### versionInfo

Provides read-only information on the calculated next version.

#### setSemanticVersion

This tasks sets the `project.version` to the calculated semantic version. This task should be
added as dependency to one of your build tasks (for example to `jar` or `distXXX`):

#### commitVersion

Calculates the new version and commits it to the git as a new tag. If `pushTag` is true (default)
the tags are pushed as well.


### Using in other tasks

To access the calculated version, a dependency should be added to one of your build tasks. 
A good place for this could be `jar`, but you might choose to move it up in the build chain.

This is an example of usual usage of the versioning:

```kotlin
tasks.named("jar") {
    dependsOn("setSemanticVersion")
}
```

Also, in most cases, you will wish to add the commit as the last task in the
chain (`distXXX` or `publish`):

```kotlin
tasks.named("publishPlugins") {
    finalizedBy("commitVersion")
}
```
