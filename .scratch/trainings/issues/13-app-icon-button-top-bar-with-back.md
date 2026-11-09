# 13: AppIconButton + AppTopBarWithBack

**What to build:** a 48dp icon button and the small top bar with a back arrow that the Training editor needs. See
the spec's "Design system" and "Visual details".

**Blocked by:** 07 (Design-system foundation)

**Status:** done

- [x] `AppIconButton`: an M3 icon button at the default 48dp, with icon and content-description parameters
- [x] `AppTopBarWithBack`: small top bar with a title and a back callback; ← built on `AppIconButton`, with the
      content description `design_system_cd_back` („Wstecz”); no actions
- [x] App-agnostic and public; one `internal` preview each through `AppPreview`, snapshotted; reference images
      committed
- [x] `./gradlew check` is green
