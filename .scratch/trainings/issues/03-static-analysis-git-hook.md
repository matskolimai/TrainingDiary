# 03: Static analysis + git hook

**What to build:** style is enforced by tools: `./gradlew check` fails on ktlint or Detekt findings, and a commit
fails on ktlint findings. See the spec's "Static analysis and style".

**Blocked by:** 02 (build-logic convention plugins)

**Status:** ready-for-agent

- [ ] `convention.static-analysis`, applied by the base plugins
- [ ] Root `.editorconfig` with only `ktlint_code_style = ktlint_official` and `max_line_length = 120`; ktlint 1.8.0
      through ktlint-gradle 14.2.0
- [ ] Detekt 2.0.0-alpha.6 (`dev.detekt`), `buildUponDefaultConfig`, `maxIssues: 0`, no baseline, compose-rules
      0.6.8; the Detekt config holds no overrides yet (Compose adaptations arrive when a composable needs them)
- [ ] `check` runs `ktlintCheck`, `detektMain` and `detektTest` (the type-resolving tasks)
- [ ] Versioned pre-commit hook running `ktlintCheck`, installed with `git config core.hooksPath`; how to install it
      is written where a fresh clone finds it
- [ ] Existing template code is fixed to pass, without suppressions
- [ ] `./gradlew check` is green
