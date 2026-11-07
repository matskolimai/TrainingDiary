# 20: AppBottomSheet + AppSheetItem

**What to build:** the modal bottom sheet and its items, used for the Training options. See the spec's "Visual
details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppBottomSheet` wrapping the M3 modal bottom sheet: `surfaceContainerLow`, 28dp top corners, drag handle,
      content slot and dismiss callback; no title
- [ ] `AppSheetItem`: a 56dp item with a leading icon, text and click callback
- [ ] App-agnostic and public; `internal` previews through `AppPreview`, snapshotted; reference images committed
- [ ] `./gradlew check` is green
