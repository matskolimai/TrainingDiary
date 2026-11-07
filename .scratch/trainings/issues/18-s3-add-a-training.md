# 18: S3 Add a Training

**What to build:** the user types a name and saves with „Zapisz” or the keyboard's Done; the editor closes and the
trimmed Training appears at the end of the list. „Zapisz” stays disabled for a blank name. See the spec's "Product
rules", "Domain", "Presentation" and user stories 2, 6, 7, 12, 20–23 and 25–27.

**Blocked by:** 16 (S2 Open editor for adding), 17 (AppListRow)

**Status:** ready-for-agent

- [ ] Written test-first: acceptance cases 2 "An added Training appears in the list", 3 "Names are trimmed" and 4
      "Save is unavailable for a blank name"; `givenTrainings` added to the DSL
- [ ] A pure `isValidTrainingName` in the feature domain, also used by the repository's `require` (replacing ticket
      08's temporary check). Unit tests with `/mattpocock-skills:tdd` only if its input space warrants them; if
      written, the break vectors go into the change summary
- [ ] `canSave = !isSaving && isValidTrainingName(name)`; `OnSaveClick` and keyboard Done are ignored unless
      `canSave`, so a double tap saves once
- [ ] After saving, the ViewModel emits `TrainingSaved` and the Root maps it to `pop(key)`; `ObserveAsEvents` lives
      in `:core:presentation`
- [ ] The list shows `TrainingRow`: `AppListRow` with the wrapping name and a ⋮ `AppIconButton` („Więcej opcji”)
      that does nothing yet; tapping the row does nothing; list padding and gaps as in the spec, with room left for
      the FAB
- [ ] Previews for the list with Trainings and the editor with a valid name, snapshotted; reference images committed
- [ ] `./gradlew check` and the managed-device test task are green
