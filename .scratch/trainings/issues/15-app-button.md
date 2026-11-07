# 15: AppButton

**What to build:** the full-width filled button used for „Zapisz”. See the spec's "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppButton` wrapping the M3 filled button: text, `enabled` and `onClick`; 56dp high, fully rounded, label 16sp
      medium; the caller's modifier sets the width
- [ ] The disabled look is the standard M3 disabled filled button
- [ ] App-agnostic and public; `internal` previews (enabled, disabled) through `AppPreview`, snapshotted; reference
      images committed
- [ ] `./gradlew check` is green
