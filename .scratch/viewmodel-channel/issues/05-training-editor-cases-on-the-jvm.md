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

**Status:** ready-for-agent

- [ ] No `TODO` remains in the ViewModel drivers.
- [ ] A one-line JVM test class runs the Training editor cases: 6 pass, 1 skipped (ready to type).
- [ ] Re-applying the control mutant (typing a name does not update state) by hand fails cases on the JVM.
- [ ] `./gradlew check` and the device suite pass.
