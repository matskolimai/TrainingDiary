# Spec-to-ticket slicing order

Type: grilling
Status: resolved
Blocked by: 05, 07

## Question

In what order should `/to-tickets` slice this feature, and which acceptance cases (on which channels) does each slice
carry? Same principles as the Trainings map's
[slicing ticket](../../../trainings/issues/wayfinding/15-slicing-order.md): small single-purpose slices reviewable in
~15 minutes, infrastructure just before it's needed, one ticket per new component.

## Answer

Settled with the user on 2026-11-13. Same principles as the Trainings slicing: infrastructure just before it's
needed, scaffolding tickets add no tests, one small ticket per new component, every ticket leaves the gate green.
No skill, glossary or ADR changes.

### Infrastructure

1. **Modules and catalogue** (before S1): `:core:domain` with `Exercise`, `ExerciseCategory`, `ExerciseCatalogue`;
   `:feature:exercises:data` with the hardcoded catalogue and its Koin binding; `:feature:exercises:presentation`
   empty and wired.
2. **Storage of Training exercises** (before S5): the three tables, the `@Relation` read, the transactional writes,
   the new repository methods; `Training` gains `exercises`; the schema JSON is overwritten. Existing cases stay
   green; no new cases.

The navigator changes (`NavResult`, `AppNavigator.results`, `popTo` with a result, `HeadlessApp`'s `PopTo` branch)
land inside **S4**, whose cases are their only tests.

### Behaviour slices

Each written test-first with only its own cases; exact case wording is left to the spec.

- **S1 Category picker**: „Dodaj ćwiczenie” lists the 7 Exercise categories with their Exercise counts; back returns to
  the Training editor.
- **S2 Exercise picker**: a category tap lists its Exercises in catalogue order; back returns to the categories.
- **S3 Set editor, add mode**: picking an Exercise opens it at 3 × 0 kg × 12; back without changes returns to the
  Exercise picker.
- **S4 Add to Training**: „Dodaj do treningu” returns to the Training editor showing „name / category · 3 serie”; the
  same Exercise twice shows two cards in added order.
- **S5 Save with Training exercises**: „Zapisz” stores them and reopening the Training shows them; leaving without
  saving stores nothing.
- **S6 Number of Sets**: + adds a Set copying the last; − removes the last; limits 1 and 10.
- **S7 Reps and weight**: ± changes only the focused value of the focused row; 1.5 kg steps shown „1,5 kg” / „3 kg”;
  limits 0–300 kg, 1–999 reps; timed Exercises show „12 s”; holding + repeats.
- **S8 Edit a Training exercise**: row tap or ⋮ → „Edytuj” opens the Set editor with its Sets; „Gotowe” replaces it in
  place.
- **S9 Delete a Training exercise**: ⋮ → „Usuń” removes it; saving stores the removal.
- **S10 Discard dialog, Training editor**: back after a change asks „Odrzucić zmiany?”; „Odrzuć” leaves storing nothing;
  „Anuluj” stays; an undone change doesn't ask.
- **S11 Discard dialog, Set editor**: the same in both modes; „Odrzuć” in add mode returns to the Exercise picker.
- **S12 Delete and undo with exercises**: deleting a Training and „Cofnij” restore it with its Training exercises and
  Sets.

Each slice adds only what its cases need: after S3 the Set editor shows Sets but nothing on it can change (S4 can
still add the default); S6 brings the count stepper, S7 the focused row and ±. Until S11, back from the Set editor
always just goes back.

### Channels

Every new case runs on both channels. One exception: „holding + repeats” is `@OnlyOn(Channel.DEVICE)` (the hold lives
in the composable) and asserts at least two steps (≥ 3 kg) after about 1 s, so slow emulators don't flake it.
Focus is ViewModel state, so both channels check it. No new ViewModel-only case.

### Components

One ticket each (component + preview + snapshot), right before its first user:

- before S1: `AppCountBadge`; a subtitle on `AppTopBarWithBack` („Krok 1 z 3”). Category rows reuse `AppListRow`
  with the badge trailing.
- before S3: `AppValuePill`.
- before S6: `AppRepeatIconButton`, then `AppCountStepper` on it.
- before S10: `AppDiscardDialog`.

Training exercise cards (S4) reuse `AppListRow` (name + subtitle in the content, ⋮ trailing).

### Tests

No unit tests: weight takes only 201 values, so the S7 acceptance cases („0 kg”, „1,5 kg”, „3 kg”) plus mutation
testing cover formatting.

**Additions**: none.
