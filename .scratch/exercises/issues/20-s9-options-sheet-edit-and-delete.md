# 20: S9 ⋮ sheet: Edytuj and Usuń

**What to build:** each Training exercise card gets a trailing ⋮ („Więcej opcji”) that opens the same bottom sheet as
the Training list: „Edytuj” opens the Set editor like a row tap; „Usuń” removes the Training exercise from the
unsaved list immediately, without undo. „Zapisz” stores the removal. See `../spec.md`.

**Blocked by:** 19 (S8 Edit by row tap).

**Status:** ready-for-agent

- [ ] The sheet reuses `AppBottomSheet` / `AppSheetItem` (`edit` „Edytuj”, `delete` „Usuń”).
- [ ] Cases, test-first, on both channels: ⋮ → „Edytuj” opens the Set editor with its Sets; ⋮ → „Usuń” removes it;
      saving stores the removal.
- [ ] Previews updated and snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
