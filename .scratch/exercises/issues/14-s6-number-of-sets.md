# 14: S6 Number of Sets

**What to build:** the Set editor shows a „Serie” row with the count stepper under the top bar. + appends a Set
copying the last Set's weight and reps; − removes the last Set. 1–10 Sets: − is disabled at 1, + at 10. See
`../spec.md` (Product rules, Set editor visuals).

**Blocked by:** 11 (S5 Save with Training exercises), 13 (AppCountStepper).

**Status:** done

- [x] Container `surfaceContainerLow`, 16dp corners, 8dp top / 16dp bottom / 16dp side margins, „Serie” 16sp.
- [x] „Dodaj do treningu” carries the changed Sets, so the card shows „N serie”.
- [x] Cases, test-first, on both channels: + adds a Set copying the last; − removes the last; limits 1 and 10.
- [x] Previews updated and snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Validation

- Add, remove and limit cases failed before their respective implementations, then passed on the ViewModel channel.
- All 8 Set editor cases pass on both channels. The full device suite reports 29 cases, 2 expected skips,
  no failures or errors.
- `./gradlew check pixel9DebugAndroidTest` passed, including Paparazzi verification.
- New, minimum and maximum Set editor previews and the AppCountRow preview were recorded and visually inspected.
- The count row uses the existing AppCountStepper in a design-system surface with the specified style and margins.
- Focus relocation belongs to ticket 15; this ticket adds only count behavior.

## Mutation testing

| Mutant | Outcome | Channel |
|--------|---------|---------|
| Remove the decrement state update | KILLED | ViewModel |
| Allow adding an eleventh Set | KILLED | ViewModel |
| Allow removing the final Set | KILLED | ViewModel |
| Wire the count increment to decrement | KILLED | Device |

All mutants were restored; `mutant.sh status` reported `clean`.

The current app cannot stage Sets with different values: changing reps and weight arrives in tickets 15 and 16.
Both channels currently check copying and removal with the reachable default Sets; production copies `last()`
and removes with `dropLast(1)`.

## Code review

- Standards: moved repeated multi-screen setup for the four new count cases into GivenDsl, then the reviewer
  confirmed no remaining findings.
- Spec: no findings and no unasked user-visible additions. Both review axes used starting commit `53ddd8d`.
- After the setup refactor, `check` and all 8 Set editor device cases passed again.
