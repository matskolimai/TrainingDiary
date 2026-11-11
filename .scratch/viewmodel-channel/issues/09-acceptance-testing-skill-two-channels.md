# 09: Teach `android-acceptance-testing` about the two channels

**What to build:** the `android-acceptance-testing` skill (in the user's skills directory, outside this repo)
describes acceptance cases on two channels, so the next feature's cases are written that way without rediscovering
it. See `../spec.md` (Vocabulary, Skills) and `docs/adr/0001-acceptance-cases-on-two-channels.md`.

**Blocked by:** 06 (Saving twice quickly adds the Training once), 07 (Leaving the app for a while keeps the typed
name).

**Status:** done

- [x] Defines channel, driver and case; one DSL with a driver per channel.
- [x] States that every case runs on both channels unless tagged with `@OnlyOn`, and that a step a channel cannot
      stage throws the cannot-stage exception rather than passing.
- [x] Describes the headless app: what it stands in for, what it mirrors from `AppNavDisplay`, and that everything
      below it is the real graph over in-memory Room.
- [x] Lists the add-a-Screen checklist: DSL, driver interface, a driver per channel, a one-line test class per
      channel, and registering the Screen's ViewModel in the headless app.
- [x] Follows the `writing-for-agents` skill; lines at most 120 characters.

## Comments

- Updated `C:/Users/matsk/.claude/skills/android-acceptance-testing/SKILL.md` in the user's separate skills repository.
  Preserved its existing per-screen organisation while replacing the single-channel examples and guidance.
- Defined channel, driver and case; documented shared case interfaces, one DSL, both driver implementations,
  `@OnlyOn` skips and `CannotStageException` for unsupported steps.
- Documented the headless app's navigation guards, per-entry ViewModel stores, foreground collection, virtual time
  and real Koin graph over context-free in-memory Room. The add-a-screen checklist includes ViewModel registration.
- Applied `writing-for-agents` and `skill-creator`. The skill validator passes, all edited Markdown lines fit within
  120 characters, and whitespace checks pass. No Kotlin changed, so mutation testing does not apply.
- Gate: `./gradlew check :app:pixel9DebugAndroidTest --no-daemon --quiet` passes. The JVM channel reports 12 passed
  and one skipped; the device channel reports 11 passed and two skipped.
- Review: separate Standards and Spec reviewers report no remaining findings. The Standards review's concern about
  project-specific driver rules was resolved by labelling those details as TrainingDiary examples.
- Skill committed on the skills repository's current `master` branch as `894550d`; unrelated pending skill edits
  remain unchanged and uncommitted.
