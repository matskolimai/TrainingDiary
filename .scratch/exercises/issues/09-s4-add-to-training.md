# 09: S4 Add to Training

**What to build:** the Set editor gets a pinned „Dodaj do treningu” button. It returns straight to the Training
editor, past both pickers, and the new Training exercise appears at the end of the list as a card: Exercise name, and
„<Exercise category> · 3 serie” (Polish plurals seria / serie / serii). Nothing is stored yet. See `../spec.md`
(Navigation, Visual details).

**Blocked by:** 07 (S3 Set editor, add mode), 08 (Screen results in AppNavigator).

**Status:** ready-for-agent

- [ ] `TrainingExercise(exercise, sets)` in the trainings domain; `sealed interface SetEditorResult : NavResult` with
      `Added(trainingExercise)` (`Replaced` comes with ticket 19).
- [ ] `SetEditorNavigator.backToTrainingEditor(result)`, implemented in `:app` with
      `AppNavigator.popTo(TrainingEditorKey::class, result)`.
- [ ] `TrainingEditorNavigator.setEditorResults` = `appNavigator.results.filterIsInstance<SetEditorResult>()`,
      collected in the Training editor's ViewModel `init`; the list lives in its State.
- [ ] Cards reuse `AppListRow` (no ⋮ yet); list 24dp below the field, 12dp gap; „Dodaj ćwiczenie” 16dp below the
      last card and scrolls with the list.
- [ ] Cases, test-first, on both channels: „Dodaj do treningu” returns to the Training editor showing the name and
      „category · 3 serie”; the same Exercise twice shows two cards in added order.
- [ ] Training editor preview with Training exercises snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done, covering ticket 08's `popTo` lines too.
