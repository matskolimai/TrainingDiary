# Module structure and build-logic

Type: grilling
Status: resolved
Blocked by: 02

## Question

Which parts of `android-module-structure` does the user accept or override for this app: feature-layered modules (`:feature:trainings:{domain,data,presentation}`), `:core:*` submodules (incl. `:core:database`, `:core:design-system`), `:build-logic` convention plugins (which ones, given AGP 9), version-catalog rules, and static analysis (ktlint/Detekt, how enforced)? Is the domain layer a pure Kotlin/JVM module? Following the library-landscape research: which Kotlin version to bump to (2.2.10 is too old for Koin 4.2.2 and kotlinx-serialization 1.12), and whether to use Detekt 2.0 alpha or defer Detekt.

Resolve by presenting the skill's prescriptions point by point, then rewrite `~/.claude/skills/android-module-structure/SKILL.md` with `/mattpocock-skills:writing-for-agents` to reflect the outcome.

## Answer

Settled with the user on 2026-11-05, point by point against `android-module-structure`. The skill was rewritten to match
(`~/.claude/skills/android-module-structure/SKILL.md`, Android-only, written with `/mattpocock-skills:writing-for-agents`).

- **Modules**: each feature gets three layer modules, `:feature:trainings:{domain,data,presentation}`. Features never
  depend on each other. `:app` stays thin.
- **Core modules created now**: `:core:domain`, `:core:presentation`, `:core:design-system`, `:core:database`. There is
  no `:core:data` until a remote API exists.
- **Where code lives**: the "needed by more than one feature" rule applies to feature code only. App-wide
  infrastructure goes straight into `:core`. `feature:*:data` may depend on `:core:database`.
- **Domain modules** (feature and core) are pure Kotlin/JVM (`org.jetbrains.kotlin.jvm`).
- **KMP** is dropped from the skill. A future KMP project gets its own skill.
- **Toolchain bump**: Kotlin 2.4.21, KSP 2.3.13, AGP 9.5.0, Compose BOM 2026.09.00 (Compose 1.12.1). JVM target and
  toolchain are 17. A single `kotlin` catalog version is shared by built-in Kotlin and `kotlin-jvm`.
- **build-logic** is an included build at `build-logic/convention`. Plugin ids use a generic `convention.*` prefix:
  `android.application`, `android.library`, `android.feature`, `jvm.library`, `android.compose`, `android.room`, `koin`,
  `testing`, `static-analysis`.
  - There is no `ktor` plugin and no separate serialization plugin. `android.feature` applies kotlinx-serialization for
    the Nav3 keys.
  - The base plugins apply `testing` and `static-analysis` themselves.
  - The skill records the AGP 9 rules: no `kotlin-android`, untyped `CommonExtension`, `compilerOptions`.
- **Compiler**: `allWarningsAsErrors = true`, with a per-module Gradle-property opt-out for experimental APIs whose
  warnings can't be fixed. `@OptIn` is declared at the use site. `org.gradle.parallel=true` is enabled.
- **Version catalog**: every coordinate and version is in `libs.versions.toml`. BOMs are used wherever they exist,
  bundles only for groups that always travel together, and build-logic gets versions only through the catalog.
- **Static analysis** (overrides the recommendation to defer Detekt: the user chose the alpha):
  - ktlint 1.8.0 through ktlint-gradle 14.2.0, with its rules in `.editorconfig`.
  - **Detekt 2.0.0-alpha.6** (`dev.detekt`): `buildUponDefaultConfig`, overrides only in `config/detekt/detekt.yml`,
    `maxIssues: 0`, no baseline.
  - compose-rules `io.nlopez.compose.rules:detekt` 0.6.8. It is built against Detekt 2.0.0-alpha.6 and Kotlin 2.4.10,
    as verified from its release notes. Rules that clash with Compose are adjusted.
- **Gates**: a versioned `config/git-hooks/pre-commit` runs `ktlintCheck`, installed through
  `git config core.hooksPath config/git-hooks`. `./gradlew check` runs ktlint, Detekt and tests, and is the gate
  before review.
- **Naming**: the feature module is plural, `:feature:trainings`. Namespace and package equal the module path
  (`pl.skolim.trainingdiary.feature.trainings.domain`). Type-safe project accessors are on.
- **Visibility**: `data` and `presentation` are `internal` by default, and their public surface is the DI module plus the
  nav entry. Exactly how those are exposed is left to the DI and Navigation tickets. Domain types are public, and
  explicit API mode is off.
- **Key libraries** in the skill are defaults, added only when needed: Nav3, Room 3 with KSP, JUnit 6 through
  mannodermaus, and the KMP rows dropped. This app adopts Room, Koin, Nav3, kotlinx-serialization, coroutines and the
  test stack.
- No glossary changes and no ADR: the decisions can be reversed and the skill documents them.
