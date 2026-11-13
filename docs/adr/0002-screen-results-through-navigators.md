# Screen results travel through the navigators, not Navigation 3's ResultEventBus

The Set editor hands a Training exercise back to the Training editor's unsaved changes, the first Screen that returns
a value. Results go ViewModel to ViewModel through the screen navigators: the sender calls one back-with-result method,
`:app` implements it with `AppNavigator.popTo(keyType, result)`, and the receiver's navigator exposes a `Flow` of its
result type that its ViewModel collects from `init`. `AppNavigator` broadcasts every `NavResult` on a `SharedFlow`,
emitting it before the pop request. We chose this over the library's `ResultEventBus` because the bus lives in
composition while our ViewModels navigate: the result would detour through the Roots and race the ViewModel's pop, and
the ViewModel acceptance channel (ADR 0001), which replaces the Roots, would test a copy of that glue.

## Considered Options

- **`ResultEventBus` in the Roots** (`navigation3-runtime` 1.2.0): official, but in-memory per composition, racing
  the pop, and invisible to the ViewModel channel.
- **A shared ViewModel scoped to the Training editor's entry** (nav3-recipes `sharedviewmodel`): a custom decorator,
  the editor's key carried through both pickers, and the same Root glue as the bus.
- **A result class per receiver bound in Koin**: worked, but sending and popping were two calls; the user wanted
  results to read as navigation.
- **A `Channel` instead of a `SharedFlow`**: hands each result to one collector, so with two receivers the wrong one
  could take and drop it.

## Consequences

- `NavResult` is a marker interface in `:core:presentation`, not sealed: each feature owns its results, sealed per
  returning Screen (`SetEditorResult`).
- A result emitted while no ViewModel collects is lost. That holds because the Screen a result returns to is below on
  the back stack and collects for its whole life; process death loses unsaved input anyway.
- Results match by Kotlin type; two live receivers of one type would both take it. If that becomes possible, the
  result carries the target key.
