# 16: S2 Open editor for adding

**What to build:** tapping the FAB opens an empty editor titled „Nowy trening”, with the name field focused and the
keyboard open; ← or system back returns to the list with nothing changed. Saving doesn't work yet. See the spec's
"Presentation", "Navigation", "Visual details" and user stories 18, 19, 24 and 33–36.

**Blocked by:** 12 (S1 Empty list), 13 (AppIconButton + AppTopBarWithBack), 14 (AppTextField), 15 (AppButton)

**Status:** ready-for-agent

- [ ] Written test-first: acceptance cases 11 "Starting to add opens an empty editor titled „Nowy trening”" and 10
      "Back from the editor without saving changes nothing"
- [ ] The list Root intercepts `OnAddClick` and pushes `TrainingEditorKey()` through the `push` guard
- [ ] `TrainingEditor` Root and Screen wired for `TrainingEditorKey`; `TrainingEditorViewModel(key, repository)`
      obtained with `koinViewModel { parametersOf(key) }`; `KoinModulesTest` gets the matching injected-parameters
      entry
- [ ] Editor state `name`, `isEditMode`, `isLoading`, `isSaving`; `OnNameChange` updates the name; nothing in `init`
- [ ] „Nazwa” field, and a disabled „Zapisz” pinned to the bottom above the keyboard (`imePadding`)
- [ ] Add mode autofocuses the field (a Compose-internal effect)
- [ ] `OnBackClick` and system back call `pop(key)`
- [ ] Previews for the add-mode states, snapshotted; reference images committed
- [ ] `./gradlew check` and the managed-device test task are green
