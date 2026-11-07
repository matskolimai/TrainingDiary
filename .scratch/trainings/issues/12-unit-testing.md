# Unit testing

Type: grilling
Status: open
Blocked by: 07, 09

## Question

Which parts of `android-unit-testing` does the user accept or override: JUnit 5, Truth, Turbine, fakes over mocks,
dispatcher injection, the adversarial "break vectors" methodology, test naming and structure, what must be unit-tested
in this feature (ViewModels, mappers, validation) and what not? How do tests fit into small reviewable slices (same
ticket as the code, or TDD via `/mattpocock-skills:tdd`)?

Resolve point by point, then rewrite `~/.claude/skills/android-unit-testing/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

## Comments

- 2026-11-06 (from Presentation: MVI): the units to test are `TrainingListViewModel` (combine + `stateIn`,
  sheet state, delete → `TrainingDeleted`, undo restores the same identity, only the latest delete can be undone),
  `TrainingEditorViewModel` (`onStart` load with the `hasLoaded` guard, `TrainingMissing`, `canSave`, save ignored
  while `isSaving`, `TrainingSaved`), the domain `isValidTrainingName`, and the monogram function (emoji / surrogate
  pairs). Nothing runs in `init`, so tests start the work by collecting `state`.
