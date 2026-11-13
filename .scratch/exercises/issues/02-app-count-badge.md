# 02: AppCountBadge

**What to build:** a small rounded badge showing a number, for the Exercise count on Exercise category rows. 28dp
high, fully rounded, `surfaceContainerHighest`, 13sp medium, `onSurfaceVariant`. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** done

- [x] `AppCountBadge` in `:core:design-system`, following `android-compose-components`.
- [x] One preview, snapshotted with Paparazzi; reference image recorded and committed.
- [x] `./gradlew check` passes.

## Mutation testing

The only production Kotlin is in `:core:design-system`, which the skill leaves out (screenshot tests cover it); no
mutants run.
