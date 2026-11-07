# Prototype: Training form screen

Type: prototype
Status: resolved
Blocked by: —

## Question

What should the Training form screen look like in edit mode with data filled? Build one HTML page
(`.scratch/trainings/prototypes/training-form.html`) with at least 3 switchable variants at phone size, Material 3,
**dark theme only**, Polish strings: a single name text field and a Save button at the bottom (also show its disabled
look). The user picks one; the answer records the choice and visual details (top bar/title for add vs edit, button
placement relative to the keyboard).

## Answer

Resolved with the user on 2026-11-05. **Winner: variant A (Klasyczny)**, unchanged.
Prototype: `.scratch/trainings/prototypes/training-form.html` (`?variant=A`). All four variants are captured on the
throwaway
branch `prototype/training-form` (never merged).

Visual and behavioural details the design system and presentation layer must honour (dark theme, M3 baseline dark tokens
until the design-system ticket picks a palette):

- **Top bar**: small (start-aligned) top app bar, 64dp; navigation icon `arrow_back` (content description „Wstecz”);
  title in titleLarge 22sp: **„Nowy trening”** in add mode, **„Edytuj trening”** in edit mode. No actions.
- **Field**: one M3 outlined text field, full width, 16dp screen padding, label „Nazwa”, single line, no supporting or
  error text. Edit mode pre-fills the Training's current name.
- **Save button**: filled button „Zapisz”, full width, 56dp high, fully rounded (28dp), label 16sp medium; in a bottom
  area with 16dp padding, **pinned to the bottom of the screen and lifted above the keyboard** (`imePadding`).
  Disabled look = standard M3 disabled filled button (on-surface 12% container, 38% content); disabled exactly when the
  trimmed name is empty (see Training product rules).
- **Focus / keyboard**: in **add mode** the field gets focus and the keyboard opens when the form appears; in **edit
  mode**
  nothing is focused and the keyboard stays closed until the user taps the field.
- **Not decided**: whether the keyboard's Done (IME) action also saves. Raise it in the presentation-MVI or
  components grilling.
