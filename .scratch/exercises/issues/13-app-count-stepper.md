# 13: AppCountStepper

**What to build:** a „− N +” control built on `AppRepeatIconButton`: count 22sp with tabular digits, − and + each
disableable. See `../spec.md` (Design system).

**Blocked by:** 12 (AppRepeatIconButton).

**Status:** ready-for-agent

- [x] `AppCountStepper(count, onDecrement, onIncrement, decrementEnabled, incrementEnabled)` in
      `:core:design-system`.
- [x] Previews for the middle and both limits, snapshotted.
- [x] `./gradlew check` passes.

## Mutation testing

No production Kotlin changed outside `:core:design-system`, which screenshot tests cover; no mutants run.
