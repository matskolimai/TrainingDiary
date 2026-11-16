# 17: S7c Timed Exercises

**What to build:** for timed Exercises (Plank, Deska boczna) the reps pill shows „12 s” instead of „12 powt.”,
with the same start, step and limits. See `../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** done

- [x] Cases, test-first, on both channels: a timed Exercise shows „12 s”; a non-timed one shows „12 powt.”.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Validation

- Two cases (Plank in seconds, Brzuszki in repetitions) over a new `assertRepsUnit(RepsUnit)` step were written
  first; the timed case failed on the ViewModel channel (17 run, 1 failed), then passed.
- `SetEditorState.isTimed` comes from the catalogue Exercise, looked up once in the ViewModel and reused for „Dodaj
  do treningu”. Start, step and limits share `ExerciseSet.stepped`, so they are unchanged for timed Exercises.
- The device driver's reps pill matcher accepts both units, so focusing reps works for timed Exercises.
- `SetCard` takes `isTimed` as a sixth parameter (`LongParameterList` suppressed with a reason) and the file
  suppresses `TooManyFunctions` for the new preview. A timed preview snapshot was added and inspected; the others
  are unchanged.
- `check pixel9DebugAndroidTest` passed: 38 device cases, 2 expected skips, no failures. It passed again after the
  review fix.

## Mutation testing

| Mutant | Outcome | Channel |
|--------|---------|---------|
| ViewModel never sets `isTimed` in the state | KILLED | ViewModel |
| Reps pill always uses „powt.” | KILLED | Device |
| − / + shown on every card except the focused one | KILLED | Device |
| − wired to the increment action | KILLED | Device |
| Reps pill tap focuses the weight | KILLED | Device |

The first run of the „powt.” mutant ended in a build error (the Gradle daemon was stopped externally); the rerun
killed it. `mutant.sh status` reported `clean`. The three control and focus mutants ran against the `SetCard`
refactor that the review then reverted; the label mutant's line is unchanged in substance.

## Code review

Review baseline: commit `e6d80e5`. Standards and spec axes ran in independent fresh agents.

### Standards

One hard finding, fixed: the refactored `SetCard` took the screen State and `onAction`, which components must not
(android-compose-components, „Parameters and state”). It is back to value parameters plus `isTimed`, with
`ValueControls` restored; detekt's limits are met by the two suppressions above. Kept as judgement calls:
`isTimed: Boolean` in State instead of the whole Exercise (one flag needed), and the test-side `RepsUnit` enum.

### Spec

No findings and no unasked user-visible additions; the „s” label is a kept addition in the spec. The ViewModel
channel checks the flag and the device channel the „s” / „powt.” text.

Review outcome: Standards 0 remaining hard findings; Spec 0 findings.
