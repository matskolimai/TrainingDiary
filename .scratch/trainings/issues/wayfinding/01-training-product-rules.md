# Training product rules

Type: grilling
Status: resolved
Blocked by: —

## Question

What are the behavioural rules for a Training beyond "it has a name": validation, uniqueness, ordering, undo semantics,
and editing a missing Training?

## Answer

Settled with the user while charting the map (2026-11-05):

- Name is trimmed before saving; a whitespace-only name counts as empty → Save disabled.
- Duplicate names are allowed. No length limit.
- List order: creation order, oldest first.
- Delete is immediate, no confirmation; a snackbar offers Undo. Undo restores the Training with its **original
  identity**, so it reappears in the same position.
- Edit mode loads the Training by id; if it no longer exists (e.g. just deleted), navigate back.
- Back from the form just navigates back — no discard dialog.
- Empty list shows an informational message centred on screen; FAB (bottom-right) opens the form in add mode.
- Each row: name + trailing three-dot icon → bottom sheet with Edit and Delete.
