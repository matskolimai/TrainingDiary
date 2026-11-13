# Prototype: Exercise category and Exercise pickers

Type: prototype
Status: resolved
Blocked by: —

## Question

How should the Exercise category picker and the Exercise picker look? Build one HTML page
(`.scratch/exercises/prototypes/pickers.html`) with at least 3 switchable variants at phone size, Material 3, dark
theme only, Polish strings, using the real catalogue from the map's Notes. Plain lists, no search. Show both screens:
top bar titles, back, the row look, and whether rows show an Exercise count or an icon. The user picks one; the answer
records the choice and visual details. List anything not asked for under Additions.

## Answer

Resolved with the user on 2026-11-12. **Winner: variant E**, which is variant B (cards like the Training editor)
without the category icons. Prototype: `.scratch/exercises/prototypes/pickers.html` (`?variant=E`). All variants are
captured on the throwaway branch `prototype/pickers` (never merged).

- **Top bar** (both screens): small top bar, back arrow, title „Dodaj ćwiczenie” (22sp) with a step subtitle under it
  (bodyMedium 14sp, `onSurfaceVariant`): „Krok 1 z 3: kategoria” on the Exercise category picker, „Krok 2 z 3:
  <Exercise category>” on the Exercise picker. Back on the Exercise picker returns to the category picker; back on
  the category picker returns to the Training editor.
- **Rows** (both screens): separate containers like the Training editor rows: `surfaceContainer`, 16dp corners, 12dp
  gap, horizontal padding 16dp, 8dp above the first row, 24dp below the last. The whole row is the tap target; no
  chevron, no dividers.
- **Exercise category row**: min height 72dp, Exercise category name (bodyLarge 16sp, `onSurface`) and a trailing
  count badge with the number of Exercises (28dp high, fully rounded, `surfaceContainerHighest`, 13sp medium,
  `onSurfaceVariant`). **No icon.**
- **Exercise row**: min height 56dp, Exercise name only (bodyLarge 16sp, wraps), padding 16dp both sides.
- **Order**: the catalogue order in [catalogue.md](../../catalogue.md), for categories and Exercises.
- Tapping an Exercise opens the Set editor (step 3 of 3).

**Additions**: step subtitle and count badge (kept, as part of the chosen variant). Dropped: body-part icons for
Exercise categories (harder to implement; maybe a later effort), numbered Exercises (C), the one-screen chips picker
(D).
