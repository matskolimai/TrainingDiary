# 06: AppValuePill

**What to build:** a tappable pill showing a value such as „0 kg” or „12 powt.”, with a focused state. 40dp high,
12dp corners, 16sp; focused = `secondaryContainer` fill and a 2dp `primary` border. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** done

- [x] `AppValuePill(text, focused, onClick)` in `:core:design-system`.
- [x] Previews for focused and unfocused, snapshotted.
- [x] `./gradlew check` passes.

## Mutation testing

Skill run; its diff leaves out `:core:design-system` (screenshot-covered), so no production Kotlin
remained to mutate. The focused and unfocused snapshots cover the pill.
