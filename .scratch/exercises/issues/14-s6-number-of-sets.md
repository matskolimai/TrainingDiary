# 14: S6 Number of Sets

**What to build:** the Set editor shows a „Serie” row with the count stepper under the top bar. + appends a Set
copying the last Set's weight and reps; − removes the last Set. 1–10 Sets: − is disabled at 1, + at 10. See
`../spec.md` (Product rules, Set editor visuals).

**Blocked by:** 11 (S5 Save with Training exercises), 13 (AppCountStepper).

**Status:** ready-for-agent

- [ ] Container `surfaceContainerLow`, 16dp corners, 8dp top / 16dp bottom / 16dp side margins, „Serie” 16sp.
- [ ] „Dodaj do treningu” carries the changed Sets, so the card shows „N serie”.
- [ ] Cases, test-first, on both channels: + adds a Set copying the last; − removes the last; limits 1 and 10.
- [ ] Previews updated and snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
