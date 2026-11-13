# 07: S3 Set editor, add mode

**What to build:** tapping an Exercise opens the Set editor: title „Dodaj ćwiczenie” (no subtitle: the step
subtitles were dropped with ticket 03), and 3 Set cards (number in a circle, weight pill „0 kg”, reps pill
„12 powt.”). Nothing can be changed yet and there is no confirm button yet. Back returns to the Exercise picker.
See `../spec.md` (Set editor visuals).

**Blocked by:** 05 (S2 Exercise picker), 06 (AppValuePill).

**Status:** done

- [x] Set editor in `:feature:trainings:presentation`; `ExerciseSet(reps, weightGrams)` in the trainings domain.
- [x] `ExercisePickerNavigator.onExercisePicked(exerciseId)`; `:app` pushes `SetEditorKey(exerciseId)` (add mode,
      `index == null`); `KoinModulesTest` entry for `SetEditorKey`. The key holds only `exerciseId` for now;
      `index` and `sets` join it with edit mode (S8).
- [x] Weight shows „0 kg” (only the zero case is needed here); reps show „12 powt.”.
- [x] DSL steps and drivers on both channels.
- [x] Cases, test-first, on both channels: picking an Exercise opens the Set editor at 3 × 0 kg × 12; back returns to
      the Exercise picker.
- [x] Screen preview snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Mutation testing

| Mutant | Outcome | Channel |
|---|---|---|
| Exercise picker: row tap no longer sends `OnExerciseClick` | KILLED | device |
| Exercise picker: Exercise tap no longer calls `onExercisePicked(…)` | KILLED | ViewModel |
| Set editor: back action no longer calls `navigator.back(key)` | KILLED | ViewModel |
| Set editor: Set numbers start at 0 instead of 1 | KILLED | device |
| Set editor: reps pill shows the weight value | KILLED | device |

## Comments

- Set cards are `AppListRow`s: it gained an optional `leading` slot (12dp start, 8dp before the content), and the
  number circle is the new `AppNumberCircle`; both are snapshotted. Features may not import `Surface`.
- `onExercisePicked` keeps the spec's name: the exercises feature reports the pick without naming another feature's
  Screen; `:app` decides it opens the Set editor.
- The two cases sit in `SetEditorCases`, like ticket 05's pick case sits with the Screen it opens.
- The pills are tappable but do nothing until focus arrives (S7); the weight shows whole kilograms only until then.
