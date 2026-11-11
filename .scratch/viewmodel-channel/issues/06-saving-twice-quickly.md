# 06: Saving twice quickly adds the Training once

**What to build:** a case proving that two taps on save, landing before the Training editor redraws, add the
Training only once. It runs on the ViewModel channel only, because the device cannot time two taps before a redraw.
It kills M1 (no `isSaving` update in save) and M3 (no `canSave` guard in save). See `../spec.md` (New cases).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** done

- [x] A `saveTwiceQuickly` step on the Training editor DSL and driver interface.
- [x] The ViewModel driver sends two save actions before settling, then requires the editor is no longer on top.
- [x] The UI driver throws the cannot-stage exception.
- [x] Case "saving the new training screen twice quickly adds the training once", tagged ViewModel-only: open new,
      enter a name, save twice quickly, the Training is listed once.
- [x] Re-applying M1 and M3 by hand each fails the case; with production code restored it passes.
- [x] `./gradlew check` and the device suite (case skipped) pass.

## Comments

- Added the ViewModel-only case through the shared DSL. The driver sends two save actions to the same editor
  before settling, then requires that the editor has closed. The list assertion requires exactly one Training.
- The new case first failed at the unfinished driver step, then passed with the complete JVM editor class:
  seven passed and the device-only focus case skipped.
- Hand-applied M1 (remove the `isSaving` update) and M3 (remove the `canSave` guard) separately. Each failed the new
  case because the list contained `[Push day, Push day]` instead of `[Push day]`.
- The production source was restored byte-for-byte after each mutant; no production Kotlin change remains.
- Gate: `./gradlew check :app:pixel9DebugAndroidTest --rerun --max-workers=2 --no-daemon` passes. The JVM editor
  class reports seven passed and one skipped; the device suite reports 11 passed and the new case skipped.
- Review: the independent Standards and Spec reviews report no findings.
