# TrainingDiary

An Android training diary.

## Setup after cloning

Install the versioned git hooks once per clone, so a commit fails on ktlint findings:

```sh
git config core.hooksPath config/git-hooks
```

## Gate before review

```sh
./gradlew check
```

`check` runs ktlint, Detekt (`detektMain`, `detektTest`) and the unit tests. `./gradlew ktlintFormat` fixes most ktlint
findings.
