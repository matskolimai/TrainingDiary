# 19: S8 Edit by row tap

**What to build:** tapping a Training exercise card opens the Set editor in edit mode: title = Exercise name, subtitle
= its Exercise category, its Sets loaded, the Exercise fixed. A pinned „Gotowe” returns to the Training editor with
the changed Training exercise in the same place. Back returns to the Training editor unchanged. See `../spec.md`
(Navigation).

**Blocked by:** 16 (S7b Weight).

**Status:** done

- [x] `SetEditorKey(exerciseId, index, sets)` for edit mode; the Training editor's navigator pushes it.
- [x] `SetEditorResult.Replaced(index, trainingExercise)`, sent with the same `backToTrainingEditor(result)`.
- [x] Cases, test-first, on both channels: a row tap opens the Set editor with its Sets; „Gotowe” replaces it in
      place.
- [x] Edit-mode preview snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Comments

- No subtitle: ticket 03 (top bar subtitle) is wontfix, so edit mode shows only the Exercise name as its title. The
  Exercise category is not shown in the Set editor.
- `ExerciseSet` is now `@Serializable` (kotlinx-serialization added to `:feature:trainings:domain`) because
  `SetEditorKey` carries the Sets.
- The Training exercise card is tappable as a whole, so its name and summary merge into one node; the device driver
  matches both texts on that node.
- Extra case beyond the list: leaving the Set editor of a Training exercise without changes returns to the Training
  unchanged (user story 56).

## Mutation testing

| Mutant | Outcome | Channel |
| --- | --- | --- |
| `Replaced` result overwrites every row but the edited one (`==` → `!=`) | KILLED | ViewModel |
| Card tap does nothing (`OnExerciseClick -> Unit`) | KILLED | ViewModel |
| Edit and add modes swapped (`key.index != null` → `== null`) | KILLED | ViewModel |
| Edit mode ignores the key's Sets (state starts from 3 × 0 kg × 12) | KILLED | ViewModel |
| „Gotowe” always sends `Added`, appending a duplicate | KILLED | ViewModel |
