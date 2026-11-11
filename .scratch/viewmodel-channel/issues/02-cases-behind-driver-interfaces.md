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

**Status:** ready-for-agent

- [ ] The DSL, `given`, the driver interfaces and the per-Screen cases live in one shared test directory compiled into
      both the unit-test and the instrumented-test source sets of `:app`.
- [ ] `AppDsl` takes one driver per Screen, typed by its interface, not by a concrete UI driver.
- [ ] The case bodies and names are unchanged.
- [ ] Each device test class is one line: the device base class plus the Screen's cases interface.
- [ ] The managed-device acceptance task runs the same 11 cases, all passing; `./gradlew check` passes.
