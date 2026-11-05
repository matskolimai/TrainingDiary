# Module structure and build-logic

Type: grilling
Status: open
Blocked by: 02

## Question

Which parts of `android-module-structure` does the user accept or override for this app: feature-layered modules (`:feature:trainings:{domain,data,presentation}`), `:core:*` submodules (incl. `:core:database`, `:core:design-system`), `:build-logic` convention plugins (which ones, given AGP 9), version-catalog rules, and static analysis (ktlint/Detekt, how enforced)? Is the domain layer a pure Kotlin/JVM module? Following the library-landscape research: which Kotlin version to bump to (2.2.10 is too old for Koin 4.2.2 and kotlinx-serialization 1.12), and whether to use Detekt 2.0 alpha or defer Detekt.

Resolve by presenting the skill's prescriptions point by point, then rewrite `~/.claude/skills/android-module-structure/SKILL.md` with `/mattpocock-skills:writing-for-agents` to reflect the outcome.
