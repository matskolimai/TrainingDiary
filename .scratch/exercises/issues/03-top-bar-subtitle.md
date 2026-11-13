# 03: Subtitle on AppTopBarWithBack

**What to build:** the top bar with a back arrow can show a subtitle under its title, for step lines such as
„Krok 1 z 3: kategoria”. Title 22sp, subtitle bodyMedium 14sp `onSurfaceVariant`. Without a subtitle the bar looks
exactly as today. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** wontfix

- [ ] `AppTopBarWithBack` takes an optional subtitle; existing callers are unchanged.
- [ ] A preview with a subtitle is added and snapshotted; the existing snapshot is unchanged.
- [ ] `./gradlew check` passes.

## Comments

Implemented in 8aa162e, then reverted: the user doesn't need the information under the title, so the top bar keeps
only its title. The step lines „Krok N z 3” are dropped from the spec.
