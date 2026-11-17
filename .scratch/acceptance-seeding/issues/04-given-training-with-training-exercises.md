# 04: `given.training` with Training exercises

**What to build:** a case about a stored Training with Training exercises starts from
`given.training("Push day", PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3))`
instead of building it through four Screens. See `../spec.md`.

**Blocked by:** 02 (`given.trainings` writes to storage instead of driving the UI).

**Status:** ready-for-agent

- [ ] `given.training(name, vararg PlannedTrainingExercise)` goes through `GivenDriver`: each Exercise is looked up
      by category and name in the real catalogue, gets the Sets the Set editor adds by default for that number of
      Sets, and the Training is stored through `addTraining(name, exercises)`.
- [ ] These Training editor cases start from `given.training(…)`, with the behaviour under test still spelled out:
      `leaving an edited training without saving keeps its stored name exercises and sets`,
      `saving an edited training keeps existing and added exercises with their sets`,
      `saving after deleting a training exercise stores the removal`.
- [ ] `./gradlew check` and the device suite pass.
