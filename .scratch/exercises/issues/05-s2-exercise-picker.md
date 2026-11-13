# 05: S2 Exercise picker

**What to build:** tapping an Exercise category opens the Exercise picker: title „Dodaj ćwiczenie”, subtitle
„Krok 2 z 3: <Exercise category>”, that category's Exercises in catalogue order as card rows (name only, min height
56dp). Back returns to the Exercise category picker. Tapping an Exercise does nothing yet. See `../spec.md`.

**Blocked by:** 04 (S1 Exercise category picker).

**Status:** ready-for-agent

- [ ] `ExercisePickerKey(categoryId)`, pushed within the exercises feature; `KoinModulesTest` gets an
      `injectedParameters` entry for it.
- [ ] DSL steps and drivers on both channels.
- [ ] Cases, test-first, on both channels: a category tap lists its Exercises in catalogue order; back returns to the
      categories.
- [ ] Screen preview snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
