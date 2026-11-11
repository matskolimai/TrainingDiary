# 02: Put the acceptance cases behind driver interfaces

**What to build:** the acceptance cases, DSL and driver contracts move to a test source directory that both test
source sets of `:app` compile, so a second channel can run the same cases later. Each Screen's cases become an
interface, each Screen's DSL depends on a driver interface, and the UI drivers implement those interfaces. Each device
test class shrinks to one line. No JVM code, no new steps and no production change in this ticket. See `../spec.md`
(Shared cases, DSL and driver interfaces).

Shape, from the prototype on branch `prototype/viewmodel-channel`:

```kotlin
interface AcceptanceCases {
    fun app(steps: AppDsl.() -> Unit)
}

interface TrainingEditorCases : AcceptanceCases { /* @Test cases, bodies as today */ }

class TrainingEditorAcceptanceTest : AcceptanceTest(), TrainingEditorCases
```

**Blocked by:** None (can start immediately).

**Status:** done

- [x] The DSL, `given`, the driver interfaces and the per-Screen cases live in one shared test directory compiled into
      both the unit-test and the instrumented-test source sets of `:app`.
- [x] `AppDsl` takes one driver per Screen, typed by its interface, not by a concrete UI driver.
- [x] The case bodies and names are unchanged.
- [x] Each device test class is one line: the device base class plus the Screen's cases interface.
- [x] The managed-device acceptance task runs the same 11 cases, all passing; `./gradlew check` passes.

## Comments

- The device test classes are one declaration with no body. ktlint's `class-signature` rule wraps a class with two
  supertypes over three lines, so they read `class X :` / `AcceptanceTest(),` / `XCases`.
- `convention.testing` adds `src/sharedTest/java` to both test source sets of the application module.
- `config/detekt/detekt.yml` adds `**/sharedTest/**` to `FunctionNaming`'s excludes, repeating Detekt's default test
  globs, so the backtick case names pass there as they do in `test` and `androidTest`. This is the one rule override
  in that file that is not a Compose adaptation, which the `android-module-structure` skill doesn't allow for yet.
- Gate: `./gradlew check` passes. On `pixel9DebugAndroidTest`, TrainingEditorAcceptanceTest ran 7 cases and
  TrainingListAcceptanceTest ran 4: 0 failed, 0 skipped.
