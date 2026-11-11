# 03: Tag cases for one channel

**What to build:** a case that only one channel can stage can be tagged for that channel, and is then skipped, not
failed, on the other channel. A driver step its channel cannot stage fails loudly instead of passing silently. The
"ready to type" case is tagged device-only, because focus is set by the Screen and is not in ViewModel state. See
`../spec.md` (Channel tags).

**Blocked by:** 02 (Put the acceptance cases behind driver interfaces).

**Status:** done

- [x] A channel enum with device and ViewModel; a channel's base test class names its channel.
- [x] `@OnlyOn(channel)` on a case skips it, reported as skipped, on any other channel, through a JUnit execution
      condition registered on the common cases interface.
- [x] A dedicated exception for a step a channel cannot stage, whose message names the step and says to tag the case.
- [x] "opening the new training screen shows an empty name ready to type" is tagged device-only.
- [x] The device still runs all 11 cases, all passing; `./gradlew check` passes.

## Comments

- `AcceptanceCases` declares `val channel: Channel`, so the compiler makes each channel's base class name its channel.
  `ChannelCondition` reads it from the test instance, which JUnit builds before it evaluates a test method's
  conditions.
- `Channel` and `@OnlyOn` are in `acceptance`, next to the cases that use them. `ChannelCondition` and
  `CannotStageException` are in `acceptance.driver`, with the other JUnit plumbing.
- Nothing throws `CannotStageException` yet. The ViewModel driver's focus check throws it in 05, and the device
  driver's new steps throw it in 06 and 07.
- Not tested separately. A throwaway JVM class (deleted) ran `TrainingEditorCases` with a no-op `app`: with the
  ViewModel channel, 7 cases with 1 skipped (ready to type); with the device channel, 7 cases with 0 skipped. Ticket
  05's "6 pass, 1 skipped" will cover this for good.
- Gate: `./gradlew check` passes. On `pixel9DebugAndroidTest`, TrainingEditorAcceptanceTest ran 7 cases and
  TrainingListAcceptanceTest ran 4: 0 failed, 0 skipped.
