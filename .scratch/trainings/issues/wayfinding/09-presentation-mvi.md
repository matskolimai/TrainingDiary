# Presentation: MVI

Type: grilling
Status: resolved
Blocked by: 03, 06

## Question

Which parts of `android-presentation-mvi` does the user accept or override: State/Action/Event shapes and naming,
Root/Screen composable split, ViewModel state exposure (`StateFlow`, `stateIn`, init loading), one-off events channel +
`ObserveAsEvents` (snackbar with Undo, navigate back after save), UI models, `UiText`, `SavedStateHandle`/process death?
How do add and edit mode share one ViewModel/screen?

Resolve point by point, then rewrite `~/.claude/skills/android-presentation-mvi/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

## Comments

- 2026-11-06 (from Dependency injection): the editor ViewModel receives its Nav3 key (`TrainingEditorKey`) as a
  constructor parameter through `parametersOf(key)`. Any add/edit mode type is derived from the key inside the
  ViewModel. `SavedStateHandle` is not the source of nav arguments; whether it is needed for process death stays here.

## Answer

Settled with the user on 2026-11-06, point by point against `android-presentation-mvi`. The skill was rewritten to
match (`~/.claude/skills/android-presentation-mvi/SKILL.md`, Android-only, written with
`/mattpocock-skills:writing-for-agents`).

- **Shape and naming**: the skill's table. The two screens are `TrainingList` and `TrainingEditor`: one editor for add
  and edit, with the mode derived from `TrainingEditorKey.trainingId`. Actions are named `On<Thing><Gesture>`
  (`OnSaveClick`, `OnNameChange`).
- **Files**: one package per screen (`…presentation.list`, `…presentation.editor`) and one type per file. Root and
  Screen share `<Screen>Screen.kt`. `ObserveAsEvents` lives in `:core:presentation`.
- **State exposure**: nothing runs in `init`.
  - List: `combine(observeTrainings(), <UI-only MutableStateFlows>)` + `stateIn(WhileSubscribed(5_000))`.
  - Editor: a private `MutableStateFlow`, loaded from `onStart` + `stateIn`. A `hasLoaded` guard stops the load from
    rerunning when collection restarts.
- **List loading**: `isLoading = true` at first, with no spinner. Until data arrives the screen shows only the top
  bar and FAB. "Brak treningów" appears only after the first emission.
- **Options sheet**: in State as `optionsSheetTrainingId: Long?`, so it can be previewed (the user wants that).
  Choosing Edytuj or Usuń closes it.
- **Events**, named by what happened:
  - List: `TrainingDeleted(name)`.
  - Editor: `TrainingSaved` and `TrainingMissing`. The Root maps both to `onBack()`.
  - Transport: `Channel` → `receiveAsFlow()` → `ObserveAsEvents` (`Main.immediate`, `STARTED`).
- **Undo**: the Root owns `SnackbarHostState`. It dismisses the current snackbar before showing a new one, so only the
  latest delete can be undone. `ActionPerformed` sends `OnUndoClick`. The ViewModel keeps the deleted `Training` and
  restores it with the same identity. The pending undo does not survive process death.
- **Navigation from the Screen**: the Screen is always `(state, onAction)` (+ `snackbarHostState`). The Root intercepts
  navigation Actions (`OnAddClick`, `OnEditClick(id)`, `OnBackClick`), calls the matching callback, and forwards every
  Action to the ViewModel. The user accepts this for now and wants a reusable ViewModel-driven navigation mechanism
  later (map fog).
- **No UI model**: State holds the domain `Training` directly, because the user dislikes near-duplicate classes. The
  monogram is a pure presentation function with unit tests, and it must handle emoji and surrogate pairs.
- **Editor**:
  - State fields: `name: String` (changed by `OnNameChange`, `value` / `onValueChange`), `isEditMode`, `isLoading`
    (true only while edit mode is loading) and `isSaving`.
  - `canSave` is the computed `!isSaving && isValidTrainingName(name)`. `isValidTrainingName` is a pure function in
    `feature:trainings:domain`, and the repository's `require` also uses it.
  - Edit mode draws the form only after loading. `OnSaveClick` is ignored while saving or when `!canSave`.
  - **Keyboard Done saves** when `canSave` is true. This settles the open point from the form prototype.
- **Process death**: no `SavedStateHandle` in this feature. The user doesn't mind losing input.
- **Strings**: chosen in the composable from State (`isEditMode`, `pluralStringResource` on `trainings.size`). State
  holds no strings, and `UiText` stays dormant with errors. *Provisional*: the user may change this once they see real
  code.
- No glossary changes and no ADR.

> **Superseded in part** (2026-11-07): the monogram was dropped from the row, so `AppMonogram`, the monogram
> function and its unit tests are gone. See [Spec-to-ticket slicing order](15-slicing-order.md).
