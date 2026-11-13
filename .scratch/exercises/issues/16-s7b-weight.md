# 16: S7b Weight

**What to build:** with weight focused, − and + step it by exactly 1.5 kg within 0–300 kg, disabled at a limit.
Weight shows with a Polish decimal comma and no „,0”: „0 kg”, „1,5 kg”, „3 kg”, „13,5 kg”. Stored as whole grams. See
`../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** ready-for-agent

- [ ] Cases, test-first, on both channels: + shows „1,5 kg” then „3 kg”; weight limits 0 kg and 300 kg; ± changes only
      the focused weight of the focused row.
- [ ] No unit test for formatting (spec: cases plus mutation testing cover it).
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
