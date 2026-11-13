# Returning a result from one Screen to another

Type: grilling
Status: resolved
Blocked by: 05

## Question

How does one Screen hand a result back to a Screen below it on the back stack, here the Set editor's new or edited
Training exercise to the Training editor's unsaved changes? The user calls this a crucial operation to get right:
survey the options first (what Navigation 3 offers officially, its recipes, the result channel proposed in
[Architecture for Exercises and Training exercises](05-architecture.md), others), then decide with the user and
update `android-navigation` to match.

Starting point for the session: [handoff-screen-results.md](../../handoff-screen-results.md).

## Answer

Resolved with the user on 2026-11-13. Options surveyed in
[research/nav3-results.md](../../research/nav3-results.md) and compared in code in
[prototypes/screen-results.html](../../prototypes/screen-results.html) (tab „B′ · via navigator” is the winner).

- **Through the navigators, ViewModel to ViewModel.** Not Nav3's `ResultEventBus` (lives in composition, races the
  ViewModel's pop, and the ViewModel channel would test a copy of the Root glue), not a shared ViewModel (custom
  decorator, editor key threaded through the pickers), not a separate Koin result class (two calls: send, then pop).
- **Result types**: `interface NavResult` (marker, not sealed: features own their results) in `:core:presentation`;
  each returning Screen declares `sealed interface <Screen>Result : NavResult`. Here
  `SetEditorResult.Added(trainingExercise)` / `Replaced(index, trainingExercise)`.
- **Sending is one call**: `SetEditorNavigator.backToTrainingEditor(result: SetEditorResult)` for both
  „Dodaj do treningu” and „Gotowe”; `:app` implements it with `AppNavigator.popTo(TrainingEditorKey::class, result)`,
  which removes both pickers in add mode and just the Set editor in edit mode. `back(key)` stays for leaving without
  a result. This replaces `backToTraining()` and the edit-mode self-pop from
  [Architecture for Exercises and Training exercises](05-architecture.md).
- **Receiving**: `TrainingEditorNavigator.setEditorResults: Flow<SetEditorResult>`, implemented as
  `appNavigator.results.filterIsInstance<SetEditorResult>()`; the Training editor's ViewModel collects it in `init`
  on `viewModelScope`, so the result lands while it is still under the Set editor.
- **AppNavigator**: `results: Flow<NavResult>` backed by `MutableSharedFlow(extraBufferCapacity = 1)` (broadcast;
  every receiver keeps its own type); `popTo(keyType, result: NavResult? = null)` emits the result before queuing
  `NavRequest.PopTo`. Results match by Kotlin type; two live receivers of one type can't exist today.
- **Tests**: `HeadlessApp` needs only the `PopTo` branch and the Set editor's entry; the real `AppNavigator` carries
  results, so the ViewModel channel tests the real path.
- Recorded in [ADR 0002](../../../../docs/adr/0002-screen-results-through-navigators.md); `android-navigation` gained a
  "Returning a result" section and checklist item, and no longer lists `ResultEventBus` under "Added only when needed".

**Additions**: none.
