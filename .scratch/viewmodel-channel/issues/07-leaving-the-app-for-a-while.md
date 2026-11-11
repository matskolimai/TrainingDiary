# 07: Leaving the app for a while keeps the typed name

**What to build:** a case proving that when the user leaves the app for longer than the Training editor keeps its
state alive and then comes back, the name being typed is still there. It runs on the ViewModel channel only, where the
absence costs virtual time, not real seconds. It kills M2 (no `hasLoaded` in load). See `../spec.md` (ViewModel
channel, New cases).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** done

- [x] An app driver interface with `leaveAppFor(duration)`, exposed as `leaveAppFor` on `AppDsl`.
- [x] The headless app's `leaveFor(duration)` stops collecting, advances virtual time by the duration, then resumes
      collecting the top entry, as the Activity going to the background and back does.
- [x] The device's app driver throws the cannot-stage exception.
- [x] Case "leaving the app for a while keeps the name being typed", tagged ViewModel-only: given a Training, open it
      for editing, replace the name, leave the app for 10 s, the editor shows the replaced name.
- [x] Re-applying M2 by hand fails the case; with production code restored it passes.
- [x] `./gradlew check` and the device suite (case skipped) pass.

## Comments

- `AppDsl.leaveAppFor` uses an app-level driver on each channel. The device driver throws `CannotStageException`,
  naming the step and channel and asking for a channel tag. The ViewModel driver delegates to `HeadlessApp.leaveFor`.
- Leaving cancels state and event collection, settles cancellation before advancing virtual time, then resumes
  collection of the top entry. Navigation cannot restart collection while the app is in the background.
- TDD: the new case failed at the unfinished leave step, then passed with the implementation. The complete JVM editor
  class passed seven cases and skipped the device-only focus case; the new case took about 0.02 seconds.
- Re-applying M2 by removing the `hasLoaded` check and assignment from `load()` made the new case fail: it expected
  `Leg day` but got the stored name `Nogi`. The production file was restored byte-for-byte; no production diff remains.
- Gate: `./gradlew check :app:pixel9DebugAndroidTest --no-daemon --quiet` passes. Both acceptance channels report
  11 passed and one skipped: focus on the JVM, and leaving the app on the device.
- Review against `4661557`: independent Standards and Spec reviews found no findings on either axis.
