# 03: Static analysis + git hook

**What to build:** style is enforced by tools: `./gradlew check` fails on ktlint or Detekt findings, and a commit
fails on ktlint findings. See the spec's "Static analysis and style".

**Blocked by:** 02 (build-logic convention plugins)

**Status:** done

- [x] `convention.static-analysis`, applied by the base plugins
- [x] Root `.editorconfig` with `ktlint_code_style = ktlint_official` and `max_line_length = 120`, plus
      `ktlint_function_naming_ignore_when_annotated_with = Composable`; ktlint 1.8.0 through ktlint-gradle 14.2.0
- [x] Detekt 2.0.0-alpha.6 (`dev.detekt`), `buildUponDefaultConfig`, `failOnSeverity = Info` (Detekt 2's
      replacement for `maxIssues: 0`), no baseline, compose-rules 0.6.8; the Detekt config holds only the Compose
      adaptations the template's composables needed (`FunctionNaming` ignoring `@Composable`,
      `UnusedPrivateFunction` ignoring `@Preview`)
- [x] `check` runs `ktlintCheck`, `detektMain` and `detektTest` (the type-resolving tasks)
- [x] Versioned pre-commit hook running `ktlintCheck`, installed with `git config core.hooksPath`; how to install it
      is written where a fresh clone finds it
- [x] Existing template code is fixed to pass, without suppressions
- [x] `./gradlew check` is green

## Comments

- The template's composables tripped `FunctionNaming` in both ktlint and Detekt, and its private preview tripped
  Detekt's `UnusedPrivateFunction`, so the Compose adaptations arrived here rather than later; the spec was updated
  to allow the ktlint one in `.editorconfig`.
