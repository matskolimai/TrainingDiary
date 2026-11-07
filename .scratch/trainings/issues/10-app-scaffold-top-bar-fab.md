# 10: AppScaffold + AppTopBar + AppFab

**What to build:** the design-system pieces for the Training list's screen frame, which always travel together. See
the spec's "Design system" and "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppScaffold` wrapping the M3 scaffold, with parameters for the top bar, FAB and snackbar host (the FAB moves
      above a shown snackbar)
- [ ] `AppTopBar`: centre-aligned top bar with a title
- [ ] `AppFab`: standard 56dp, 16dp corners, `primaryContainer`, with icon and content-description parameters
- [ ] App-agnostic and public, no app strings; one `internal` preview each through `AppPreview`
- [ ] Snapshot tests for every preview; reference images committed
- [ ] `./gradlew check` is green
