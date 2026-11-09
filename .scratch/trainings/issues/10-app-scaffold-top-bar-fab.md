# 10: AppScaffold + AppTopBar + AppFab

**What to build:** the design-system pieces for the Training list's screen frame, which always travel together. See
the spec's "Design system" and "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** done

- [x] `AppScaffold` wrapping the M3 scaffold, with parameters for the top bar, FAB and snackbar host (the FAB stays
      in place when a snackbar shows, standard M3)
- [x] `AppTopBar`: centre-aligned top bar with a title
- [x] `AppFab`: standard 56dp, 16dp corners, `primaryContainer`, with icon and content-description parameters
- [x] App-agnostic and public, no app strings; one `internal` preview each through `AppPreview`
- [x] Snapshot tests for every preview; reference images committed
- [x] `./gradlew check` is green
