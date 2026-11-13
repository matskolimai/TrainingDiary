Label: wayfinder:map
Status: open

# Exercises in Trainings

## Destination

Every decision is settled for giving a Training its **Training exercises**: picking an Exercise via its category,
editing its Sets (± sets, set rows, ± reps/weight on the focused row, 1.5 kg weight step), editing and deleting
Training exercises, and storing them. `/mattpocock-skills:to-spec` can then write `.scratch/exercises/spec.md` with no
new questions; the user then chooses `/to-tickets` + `/implement` or implement-spec.

## Notes

- **Domain**: same app and conventions as the [Trainings map](../trainings/map.md) (Kotlin + Compose, single user,
  dark theme only, Polish UI strings, English code, minimalism). Vocabulary in `GLOSSARY.md`: **Exercise**,
  **Exercise category**, **Training exercise** and **Set** were added for this effort. The user asked to be corrected
  when they slip on these terms or on English gym vocabulary.
- **Skills**: the `android-*` project skills are the house style; the architecture and slicing tickets consult them
  and update any skill a decision overrides (via `/mattpocock-skills:writing-for-agents`).
- **Additions**: anything user-visible the user didn't ask for goes under an Additions heading for them to keep or
  drop (see `AGENTS.md`).
- **Given by the user while charting (2026-11-12)**:
  - Catalogue: 7 Exercise categories and their Exercises as listed in [catalogue.md](catalogue.md) (Plank and Deska
    boczna stay; their reps stand for seconds). Hardcoded as Kotlin constants with stable ids; a later effort moves
    them to Room for user-defined Exercises.
  - Flow: Training editor „add exercise” button → **Exercise category picker** → **Exercise picker** (plain list, no
    search) → **Set editor** → back to the Training editor with the new Training exercise shown. Editing a Training
    exercise opens the Set editor directly; its Exercise is fixed. Delete is an action on the Training exercise.
  - Set editor: ± at the top changes the number of Sets; one row per Set showing weight and reps; ± on the right
    changes whichever of reps or weight is focused. Weight starts at 0 kg and steps by exactly 1.5 kg; reps start
    at 12.
  - ~~Saving is immediate~~: reversed on 2026-11-13, see
    [Training exercise and Set rules](issues/wayfinding/04-training-exercise-and-set-rules.md).
  - Existing data on the phone may be wiped: no Room migration needed.
  - The catalogue and pickers go in a new **`feature/exercises`**, shaped so user-defined Exercises later need no
    module move; build nothing for that future now.
- **Tracker**: local markdown, see `docs/agents/issue-tracker.md`.

## Decisions so far

- [Prototype: Training editor with Training exercises](issues/wayfinding/01-prototype-training-editor-exercises.md):
  variant A simplified; cards show Exercise name + „category · N serie”, row tap edits, ⋮ sheet Edytuj / Usuń, no
  undo snackbar, outlined „Dodaj ćwiczenie” under the list, „Zapisz” pinned (now saves everything, see below).
- [Prototype: Exercise category and Exercise pickers](issues/wayfinding/02-prototype-category-and-exercise-pickers.md):
  variant B without icons; card rows, category rows with an Exercise count badge, title „Dodaj ćwiczenie” with
  „Krok 1 z 3” / „Krok 2 z 3” subtitles.
- [Prototype: Set editor](issues/wayfinding/03-prototype-set-editor.md): variant A plus two behaviours; Set cards
  with weight/reps pills, ± only on the focused row, hold to repeat, a new Set copies the previous one, pinned
  „Dodaj do treningu” / „Gotowe”, back in add mode discards.
- [Training exercise and Set rules](issues/wayfinding/04-training-exercise-and-set-rules.md): nothing stored until
  „Zapisz” (name + Training exercises at once); discard dialog on back when values changed; duplicates allowed,
  added order; 1–10 Sets, 0–300 kg, 1–999 reps, new = 3 × 0 kg × 12; timed Exercises show „s”.
- [Architecture for Exercises and Training exercises](issues/wayfinding/05-architecture.md): catalogue behind an
  `ExerciseCatalogue` in new `:core:domain`, implemented in `feature:exercises:data`; Set editor in `feature:trainings`;
  `Long` ids, `weightGrams`; three Room tables with cascade, transactional save, DB stays v1 (wipe once); `popTo`;
  `AppDiscardDialog` + four new components; process death loses unsaved input. Result passing split out.
- [Returning a result from one Screen to another](issues/wayfinding/07-screen-results.md): through the
  navigators; `SetEditorResult : NavResult`, one `backToTrainingEditor(result)` → `AppNavigator.popTo(key, result)`,
  Training editor collects `setEditorResults` (a `SharedFlow` filtered by type); ADR 0002.
- [Spec-to-ticket slicing order](issues/wayfinding/06-slicing-order.md): modules + catalogue, then storage
  before S5; navigator changes inside S4; slices S1–S12 (pickers → Set editor → add → save → Sets → reps/weight →
  edit → delete → two discard dialogs → undo); all cases on both channels except hold-to-repeat (device); one
  ticket per component before its first user; no unit tests.

## Not yet specified

Nothing: the way to the destination is clear.

## Out of scope

- User-defined Exercises or Exercise categories (future effort; only the module shape anticipates it).
- Recording a Training session; progress history.
- Timed or distance Sets as their own model; weight units other than kg.
- Search in the pickers.
- Reordering Training exercises.
- Restoring unsaved changes after process death.
- Icons or illustrations for Exercise categories (dropped from the pickers prototype; maybe a later effort).
- Room migration of existing data.
