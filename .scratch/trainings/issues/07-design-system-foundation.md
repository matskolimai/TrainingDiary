# 07: Design-system foundation

**What to build:** the app is dark everywhere, including the system bars, and later components have a theme,
spacing, icons and a preview wrapper to build on. See the spec's "Design system".

**Blocked by:** 06 (Paparazzi wiring)

**Status:** ready-for-agent

- [ ] `AppTheme(content)`: dark only, plain `darkColorScheme()`, default `Typography()` and `Shapes()`
- [ ] `MainActivity` wraps its content in `AppTheme` and calls `enableEdgeToEdge` with dark system-bar styles; the XML
      window theme has a dark parent
- [ ] `Spacing` object (4/8/12/16/24/32dp)
- [ ] `AppIcons` object exposing Outlined `ImageVector`s from `material-icons-extended`: add, more_vert, edit, delete,
      arrow_back, fitness_center
- [ ] Public `AppPreview { }` wrapper
- [ ] A theme preview snapshot replaces the trivial one from ticket 06; reference images committed
- [ ] `./gradlew check` is green and the app launches with dark system bars
