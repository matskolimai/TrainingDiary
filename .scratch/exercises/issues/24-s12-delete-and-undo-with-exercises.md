# 24: S12 Delete and undo with exercises

**What to build:** deleting a Training on the Training list deletes its Training exercises and Sets; „Cofnij”
restores the Training with all of them, in their order. See `../spec.md` (Storage).

**Blocked by:** 11 (S5 Save with Training exercises).

**Status:** ready-for-agent

- [ ] Cases, test-first, on both channels: deleting a Training and „Cofnij” restore it with its Training exercises
      and Sets.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
