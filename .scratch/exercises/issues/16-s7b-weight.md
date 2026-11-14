# 16: S7b Weight

**What to build:** with weight focused, − and + step it by exactly 1.5 kg within 0–300 kg, disabled at a limit.
Weight shows with a Polish decimal comma and no „,0”: „0 kg”, „1,5 kg”, „3 kg”, „13,5 kg”. Stored as whole grams. See
`../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** done

- [x] Cases, test-first, on both channels: + shows „1,5 kg” then „3 kg”; weight limits 0 kg and 300 kg; ± changes only
      the focused weight of the focused row.
- [x] No unit test for formatting (spec: cases plus mutation testing cover it).
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Validation

- Three weight cases were written first and failed on the ViewModel channel (15 run, 3 failed), then passed.
  The S7a case that asserted weight ± does nothing now checks the focused weight of the focused row.
- The device driver's weight pill matcher now matches any weight built from the resource format, not only „0 kg”.
- New, minimum and maximum Set count snapshots were re-recorded (weight − is disabled at 0 kg); a maximum weight
  preview („1,5 kg”, „300 kg” with + disabled, „13,5 kg”) was added. All were inspected.
- `check pixel9DebugAndroidTest` passed: 36 device cases, 2 expected skips, no failures. It passed again after the
  review fixes.

## Mutation testing

| Mutant | Outcome | Channel |
|--------|---------|---------|
| Flip the weight/reps branch when stepping | KILLED | ViewModel |
| Drop the direction from the weight step (− adds 1.5 kg) | KILLED | ViewModel |
| Remove the 0–300 kg clamp | KILLED | ViewModel |
| Keep weight + enabled at 300 kg | KILLED | ViewModel |
| Show a decimal point instead of the Polish comma | KILLED | Device |

After the review moved stepping into one `ExerciseSet.stepped` used by both the ViewModel and the enabled flags,
three mutants were run against the new code:

| Mutant | Outcome | Channel |
|--------|---------|---------|
| Remove the weight clamp in `stepped` | KILLED | ViewModel |
| Value controls always enabled | KILLED | ViewModel |
| Step every Set except the focused one | KILLED | ViewModel |

`mutant.sh status` reported `clean`. Stale Gradle daemon JAR locks were cleared with `gradlew --stop`.

## Code review

Review baseline: commit `57ca0ac`. Standards and spec axes ran in independent fresh agents.

### Standards

No hard violations. Acted on: the field switch and limits were repeated in the ViewModel and in both enabled
flags; they now live in one `ExerciseSet.stepped`, and the flags check whether a step would change the Set.
`GRAM_DECIMALS_PER_KG` was replaced by `GRAMS_PER_KG` with a division. Kept: the focused-value case still checks
that the controls move to Set 3 and covers the „13,5 kg” display, which overlaps the other weight cases a little.

### Spec

No findings and no unasked user-visible additions. The reviewer noted that the reps pill shifts with weight
width („1,5 kg” vs „300 kg”); the spec sets no fixed pill width, so it stays as is.

Review outcome: Standards 0 remaining hard findings; Spec 0 findings.
