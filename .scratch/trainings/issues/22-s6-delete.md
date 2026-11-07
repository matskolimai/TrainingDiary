# 22: S6 Delete

**What to build:** „Usuń” in the sheet deletes the Training immediately, without confirmation, and a snackbar
„Usunięto „<name>”” appears with a „Cofnij” action that does nothing yet. See the spec's "Presentation" and user
stories 37, 38 and 41.

**Blocked by:** 21 (S5 Edit)

**Status:** ready-for-agent

- [ ] Written test-first: acceptance case 6 "Delete removes it and offers undo"
- [ ] The ViewModel deletes the Training, keeps it, and emits `TrainingDeleted(name)`
- [ ] The list Root owns `SnackbarHostState`, passed through `AppScaffold`, and dismisses the current snackbar before
      showing a new one
- [ ] The FAB moves above the snackbar
- [ ] Polish strings for the message and „Cofnij”
- [ ] `./gradlew check` and the managed-device test task are green
