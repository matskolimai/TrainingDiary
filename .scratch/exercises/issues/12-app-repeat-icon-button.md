# 12: AppRepeatIconButton

**What to build:** a tonal 40dp icon button for − and +, with a disabled state. Hold-to-repeat is **not** part of this
ticket; ticket 18 adds it. See `../spec.md` (Design system).

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] `AppRepeatIconButton(icon, contentDescription, enabled, onClick)` in `:core:design-system`; `remove` and `add`
      icons in `AppIcons` if missing.
- [ ] Previews for enabled and disabled, snapshotted.
- [ ] `./gradlew check` passes.
