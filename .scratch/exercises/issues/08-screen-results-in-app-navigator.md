# 08: Screen results in AppNavigator

**What to build:** the navigation layer can pop back to a Screen of a given key type and hand it a result, as
decided in ADR 0002. No visible change. Scaffolding: no tests; S4's cases (ticket 09) are its tests.

From ADR 0002 / wayfinding ticket 07:

- `interface NavResult` (marker, not sealed) in `:core:presentation`.
- `AppNavigator.results: Flow<NavResult>` backed by `MutableSharedFlow(extraBufferCapacity = 1)`.
- `AppNavigator.popTo(keyType, result: NavResult? = null)` emits the result **before** queuing `NavRequest.PopTo`,
  which removes every entry above the topmost entry of that key type.

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] `NavResult`, `results` and `popTo` exist as above; `push` and `pop` are unchanged.
- [ ] `AppNavDisplay` applies `PopTo` through the existing back-stack guards.
- [ ] `HeadlessApp` applies `PopTo` the same way.
- [ ] `./gradlew check` and the device suite pass.
