# Semantic versioning gradle plugin

This plugin provides automated semantic versioning based on specially prefixed
lines in the commit messages. The plugin handles proper version increment and
marks the versions in git by tags.

## Usage

### Import

[TODO] 

### Configuration

```kotlin
semanticVersion {
    forced = false
    acceptedBranches.addAll(listOf("master", "main"))
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

