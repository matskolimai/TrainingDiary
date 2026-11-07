# 05: Acceptance-test harness (risk check)

**What to build:** an instrumented test can launch the real app with JUnit 6 on a headless Pixel 9 Gradle Managed
Device. This proves the riskiest test infrastructure early. The Koin extension and the in-memory database come in
ticket 09, once there is a graph to override. See the spec's "Acceptance tests".

**Blocked by:** 04 (Module skeletons)

**Status:** ready-for-agent

- [ ] `AcceptanceTestRunner` swaps in `AcceptanceTestApplication`
- [ ] JUnit 6 on device through mannodermaus' Compose extension
- [ ] Gradle Managed Device with the Pixel 9 profile, headless
- [ ] Packages `…acceptance.trainings`, `…acceptance.dsl`, `…acceptance.driver` in `:app`'s instrumented tests
- [ ] One throwaway smoke test: the app launches under `AcceptanceTestApplication` (ticket 12 replaces it)
- [ ] No Test Orchestrator
- [ ] `./gradlew check` and the managed-device test task are green
- [ ] On failure, stop and report; switch only to the approved fallbacks: JUnit 4 instead of JUnit 6, and
      `connectedDebugAndroidTest` on a running emulator instead of the managed device
