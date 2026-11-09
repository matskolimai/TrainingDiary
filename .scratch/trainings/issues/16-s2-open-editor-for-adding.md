# 16: S2 Open editor for adding

**What to build:** tapping the FAB opens an empty editor titled „Nowy trening”, with the name field focused and the
keyboard open; ← or system back returns to the list with nothing changed. Saving doesn't work yet. See the spec's
"Presentation", "Navigation", "Visual details" and user stories 18, 19, 24 and 33–36.

**Blocked by:** 12 (S1 Empty list), 13 (AppIconButton + AppTopBarWithBack), 14 (AppTextField), 15 (AppButton)

**Status:** done

- [x] Written test-first: acceptance cases 11 "Starting to add opens an empty editor titled „Nowy trening”" and 10
      "Back from the editor without saving changes nothing"
- [x] The list Root intercepts `OnAddClick` and pushes `TrainingEditorKey()` through the `push` guard
- [x] `TrainingEditor` Root and Screen wired for `TrainingEditorKey`; `TrainingEditorViewModel(key, repository)`
      obtained with `koinViewModel { parametersOf(key) }`; `KoinModulesTest` gets the matching injected-parameters
      entry (the ViewModel is internal, so the test finds it by name next to `TrainingEditorKey`)
- [x] Editor state `name`, `isEditMode`, `isLoading`, `isSaving`; `OnNameChange` updates the name; nothing in `init`
- [x] „Nazwa” field, and a disabled „Zapisz” pinned to the bottom above the keyboard (`imePadding`)
- [x] Add mode autofocuses the field (a Compose-internal effect)
- [x] `OnBackClick` and system back call `pop(key)`
      (← calls `pop(key)`; system back stays with NavDisplay's `onBack = removeLastOrNull`, as the spec's
      Navigation section has it, which removes the same top entry and keeps predictive back)
- [x] Previews for the add-mode states, snapshotted; reference images committed
- [x] `./gradlew check` and the managed-device test task are green
