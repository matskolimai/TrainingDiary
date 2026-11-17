# 05: The last Training of a long list can still be deleted

**What to build:** a user with many Trainings can delete the last one; the add button never covers its options.
See `../spec.md`.

**Blocked by:** 02 (`given.trainings` writes to storage instead of driving the UI).

**Status:** ready-for-agent

- [ ] The Training list's device driver scrolls to a Training's row before opening its options.
- [ ] Case tagged `@OnlyOn(Channel.DEVICE)`: `the last training of a long list can still be deleted`. It stores
      20 Trainings with `given.trainings`, deletes the last and asserts the other 19 are listed in order.
- [ ] Proof: with the Training list's bottom padding removed the case fails on the device; the padding is restored.
      The result is recorded in a `## Mutation testing` table in this file.
- [ ] `./gradlew check` and the device suite pass.
