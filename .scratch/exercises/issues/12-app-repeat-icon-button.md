# 12: AppRepeatIconButton

**What to build:** a tonal 40dp icon button for − and +, with a disabled state. Hold-to-repeat is **not** part of this
ticket; ticket 18 adds it. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [x] `AppRepeatIconButton(icon, contentDescription, enabled, onClick)` in `:core:design-system`; `remove` and `add`
      icons in `AppIcons` if missing.
- [x] Previews for enabled and disabled, snapshotted.
- [x] `./gradlew check` passes.

## Mutation testing

Skill run; its diff leaves out `:core:design-system` (screenshot-covered), so no production Kotlin
remained to mutate. The enabled and disabled snapshots cover the button.
