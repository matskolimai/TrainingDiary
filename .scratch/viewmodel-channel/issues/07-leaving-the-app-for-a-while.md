# 07: Leaving the app for a while keeps the typed name

**What to build:** a case proving that when the user leaves the app for longer than the Training editor keeps its
state alive and then comes back, the name being typed is still there. It runs on the ViewModel channel only, where the
absence costs virtual time, not real seconds. It kills M2 (no `hasLoaded` in load). See `../spec.md` (ViewModel
channel, New cases).

**Blocked by:** 05 (Run the Training editor cases on the JVM).

**Status:** ready-for-agent

- [ ] An app driver interface with `leaveAppFor(duration)`, exposed as `leaveAppFor` on `AppDsl`.
- [ ] The headless app's `leaveFor(duration)` stops collecting, advances virtual time by the duration, then resumes
      collecting the top entry, as the Activity going to the background and back does.
- [ ] The device's app driver throws the cannot-stage exception.
- [ ] Case "leaving the app for a while keeps the name being typed", tagged ViewModel-only: given a Training, open it
      for editing, replace the name, leave the app for 10 s, the editor shows the replaced name.
- [ ] Re-applying M2 by hand fails the case; with production code restored it passes.
- [ ] `./gradlew check` and the device suite (case skipped) pass.
