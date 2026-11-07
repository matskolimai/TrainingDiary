# 11: AppEmptyState

**What to build:** a reusable centred empty-state component for the Training list. See the spec's "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppEmptyState`: centred, dimmed 64dp icon, headline (22sp, `onSurface`) and body (14sp, `onSurfaceVariant`);
      icon and texts are parameters
- [ ] Built from `Surface` / `Column`, not an M3 counterpart
- [ ] App-agnostic and public; one `internal` preview through `AppPreview`, snapshotted; reference image committed
- [ ] `./gradlew check` is green
