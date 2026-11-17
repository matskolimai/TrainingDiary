# 01: `startOn` for Screen shortcuts

**What to build:** cases that begin on a Screen deep in a flow say so with `startOn`, and `given` keeps only stored
data. `given.newTrainingExercise(category, exercise)` moves to a new `startOn` DSL as
`startOn.newTrainingExercise(category, exercise)`, with the same UI steps. No behaviour changes. See `../spec.md`.

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] `StartOnDsl` is registered in `AppDsl` as `startOn`, next to `given`, and depends only on Screen DSLs.
- [ ] `newTrainingExercise` lives only in `startOn`; every case that used `given.newTrainingExercise` uses
      `startOn.newTrainingExercise`, with its body otherwise unchanged.
- [ ] `GivenDsl` no longer depends on the Exercise category picker or Exercise picker DSLs.
- [ ] `./gradlew check` and the device suite pass.
