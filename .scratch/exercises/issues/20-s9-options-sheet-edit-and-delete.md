# 20: S9 ⋮ sheet: Edytuj and Usuń

**What to build:** each Training exercise card gets a trailing ⋮ („Więcej opcji”) that opens the same bottom sheet as
the Training list: „Edytuj” opens the Set editor like a row tap; „Usuń” removes the Training exercise from the
unsaved list immediately, without undo. „Zapisz” stores the removal. See `../spec.md`.

**Blocked by:** 19 (S8 Edit by row tap).

**Status:** done

- [x] The sheet reuses `AppBottomSheet` / `AppSheetItem` (`edit` „Edytuj”, `delete` „Usuń”).
- [x] Cases, test-first, on both channels: ⋮ → „Edytuj” opens the Set editor with its Sets; ⋮ → „Usuń” removes it;
      saving stores the removal.
- [x] Previews updated and snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Comments

- The sheet has its own strings (`training_editor_cd_exercise_more`, `training_editor_exercise_edit`,
  `training_editor_exercise_delete`) with the same wording as the Training list's.
- The cases live in `TrainingEditorCases`, where the action happens. Extra beyond the list: deleting keeps the order
  of the remaining Training exercises; closing the options without choosing keeps the Training exercise (added
  for mutant 4). Code review: a quick second tap on „Usuń” removed a second Training exercise, so the sheet's
  actions now read the open sheet from the state and do nothing once it closed (ViewModel-only case added).

## Mutation testing

| Mutant | Outcome | Channel |
| --- | --- | --- |
| „Edytuj” leaves the sheet open behind the Set editor | KILLED once the edit step checked the sheet | ViewModel |
| „Usuń” closes the sheet but removes nothing | KILLED | ViewModel |
| „Usuń” keeps only the chosen Training exercise (`!=` → `==`) | KILLED | ViewModel |
| Closing the sheet is ignored, so it stays open | KILLED after the case „closing the options…” was added | ViewModel |
| ⋮ opens the Set editor instead of the sheet | KILLED | device |
