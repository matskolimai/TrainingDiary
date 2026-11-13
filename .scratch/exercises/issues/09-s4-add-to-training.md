# 09: S4 Add to Training

**What to build:** the Set editor gets a pinned „Dodaj do treningu” button. It returns straight to the Training
editor, past both pickers, and the new Training exercise appears at the end of the list as a card: Exercise name, and
„<Exercise category> · 3 serie” (Polish plurals seria / serie / serii). Nothing is stored yet. See `../spec.md`
(Navigation, Visual details).

**Blocked by:** 07 (S3 Set editor, add mode), 08 (Screen results in AppNavigator).

**Status:** done

- [x] `TrainingExercise(exercise, sets)` in the trainings domain; `sealed interface SetEditorResult : NavResult` with
      `Added(trainingExercise)` (`Replaced` comes with ticket 19).
- [x] `SetEditorNavigator.backToTrainingEditor(result)`, implemented in `:app` with
      `AppNavigator.popTo(TrainingEditorKey::class, result)`.
- [x] `TrainingEditorNavigator.setEditorResults` = `appNavigator.results.filterIsInstance<SetEditorResult>()`,
      collected in the Training editor's ViewModel `init`; the list lives in its State.
- [x] Cards reuse `AppListRow` (no ⋮ yet); list 24dp below the field, 12dp gap; „Dodaj ćwiczenie” 16dp below the
      last card and scrolls with the list.
- [x] Cases, test-first, on both channels: „Dodaj do treningu” returns to the Training editor showing the name and
      „category · 3 serie”; the same Exercise twice shows two cards in added order.
- [x] Training editor preview with Training exercises snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done, covering ticket 08's `popTo` lines too.

## Mutation testing

Covers this ticket's lines and ticket 08's `popTo` (`AppNavigator.popTo`, `NavBackStack.popTo`).

| # | Mutant | Outcome | Channel | Settlement |
|---|--------|---------|---------|------------|
| 1 | `AppNavigator.popTo` queues the pop before the result | SURVIVED | device | Accepted, equivalent (note 1) |
| 2 | `AppNavigator.popTo` drops the result | KILLED | ViewModel | |
| 3 | `NavBackStack.popTo` removes one entry too few | KILLED | ViewModel | |
| 4 | Training editor prepends the added Training exercise | KILLED | ViewModel | New case order (note 2) |
| 5 | Set editor returns the Training exercise with no Sets | KILLED | ViewModel | |

1. Equivalent: the pop never clears the receiving ViewModel, which stays on the stack and collects for its whole
   life, so the result arrives in either order.
2. It first SURVIVED: the duplicate case added A, B, A, which reads the same reversed. The case now adds A, A, B.
