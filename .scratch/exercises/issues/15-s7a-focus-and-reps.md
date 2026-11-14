# 15: S7a Focus and reps

**What to build:** exactly one value in the Set editor is focused, shown by the focused pill. It opens on Set 1's
weight; tapping a pill focuses it; removing the focused Set moves focus to the same field of the new last Set. Only
the focused row shows − and + at its right end. For reps they step by 1 within 1–999, disabled at a limit. Weight ±
does nothing yet (ticket 16). See `../spec.md` (Product rules).

**Blocked by:** 14 (S6 Number of Sets).

**Status:** done

- [x] Focus (Set index + field) is ViewModel State.
- [x] ± buttons are `AppRepeatIconButton`s, shown on the focused row only.
- [x] Cases, test-first, on both channels: the editor opens with Set 1's weight focused; ± changes only the focused
      reps of the focused row; reps limits 1 and 999; removing the focused Set moves focus to the new last Set.
- [x] Previews updated and snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Validation

- Initial focus was test-first (missing State fields caused the initial compilation failure); focus selection,
  focused reps changes, reps limits and focus relocation each failed before their implementations, then passed.
- All 13 Set editor cases pass on the ViewModel channel.
- New, minimum and maximum Set count previews were updated; minimum and maximum reps previews were added.
  All five previews were recorded and visually inspected.
- `check` passed, including static analysis, JVM acceptance tests and Paparazzi verification.
- `check pixel9DebugAndroidTest` passed; the full device suite has 34 cases, 2 expected skips and no failures.

- After review fixes, `check` and all 13 Set editor device cases passed again, including refocusing edited reps.

## Mutation testing

| Mutant | Outcome | Channel |
|--------|---------|---------|
| Drop focus relocation when removing the focused Set | KILLED | ViewModel |
| Let weight controls change reps | KILLED | ViewModel |
| Change every Set except the focused Set | KILLED | ViewModel |
| Remove the reps clamp | KILLED | ViewModel |
| Wire the reps pill to weight focus | KILLED | Device |

All mutants were restored; `mutant.sh status` reported `clean`. A stale Gradle daemon's Windows JAR lock was
cleared with `gradlew --stop` before retrying the first mutant.

## Code review

Review baseline: starting commit `c5f32cb`. Standards and spec axes ran in independent fresh agents.

### Standards

The reviewer found that reps focus steps assumed 12 reps. The driver now matches the displayed integer against
its resource format, and the shared case refocuses a Set after changing it to 13 reps. Duplicate focus assertions
were consolidated in both drivers. The re-review confirmed no remaining hard violations or substantive smells.

The optional trailing slot was retained as a judgment call: it mirrors AppListRow and keeps screen control rules
out of the private Set card without adding a callback holder or a large parameter list. The reviewer accepted it.

### Spec

No findings and no unasked user-visible additions. Weight adjustment, timed labels and hold repetition stay in
S7b (ticket 16).

Review outcome: Standards 0 remaining findings; Spec 0 findings.
