# Training exercise and Set rules

Type: grilling
Status: resolved
Blocked by: 01, 02, 03

## Question

What are the product rules for Training exercises and Sets? At least:

- How immediate saving works in add mode, when no Training exists yet (create it on the first Training exercise?
  require a name first?), and what the Training editor's „Zapisz” still does.
- Whether the same Exercise may appear twice in one Training; the order of Training exercises.
- Minimum and maximum Sets, reps and weight; what a newly added Set copies; whether weight and reps can be typed or
  only stepped.
- What the Set editor stores and when (on every ±, or on confirm).
- Undo or confirmation when deleting a Training exercise; what deleting a Training does to its Training exercises.
- How weight is displayed (1.5 → „1,5 kg”).

Settled by [Prototype: Set editor](03-prototype-set-editor.md), not to reopen: a new Set copies the previous Set's
weight and reps; values are stepped (hold repeats). Still open from it: how its „Dodaj do treningu” button and
discard-on-back fit immediate saving, the limits, and the reps label for timed Exercises.

## Answer

Resolved with the user on 2026-11-13. **Immediate saving is dropped**: the user reversed that charting note in favour
of a simpler model. Supersedes the „Zapisz saves only the name” line in
[Prototype: Training editor with Training exercises](01-prototype-training-editor-exercises.md) and the
„Dodaj do treningu” / stored-on-every-± assumptions in [Prototype: Set editor](03-prototype-set-editor.md).

### Saving

- **Nothing is stored until „Zapisz”** in the Training editor, in add and edit mode alike. „Zapisz” stores the name
  and the whole list of Training exercises with their Sets in one go (all or nothing), then closes the Screen.
- „Zapisz” is enabled whenever the name is valid (as today), even with no changes.
- „Dodaj ćwiczenie” is always enabled, also in add mode before a name is entered. The title stays „Nowy trening”
  until the Training is saved.
- The Set editor never stores. „Dodaj do treningu” (add mode) adds the Training exercise to the Training editor's
  unsaved changes and returns there; „Gotowe” (edit mode) replaces the edited Training exercise in them.
- Deleting a Training exercise (⋮ → „Usuń”) removes it from the unsaved changes; still no undo.

### Leaving with unsaved changes

- Applies to the **Training editor** and the **Set editor**, both modes, for the top-bar back arrow and system back
  alike. The pickers never ask: nothing can be lost there.
- **Changed** = the current values differ from what the Screen started with (compared by value, not a "touched"
  flag). Typing a letter and deleting it, or adding and deleting a Training exercise, is no change. Whitespace in the
  name is a change. The Set editor in add mode starts from 3 Sets of 0 kg × 12.
- No change: back just goes back (Set editor in add mode → Exercise picker).
- Changed: a **discard dialog** asks first. Material 3 `AlertDialog`, title „Odrzucić zmiany?”, no body, buttons
  „Anuluj” and „Odrzuć”. „Odrzuć” leaves and drops the changes; „Anuluj”, a tap outside or back dismisses it.
- The dialog is one reusable component in `:core:design-system`, so its styling lives in one place.
- **Process death**: unsaved changes are simply lost. The user wants no saved-state machinery for it (single user,
  simplest code).

### Training exercises and Sets

- The same Exercise may appear more than once in a Training; the Exercise picker doesn't filter.
- Order: the order Training exercises were added. No reordering.
- Limits: 1–10 Sets; weight 0–300 kg in 1.5 kg steps; reps 1–999 for every Exercise. Buttons disable at a limit.
- A new Training exercise starts with 3 Sets of 0 kg × 12. A new Set copies the last Set (settled by the prototype).
  Values are stepped only, never typed.
- **Timed Exercises**: Plank and Deska boczna carry a timed flag in the catalogue and show „12 s” instead of
  „12 powt.”; same start, step and limits.
- Weight display: Polish decimal comma, no „,0” on whole numbers: „0 kg”, „1,5 kg”, „3 kg”, „13,5 kg”.

### Deleting a Training

- Deleting a Training deletes its Training exercises and Sets. The Training list's „Cofnij” restores the Training
  together with all its Training exercises and Sets.

**Additions**: the „s” label for timed Exercises (kept). Dropped: restoring unsaved changes after process death.

**Handed to [Architecture for Exercises and Training exercises](05-architecture.md)**: the discard dialog component;
how the Set editor's result reaches the Training editor's unsaved changes; atomic save of a Training with its
Training exercises; restoring them with „Cofnij”; updating `android-presentation-mvi` where it requires surviving
process death.
