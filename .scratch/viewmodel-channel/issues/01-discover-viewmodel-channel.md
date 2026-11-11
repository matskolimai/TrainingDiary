# 01: Discover the ViewModel channel

**What to decide:** whether and how the acceptance cases also run on the JVM with a ViewModel as the system under
test (a second driver under the existing DSL), so scenarios the UI cannot stage get tested. This is a seed for a
`/grill-with-docs` session that produces `spec.md` and tickets; it is not ready to implement.

**Status:** done

## Why

On 2026-11-10 five source mutants were applied by hand to `TrainingEditorViewModel`, each run against
`TrainingEditorAcceptanceTest` on the `pixel9` managed device (77-102 s per mutant). Four survived:

| Mutant | Change | Why the device suite cannot kill it |
| ------ | ------ | ----------------------------------- |
| M1 | Remove `_state.update { it.copy(isSaving = true) }` in `save()` | Needs a second tap before the editor closes |
| M2 | Remove `hasLoaded = true` in `load()` | Needs the app backgrounded over 5 s mid-edit |
| M3 | Remove `if (!state.canSave) return` in `save()` | The disabled button hides it; same double tap as M1 |
| M4 | Remove `navigator.back(key)` when the Training is missing | The Training list never opens a missing Training |

The control mutant (typing a name does not update state) was killed.

All four are easy to stage against a ViewModel with a fake repository and a fake navigator. A throwaway JVM test
that mirrored six Training editor acceptance cases translated almost one-to-one, which suggests a shared DSL is
feasible.

## Decided already

- The `save()` guard (M3) stays: it is what enforces the double-tap protection when two taps land before the
  Screen redraws.
- M1-M4 become the first cases of the ViewModel channel, not standalone tickets.
- Pitest is dropped. It ran on `:feature:trainings:presentation` (`pl.droidsonroids.pitest` 0.3.0, pitest 1.30.2)
  but most unkilled mutants were coroutine noise, and it never generated M1.
- Robolectric is ruled out as a way to run the device suite on the JVM.
- The preferred shape is one DSL with two drivers, not a second DSL.

## Open questions

- Where does the shared DSL live once it leaves `app/src/androidTest`?
- Is the system under test one ViewModel, or the ViewModels of a feature wired together?
- Fake repository, or the real repository over in-memory Room on the JVM?
- How is navigation faked for cases that cross Screens?
- Which cases run on which channel: every case on the ViewModel channel and a tagged subset on device, or a split?
- How do the DSL assertions that are about the UI (focus, a disabled button) map to ViewModel state?
- Does the mutation-testing skill run mutants against the ViewModel channel, the device channel, or both?

## Comments

### 2026-11-10: grilling, round 1-2, and the prototype

Decided in the grilling session:

- Real repository over in-memory Room on the JVM, not a fake.
- Every case runs on both channels by default; a case only one channel can stage is tagged for that channel.
- M4 goes: the missing-Training branch becomes `checkNotNull(repository.getTraining(trainingId))`.
- Test vocabulary (channel, driver, case) goes in the `android-acceptance-testing` skill, not `GLOSSARY.md`.
- Mutation testing runs mutants on the ViewModel channel first, and only survivors on device; mutants in Screen
  or Root composables go straight to device.
- New steps: `trainingEditor.saveTwiceQuickly()` (M1, M3) and `leaveAppFor(10.seconds)` (M2), both JVM-only.

Prototype of option (c), a headless app over the real Koin graph: branch `prototype/viewmodel-channel`, commit
`4a67e9e`. JVM: 12 passed, 1 skipped, about 1 s. Device: 11 passed, 2 skipped. The JVM channel kills M1, M2, M3
and the control mutant. Room runs on the JVM with the bundled driver, with no Robolectric and no `Context`.

### Discovery completed

The questions above are resolved in [the spec](../spec.md) and
[ADR-0001](../../../docs/adr/0001-acceptance-cases-on-two-channels.md). Implementation tickets 02-10 are
complete and merged to `master`; both external skills are updated on their repository's `master` too.
