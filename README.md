# Semantic versioning gradle plugin

This plugin provides automated semantic versioning based on specially prefixed
lines in the commit messages. The plugin handles proper version increment and
marks the versions in git by tags.

## Usage

### Import

[TODO] 

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
        bugfix.addAll(listOf("%", "[fix]"))
    }
}
```

| Parameter  | Type  | Default  | Description   |
| ------------- |:-------------:|:----- |:----- |
| forced  | boolean  | false | When set, the tasks will execute regardless the input state.  |
| acceptedBranches  | Set<String>  | {master, main} | On which branches the new releases could be made.  |
| allowDirtyLocal  | boolean | false | Whether release creation is allowed when there are any uncommitted changes. |
| releaseTagPrefix  | string | `"release"` | The tag prefix used in git tags marking releases. |
| versionSuffix | string | `""` | Optional suffix (like `SNAPSHOT` or `RC1`) placed after the version number. |
| initialVersion | string | `"1.0.0"` | Initial version number used when no version tag found in the git. |
| logPrefixes.bugfix | set<String> | `{"%", "[fix]"}` | The log entry prefixes used to locate bugfix related git commit message entries. |
| logPrefixes.newFeature | set<String> | `{"+", "[new]"}` | The log entry prefixes used to locate new feature related git commit message entries. |
| logPrefixes.breaking | set<String> | `{"!", "[breaking]"}` | The log entry prefixes used to locate breaking change related git commit message entries. |
| logPrefixes.caseInsensitive | boolean | true | Whether handle log prefixes in a case-insensitive way. |


### Tasks

#### versionInfo

Provides read-only information on the calculated next version.


#### commitVersion

Calculates the new version and commits it to the git as a new tag.
