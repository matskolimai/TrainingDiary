# 05: Run the Training editor cases on the JVM

**What to build:** the Training editor cases run on the ViewModel channel too. The Training editor ViewModel driver
gets its remaining steps, and the device-only "ready to type" case is skipped on the JVM. See `../spec.md` (ViewModel
drivers).

- Typing appends to the current name; replacing sets it.
- "Cannot save" reads `canSave`.
- "Showing for editing" requires edit mode, not loading, and the name.
- Saving and leaving require that the Training editor is no longer on top.
- Checking focus throws the cannot-stage exception.

**Blocked by:** 03 (Tag cases for one channel), 04 (Run the Training list cases on the JVM).

**Status:** done

- [x] No `TODO` remains in the ViewModel drivers.
- [x] A one-line JVM test class runs the Training editor cases: 6 pass, 1 skipped (ready to type).
- [x] Re-applying the control mutant (typing a name does not update state) by hand fails cases on the JVM.
- [x] `./gradlew check` and the device suite pass.

## Comments

- `TrainingEditorViewModelTest` runs the existing shared cases through the ViewModel channel: six pass and the
  device-only focus case is skipped. As for the Training list, ktlint wraps its single class declaration.
- The editor driver checks add/edit state, loading, names and `canSave`; replacement sets the name, leaving sends
  the back action and checks that the editor is no longer on top. Focus throws `CannotStageException` with the step
  name and channel. The existing typing and saving steps already append and check navigation respectively.
- Leave, blank-name, edit-loading and rename cases each failed at their unfinished step before its implementation,
  then passed individually. The complete editor class passed in about 0.8 seconds.
- Replacing `OnNameChange` with a no-op made four JVM editor cases fail: both new-Training saves, opening for editing,
  and renaming in place. The production file was restored byte-for-byte; no production Kotlin change remains.
- Gate: `./gradlew check :app:pixel9DebugAndroidTest --no-daemon` passes. The JVM editor class reports six passed and
  one skipped. A fresh device run with `:app:pixel9DebugAndroidTest --rerun --no-daemon` passes all 11 cases, with none
  skipped.
- Review: Standards suggested sharing dispatch and settling, now handled by `act` as in the list driver. The final
  Standards and Spec reviews report no remaining findings; `./gradlew check --no-daemon` passes after the cleanup.
