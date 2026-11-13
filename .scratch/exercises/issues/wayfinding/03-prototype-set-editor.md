# Prototype: Set editor

Type: prototype
Status: resolved
Blocked by: —

## Question

How should the Set editor look and behave? Build one **interactive** HTML page
(`.scratch/exercises/prototypes/set-editor.html`) with at least 3 switchable variants at phone size, Material 3, dark
theme only, Polish strings: ± at the top changes the Set count; one row per Set with weight and reps; tapping weight
or reps focuses it; ± on the right changes the focused value (weight ±1.5 kg from 0, reps from 12). Show the title
(the Exercise name?), how focus is shown, and how the screen is confirmed or left. The user picks one; the answer
records the choice, visual details and any behaviour the clicking revealed (e.g. what a new Set starts with). List
anything not asked for under Additions.

Note from [Prototype: Exercise category and Exercise pickers](02-prototype-category-and-exercise-pickers.md): in add
mode the pickers are titled „Dodaj ćwiczenie” with „Krok 1 z 3” / „Krok 2 z 3” subtitles, so the Set editor's title
in add mode should fit that (e.g. „Krok 3 z 3: <Exercise>”); in edit mode it opens directly.

## Answer

Resolved with the user on 2026-11-12. **Winner: variant E**, which is variant A (Set cards, ± on the focused row)
plus two behaviours the user chose. Prototype: `.scratch/exercises/prototypes/set-editor.html` (`?variant=E`). All
variants are captured on the throwaway branch `prototype/set-editor` (never merged).

- **Top bar**: small top bar, back arrow. Add mode: title „Dodaj ćwiczenie” (22sp) with subtitle „Krok 3 z 3:
  <Exercise>” (bodyMedium 14sp, `onSurfaceVariant`), matching the pickers. Edit mode: title = Exercise name,
  subtitle = its Exercise category.
- **Set count**: a container under the top bar (`surfaceContainerLow`, 16dp corners, 8dp top / 16dp bottom margin,
  16dp sides): label „Serie” (16sp) on the left, then tonal 40dp `remove` button, the count (22sp, tabular digits),
  tonal 40dp `add` button. − is disabled at the minimum, + at the maximum. − removes the **last** Set.
- **Set rows**: one card per Set like the Training editor rows (`surfaceContainer`, 16dp corners, 12dp gap, min height
  64dp, padding 8dp, 12dp start). Content: Set number in a 32dp circle (`surfaceContainerHighest`, 14sp,
  `onSurfaceVariant`), then two tappable pills, weight „0 kg” and reps „12 powt.” (40dp high, 12dp corners, 16sp).
- **Focus**: exactly one value is focused; tapping a pill focuses it. The focused pill has a `secondaryContainer`
  fill and a 2dp `primary` border. The Set editor opens with Set 1's weight focused. If the focused Set is removed,
  focus moves to the new last Set (same field).
- **± for values**: two tonal 40dp buttons (`remove`, `add`) at the right end of the **focused row only**; other rows
  show no buttons. Weight steps by 1.5 kg, reps by 1; a button is disabled at its limit.
- **Hold to repeat** (kept addition): holding a ± button repeats the step (first repeat after ~400 ms, then every
  ~90 ms) until released or a limit is hit.
- **New Set copies the previous Set** (asked for by the user): + in the Set count appends a Set with the weight and
  reps of the current last Set. A new Training exercise starts with Sets of 0 kg × 12.
- **Leaving**: pinned filled full-width button at the bottom, „Dodaj do treningu” in add mode, „Gotowe” in edit
  mode; both return to the Training editor. Back in add mode returns to the Exercise picker without adding the
  Training exercise. Edit mode: back and „Gotowe” both return to the Training editor.
- **Weight display**: Polish decimal comma, „13,5 kg”.

**Additions**: hold to repeat (kept); ± only on the focused row (kept, part of variant A). Dropped: the table with a
fixed ± rail (B), the docked edit panel and ✓ in the top bar (C), the big-number segmented cards (D).

**Handed to [Training exercise and Set rules](04-training-exercise-and-set-rules.md)**, as the prototype only
assumed them:

- How the add-mode button and back fit immediate saving: the prototype creates the Training exercise on „Dodaj do
  treningu” and discards it on back; edit mode changes were stored on every ±.
- The limits: the prototype used 1–10 Sets, 0–300 kg, 1–100 reps and 3 Sets for a new Training exercise.
- The reps label for timed Exercises (Plank, Deska boczna): the prototype shows „powt.” for them too.
