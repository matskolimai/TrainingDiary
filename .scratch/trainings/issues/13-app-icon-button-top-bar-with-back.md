# 13: AppIconButton + AppTopBarWithBack

**What to build:** a 48dp icon button and the small top bar with a back arrow that the Training editor needs. See
the spec's "Design system" and "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** ready-for-agent

- [ ] `AppIconButton`: an M3 icon button at the default 48dp, with icon and content-description parameters
- [ ] `AppTopBarWithBack`: small top bar with a title and a back callback; ← built on `AppIconButton`, with the
      content description `design_system_cd_back` („Wstecz”); no actions
- [ ] App-agnostic and public; one `internal` preview each through `AppPreview`, snapshotted; reference images
      committed
- [ ] `./gradlew check` is green
