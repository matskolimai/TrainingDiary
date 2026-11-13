# 23: S11 Discard dialog, Set editor

**What to build:** the same discard dialog on the Set editor, in add and edit mode. Add mode starts from 3 × 0 kg ×
12. „Odrzuć” in add mode returns to the Exercise picker; in edit mode to the Training editor. See `../spec.md`.

**Blocked by:** 22 (S10 Discard dialog, Training editor).

**Status:** ready-for-agent

- [ ] Cases, test-first, on both channels: back after a change asks, in both modes; „Odrzuć” in add mode returns to
      the Exercise picker; „Anuluj” stays; an undone change doesn't ask.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
