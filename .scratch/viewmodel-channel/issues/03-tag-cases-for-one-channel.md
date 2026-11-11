# 03: Tag cases for one channel

**What to build:** a case that only one channel can stage can be tagged for that channel, and is then skipped, not
failed, on the other channel. A driver step its channel cannot stage fails loudly instead of passing silently. The
"ready to type" case is tagged device-only, because focus is set by the Screen and is not in ViewModel state. See
`../spec.md` (Channel tags).

**Blocked by:** 02 (Put the acceptance cases behind driver interfaces).

**Status:** ready-for-agent

- [ ] A channel enum with device and ViewModel; a channel's base test class names its channel.
- [ ] `@OnlyOn(channel)` on a case skips it, reported as skipped, on any other channel, through a JUnit execution
      condition registered on the common cases interface.
- [ ] A dedicated exception for a step a channel cannot stage, whose message names the step and says to tag the case.
- [ ] "opening the new training screen shows an empty name ready to type" is tagged device-only.
- [ ] The device still runs all 11 cases, all passing; `./gradlew check` passes.
