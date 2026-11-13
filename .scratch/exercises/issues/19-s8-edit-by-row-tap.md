# 19: S8 Edit by row tap

**What to build:** tapping a Training exercise card opens the Set editor in edit mode: title = Exercise name, subtitle
= its Exercise category, its Sets loaded, the Exercise fixed. A pinned „Gotowe” returns to the Training editor with
the changed Training exercise in the same place. Back returns to the Training editor unchanged. See `../spec.md`
(Navigation).

**Blocked by:** 16 (S7b Weight).

**Status:** ready-for-agent

- [ ] `SetEditorKey(exerciseId, index, sets)` for edit mode; the Training editor's navigator pushes it.
- [ ] `SetEditorResult.Replaced(index, trainingExercise)`, sent with the same `backToTrainingEditor(result)`.
- [ ] Cases, test-first, on both channels: a row tap opens the Set editor with its Sets; „Gotowe” replaces it in
      place.
- [ ] Edit-mode preview snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
