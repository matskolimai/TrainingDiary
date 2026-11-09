# 17: AppListRow

**What to build:** the separated, rounded list row that Training rows are built from. See the spec's "Visual
details".

**Blocked by:** 07 (Design-system foundation)

**Status:** done

- [x] `AppListRow` built from `Surface` + `Row` (not M3 `ListItem`): `surfaceContainer`, 16dp corners, min height
      64dp, padding 16dp start / 4dp end, 16dp between elements
- [x] Slots for the main content and a trailing element; the row itself isn't clickable
- [x] App-agnostic and public; `internal` previews (short text, long wrapping text) through `AppPreview`,
      snapshotted; reference images committed
- [x] `./gradlew check` is green
