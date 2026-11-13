# 22: S10 Discard dialog, Training editor

**What to build:** back (top bar or system) on the Training editor after a change asks „Odrzucić zmiany?”. „Odrzuć”
leaves and stores nothing; „Anuluj” stays with the changes. Changed = name or Training exercises differ by value from
what the Screen started with, so a change undone by hand doesn't ask; whitespace in the name counts. Without a change,
back just goes back. See `../spec.md` (Product rules, Presentation).

**Blocked by:** 20 (S9 ⋮ sheet), 21 (AppDiscardDialog).

**Status:** ready-for-agent

- [ ] A `BackHandler` in the Root sends `OnBackClick`; the ViewModel goes back or shows the dialog (State).
- [ ] Cases, test-first, on both channels: back after a change asks; „Odrzuć” leaves storing nothing; „Anuluj” stays;
      an undone change doesn't ask.
- [ ] Dialog-state preview snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
