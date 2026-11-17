# 03: Training list owns opening the Training editor

**What to build:** `TrainingListCases` states everything the user can do from the Training list, including opening
a new Training and opening a Training for editing. The list's cases assert only that the right Screen opened for
the right Training; what the Training editor shows on entry stays in `TrainingEditorCases`. See `../spec.md`.

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] New step `trainingEditor.assertAdding()`: the ViewModel driver checks that the top nav key is the Training
      editor in add mode; the device driver checks for the new training Screen's title from string resources.
- [ ] Case on both channels: `opening a new training shows the new training screen`.
- [ ] Case on both channels: `opening a training for editing shows that training's edit screen`, with "Push day"
      and "Nogi" stored, opening "Nogi" and asserting `assertEditing("Nogi")`.
- [ ] The Training editor's two "opening…" cases stay where they are, unchanged.
- [ ] `./gradlew check` and the device suite pass.
