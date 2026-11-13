# 07: S3 Set editor, add mode

**What to build:** tapping an Exercise opens the Set editor: title „Dodaj ćwiczenie”, subtitle „Krok 3 z 3:
<Exercise>”, and 3 Set cards (number in a circle, weight pill „0 kg”, reps pill „12 powt.”). Nothing can be changed
yet and there is no confirm button yet. Back returns to the Exercise picker. See `../spec.md` (Set editor visuals).

**Blocked by:** 05 (S2 Exercise picker), 06 (AppValuePill).

**Status:** ready-for-agent

- [ ] Set editor in `:feature:trainings:presentation`; `ExerciseSet(reps, weightGrams)` in the trainings domain.
- [ ] `ExercisePickerNavigator.onExercisePicked(exerciseId)`; `:app` pushes `SetEditorKey(exerciseId)` (add mode,
      `index == null`); `KoinModulesTest` entry for `SetEditorKey`.
- [ ] Weight shows „0 kg” (only the zero case is needed here); reps show „12 powt.”.
- [ ] DSL steps and drivers on both channels.
- [ ] Cases, test-first, on both channels: picking an Exercise opens the Set editor at 3 × 0 kg × 12; back returns to
      the Exercise picker.
- [ ] Screen preview snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
