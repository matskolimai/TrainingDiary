# 11: S5 Save with Training exercises

**What to build:** „Zapisz” stores the name and all Training exercises with their Sets together, in add and edit
mode; reopening the Training shows them in the same order. Leaving the Training editor without saving stores nothing.
See `../spec.md` (Saving).

**Blocked by:** 09 (S4 Add to Training), 10 (Storage of Training exercises).

**Status:** ready-for-agent

- [ ] The Training editor loads a Training's Training exercises in edit mode and passes the list on save.
- [ ] Cases, test-first, on both channels: „Zapisz” stores them and reopening the Training shows them; leaving
      without saving stores nothing.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
