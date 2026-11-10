# 18: S3 Add a Training

**What to build:** the user types a name and saves with „Zapisz”; the editor closes and the trimmed Training appears
at the end of the list. The keyboard's Done only hides the keyboard. „Zapisz” stays disabled for a blank name. See
the spec's "Product rules", "Domain", "Presentation" and user stories 2, 6, 7, 12, 20–23 and 25–27.

**Blocked by:** 16 (S2 Open editor for adding), 17 (AppListRow)

**Status:** done

- [x] Written test-first: acceptance cases 2 "An added Training appears in the list", 3 "Names are trimmed" and 4
      "Save is unavailable for a blank name"; `givenTrainings` added to the DSL
- [x] A pure `isValidTrainingName` in the feature domain, also used by the repository's `require` (replacing ticket
      08's temporary check). Unit tests with `/mattpocock-skills:tdd` only if its input space warrants them; if
      written, the break vectors go into the change summary
      (none written: it is `name.isNotBlank()`, and `trim()` and `isBlank()` share `Char.isWhitespace`)
- [x] `canSave = !isSaving && isValidTrainingName(name)`; `OnSaveClick` is ignored unless `canSave`, so a double
      tap saves once; the keyboard's Done only hides the keyboard
- [x] After saving, the ViewModel emits `TrainingSaved` and the Root maps it to `pop(key)`; `ObserveAsEvents` lives
      in `:core:presentation`
- [x] The list shows `TrainingRow`: `AppListRow` with the wrapping name and a ⋮ `AppIconButton` („Więcej opcji”)
      that does nothing yet; tapping the row does nothing; list padding and gaps as in the spec, with room left for
      the FAB
- [x] Previews for the list with Trainings and the editor with a valid name, snapshotted; reference images committed
- [x] `./gradlew check` and the managed-device test task are green
