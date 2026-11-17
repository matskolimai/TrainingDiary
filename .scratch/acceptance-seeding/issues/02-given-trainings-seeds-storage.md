# 02: `given.trainings` writes to storage instead of driving the UI

**What to build:** `given.trainings("Push day", "Nogi")` stores the Trainings directly through the app's real
`TrainingRepository`, without opening the Training editor, so cases start faster. Opening a new Training and saving
it keep their own owning cases through the UI. See `../spec.md`.

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] A `GivenDriver` interface sits behind `GivenDsl`, with `GivenDeviceDriver` and `GivenViewModelDriver`
      supplied by each channel's base test class.
- [ ] Both drivers get `TrainingRepository` from the real Koin graph and store each Training with no Training
      exercises, in the given order. The ViewModel driver runs the call on the test dispatcher and settles; the
      device driver may block.
- [ ] `GivenDsl` depends only on `GivenDriver`, not on Screen DSLs.
- [ ] Every case using `given.trainings` passes unchanged on both channels.
- [ ] `./gradlew check` and the device suite pass.
