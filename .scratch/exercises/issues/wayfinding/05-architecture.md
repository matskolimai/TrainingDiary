# Architecture for Exercises and Training exercises

Type: grilling
Status: open
Blocked by: 04

## Question

How is this built? At least:

- What lives in `feature/exercises` (catalogue, picker screens) versus `feature/trainings` (Training exercise, Set,
  Set editor?), and how they connect without feature-to-feature coupling.
- Navigation keys and arguments along category picker → Exercise picker → Set editor and back to the Training editor.
- How the Training editor holds unsaved changes (name + Training exercises) and how the Set editor's result reaches
  them across Screens; saving a Training with its Training exercises atomically; restoring them with the Training
  list's „Cofnij”; repository methods, Room entities and relations, stable Exercise ids, the destructive schema change.
- Where the timed flag lives on Exercises; the discard dialog („Odrzucić zmiany?”) in `:core:design-system`.
- Updating `android-presentation-mvi` where it requires surviving process death: unsaved changes may be lost (see
  [Training exercise and Set rules](04-training-exercise-and-set-rules.md)).
- New design-system components. Candidates from the three prototypes: the tappable value pill with a focused
  state, a count stepper (− N +), hold-to-repeat ± buttons, the count badge on Exercise category rows.

Updates any `android-*` skill a decision overrides.
