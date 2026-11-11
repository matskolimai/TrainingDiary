# 06: Saving twice quickly adds the Training once

**What to build:** a case proving that two taps on save, landing before the Training editor redraws, add the
Training only once. It runs on the ViewModel channel only, because the device cannot time two taps before a redraw.
It kills M1 (no `isSaving` update in save) and M3 (no `canSave` guard in save). See `../spec.md` (New cases).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** ready-for-agent

- [ ] A `saveTwiceQuickly` step on the Training editor DSL and driver interface.
- [ ] The ViewModel driver sends two save actions before settling, then requires the editor is no longer on top.
- [ ] The UI driver throws the cannot-stage exception.
- [ ] Case "saving the new training screen twice quickly adds the training once", tagged ViewModel-only: open new,
      enter a name, save twice quickly, the Training is listed once.
- [ ] Re-applying M1 and M3 by hand each fails the case; with production code restored it passes.
- [ ] `./gradlew check` and the device suite (case skipped) pass.
