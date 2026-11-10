# 21: S5 Edit

**What to build:** tapping ⋮ on a row opens a sheet with „Edytuj” and „Usuń”. „Edytuj” opens the editor titled
„Edytuj trening” with the name filled in and the keyboard closed, and saving renames the Training in place. If the
Training is missing, the editor closes. „Usuń” does nothing yet. See the spec's "Presentation" and user stories
14–17 and 28–32.

**Blocked by:** 19 (S4 Top-bar count), 20 (AppBottomSheet + AppSheetItem)

**Status:** done

- [x] Written test-first: acceptance cases 12 "Starting to edit opens the editor titled „Edytuj trening” with the
      name filled in" and 5 "Editing renames the Training, keeping its position"
- [x] List state `optionsSheetTrainingId: Long?`; ⋮ opens the sheet; a swipe down or a tap outside dismisses it;
      choosing Edytuj or Usuń closes it
- [x] The list Root intercepts `OnEditClick(id)` and pushes `TrainingEditorKey(id)` through the `push` guard
- [x] Edit mode loads from `onStart` + `stateIn` with a `hasLoaded` guard, draws the form only once loaded, and
      doesn't autofocus; a missing Training emits `TrainingMissing`, which the Root maps to `pop(key)`
- [x] Saving in edit mode calls `updateTraining` and emits `TrainingSaved`
- [x] Previews for the open sheet, edit mode loading and edit mode loaded, snapshotted; reference images committed
- [x] `./gradlew check` and the managed-device test task are green
