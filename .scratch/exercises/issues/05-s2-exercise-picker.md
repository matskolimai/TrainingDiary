# 05: S2 Exercise picker

**What to build:** tapping an Exercise category opens the Exercise picker: title „Dodaj ćwiczenie” (no subtitle:
the step subtitles were dropped with ticket 03), that category's Exercises in catalogue order as card rows (name only,
min height 56dp). Back returns to the Exercise category picker. Tapping an Exercise does nothing yet. See `../spec.md`.

**Blocked by:** 04 (S1 Exercise category picker).

**Status:** ready-for-agent

- [x] `ExercisePickerKey(categoryId)`, pushed within the exercises feature; `KoinModulesTest` gets an
      `injectedParameters` entry for it.
- [x] DSL steps and drivers on both channels.
- [x] Cases, test-first, on both channels: a category tap lists its Exercises in catalogue order; back returns to the
      categories.
- [x] Screen preview snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Mutation testing

| Mutant | Outcome | Channel |
|---|---|---|
| Category tap's action no longer calls `openExercises` | KILLED | ViewModel |
| Category row's `onClick` not wired to the action | KILLED | device |
| `:app` pushes `ExercisePickerKey(1)` whatever the category | KILLED | ViewModel |
| Back on the Exercise picker no longer calls `navigator.back` | KILLED | ViewModel |
| Exercise rows show the category name instead of the Exercise's | KILLED | device |
