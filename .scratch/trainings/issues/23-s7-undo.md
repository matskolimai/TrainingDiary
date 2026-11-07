# 23: S7 Undo

**What to build:** „Cofnij” on the snackbar puts the deleted Training back in its original position; only the
latest delete can be undone. See the spec's "Product rules" and user stories 39 and 40.

**Blocked by:** 22 (S6 Delete)

**Status:** ready-for-agent

- [ ] Written test-first: acceptance cases 7 "Undo restores it in its original position" and 8 "Only the latest
      delete can be undone"
- [ ] `ActionPerformed` sends `OnUndoClick`; the ViewModel calls `restoreTraining` with the kept Training (its
      original id)
- [ ] A newer delete replaces the kept Training, so an older one can no longer be restored
- [ ] The pending undo isn't saved across process death
- [ ] `./gradlew check` and the managed-device test task are green
