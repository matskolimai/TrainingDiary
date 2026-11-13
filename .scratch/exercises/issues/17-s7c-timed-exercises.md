# 17: S7c Timed Exercises

**What to build:** for timed Exercises (Plank, Deska boczna) the reps pill shows „12 s” instead of „12 powt.”, with
the same start, step and limits. See `../spec.md` (Product rules).

**Blocked by:** 15 (S7a Focus and reps).

**Status:** ready-for-agent

- [ ] Cases, test-first, on both channels: a timed Exercise shows „12 s”; a non-timed one shows „12 powt.”.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
