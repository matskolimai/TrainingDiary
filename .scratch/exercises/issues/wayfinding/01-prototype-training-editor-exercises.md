# Prototype: Training editor with Training exercises

Type: prototype
Status: resolved
Blocked by: —

## Question

How should the Training editor look once a Training has Training exercises? Build one HTML page
(`.scratch/exercises/prototypes/training-editor.html`) with at least 3 switchable variants at phone size, Material 3,
dark theme only, Polish strings, extending variant A of
[Prototype: Training form screen](../../../trainings/issues/wayfinding/05-prototype-training-form.md). Show the „add
exercise” button; the state with no Training exercises; a list of several Training exercises and what each shows
(name, category, a set summary such as „3 × 12 · 0 kg”?); their edit and delete controls (⋮ sheet like the Training
list, or inline icons); and where „Zapisz” sits now that Training exercises save immediately. The user picks one; the
answer records the choice and visual details. List anything not asked for under Additions.

## Answer

Resolved with the user on 2026-11-12. **Winner: variant D**, which is variant A (cards + ⋮ menu) simplified by the
user. Prototype: `.scratch/exercises/prototypes/training-editor.html` (`?variant=D`). All variants are captured on the
throwaway branch `prototype/training-editor` (never merged).

The top of the screen is unchanged from
[Prototype: Training form screen](../../../trainings/issues/wayfinding/05-prototype-training-form.md): small top bar
with back arrow and „Nowy trening” / „Edytuj trening”, then the outlined „Nazwa” field.

- **No section header** and no Training exercise count: the list starts 24dp below the field.
- **List**: horizontal padding 16dp, 12dp gap between rows. Each row is a separate container like the Training list
  rows: `surfaceContainer`, 16dp corners, min height 72dp, padding 12dp vertical, 16dp start / 4dp end.
- **Row content**: headline = Exercise name (bodyLarge 16sp, `onSurface`, wraps); supporting text =
  „<Exercise category> · <N> serie” (bodyMedium 14sp, `onSurfaceVariant`), Polish plurals seria / serie / serii
  (`plurals` resource). **No reps or weight** on the row. Trailing 48dp `more_vert` icon button, `onSurfaceVariant`,
  content description „Więcej opcji”.
- **Tapping the row** opens the Set editor for that Training exercise (kept addition).
- **⋮** opens the same modal bottom sheet as the Training list: `edit` „Edytuj” (opens the Set editor), `delete`
  „Usuń”.
- **Delete**: immediate, **no „Cofnij” snackbar** (addition dropped).
- **„Dodaj ćwiczenie”**: outlined button with `add` icon, full width minus 16dp margins, 40dp high, fully rounded,
  16dp below the last row (24dp below the field when the list is empty), 24dp bottom margin; scrolls with the list.
- **Empty state**: nothing between the field and „Dodaj ćwiczenie”: no header, no hint text.
- **„Zapisz”**: stays as decided for the Training form (filled, full width, pinned to the bottom, above the keyboard);
  it saves only the name. Training exercises save immediately. _Superseded by
  [Training exercise and Set rules](04-training-exercise-and-set-rules.md): „Zapisz” stores the name and the Training
  exercises together; nothing saves immediately._
- Order of rows: the order the Training exercises were added (the prototype assumed this; nothing else was raised).

**Additions**: row tap opens the Set editor (kept). Dropped: „Cofnij” snackbar, the count header, autosaving the name
(variant C).

**Not decided here**: whether „Dodaj ćwiczenie” is enabled in add mode before a name is entered; that belongs to
[Training exercise and Set rules](04-training-exercise-and-set-rules.md).
