# 15: S7a Focus and reps

**What to build:** exactly one value in the Set editor is focused, shown by the focused pill. It opens on Set 1's
weight; tapping a pill focuses it; removing the focused Set moves focus to the same field of the new last Set. Only
the focused row shows − and + at its right end. For reps they step by 1 within 1–999, disabled at a limit. Weight ±
does nothing yet (ticket 16). See `../spec.md` (Product rules).

**Blocked by:** 14 (S6 Number of Sets).

**Status:** ready-for-agent

- [ ] Focus (Set index + field) is ViewModel State.
- [ ] ± buttons are `AppRepeatIconButton`s, shown on the focused row only.
- [ ] Cases, test-first, on both channels: the editor opens with Set 1's weight focused; ± changes only the focused
      reps of the focused row; reps limits 1 and 999; removing the focused Set moves focus to the new last Set.
- [ ] Previews updated and snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
