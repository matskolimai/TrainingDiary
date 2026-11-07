# 06: Paparazzi wiring (risk check)

**What to build:** a preview can be snapshotted and verified in `./gradlew check`. This proves early that the
Paparazzi alpha renders with AGP 9.5 / Compose 1.12. See the spec's "Screenshot tests".

**Blocked by:** 04 (Module skeletons)

**Status:** ready-for-agent

- [ ] `convention.paparazzi` (Paparazzi 2.0.0-alpha5.1 + JUnit 4 + vintage engine), applied only to modules with
      screenshot tests; first applied to `:core:design-system`
- [ ] One configuration: Pixel 9 (custom device config if there is no constant), dark, `pl`, font scale 1.0
- [ ] One trivial `internal` preview, snapshotted by a test that calls it directly (ticket 07 replaces it)
- [ ] Reference images committed in the module's snapshot directory, plain git
- [ ] `check` runs `verifyPaparazziDebug`
- [ ] On failure, stop and report to the user: there is no agreed fallback
