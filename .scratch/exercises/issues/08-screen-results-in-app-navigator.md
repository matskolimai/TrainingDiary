# 08: Screen results in AppNavigator

**What to build:** the navigation layer can pop back to a Screen of a given key type and hand it a result, as
decided in ADR 0002. No visible change. Scaffolding: no tests; S4's cases (ticket 09) are its tests.

From ADR 0002 / wayfinding ticket 07:

- `interface NavResult` (marker, not sealed) in `:core:presentation`.
- `AppNavigator.results: Flow<NavResult>` backed by `MutableSharedFlow(extraBufferCapacity = 1)`.
- `AppNavigator.popTo(keyType, result: NavResult? = null)` emits the result **before** queuing `NavRequest.PopTo`,
  which removes every entry above the topmost entry of that key type.

**Blocked by:** None (can start immediately).

**Status:** done

- [x] `NavResult`, `results` and `popTo` exist as above; `push` and `pop` are unchanged.
- [x] `AppNavDisplay` applies `PopTo` through the existing back-stack guards.
- [x] `HeadlessApp` applies `PopTo` the same way.
- [x] `./gradlew check` and the device suite pass.

## Mutation testing

Not run. No screen navigator calls `popTo` and nothing collects `results` until ticket 09, so every mutant in this
diff (`AppNavigator.popTo`, `NavRequest.PopTo`, the `popTo` guard, the `PopTo` branch in `AppNavDisplay`) would
survive for want of a caller. Ticket 09 mutates these lines alongside its own diff, once S4's cases exercise them.
