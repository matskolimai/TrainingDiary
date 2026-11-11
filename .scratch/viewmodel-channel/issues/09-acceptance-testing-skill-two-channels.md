# 09: Teach `android-acceptance-testing` about the two channels

**What to build:** the `android-acceptance-testing` skill (in the user's skills directory, outside this repo)
describes acceptance cases on two channels, so the next feature's cases are written that way without rediscovering
it. See `../spec.md` (Vocabulary, Skills) and `docs/adr/0001-acceptance-cases-on-two-channels.md`.

**Blocked by:** 06 (Saving twice quickly adds the Training once), 07 (Leaving the app for a while keeps the typed
name).

**Status:** ready-for-agent

- [ ] Defines channel, driver and case; one DSL with a driver per channel.
- [ ] States that every case runs on both channels unless tagged with `@OnlyOn`, and that a step a channel cannot
      stage throws the cannot-stage exception rather than passing.
- [ ] Describes the headless app: what it stands in for, what it mirrors from `AppNavDisplay`, and that everything
      below it is the real graph over in-memory Room.
- [ ] Lists the add-a-Screen checklist: DSL, driver interface, a driver per channel, a one-line test class per
      channel, and registering the Screen's ViewModel in the headless app.
- [ ] Follows the `writing-for-agents` skill; lines at most 120 characters.
