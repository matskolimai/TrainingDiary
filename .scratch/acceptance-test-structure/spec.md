# Spec: Acceptance suite split per Screen

Status: ready-for-agent

Source: the design conversation of 2026-11-10. `options.html` in this directory is the comparison page from that
conversation; its option B shows `givenTrainings` on the Training list DSL holding two drivers, which was later
replaced by `given`. Where the page and this spec disagree, this spec wins.

## Problem Statement

Every acceptance test in this app sits in one test class, every step in one DSL class, and every Compose lookup in
one UI driver class, all named after the Trainings feature. With two Screens that is 11 cases, 16 DSL steps and 17
driver methods. Each new Screen and feature will lengthen the same three files until I can no longer read through
them, find the cases for one Screen, or see what a driver can do.

## Solution

Every layer of the acceptance suite is split per **Screen**, so the suite grows by adding small files instead of
lengthening existing ones.

- Each Screen has its own test class, its own DSL and its own UI driver, all named after the Screen.
- Every step in a test case names the Screen it happens on, so a case that crosses Screens reads as a route.
- Setup that spans Screens lives in one place, `given`, and reads like a test.
- All wiring a test class needs comes from one shared base class, so a test class contains only its cases.
- The app is not touched. The same 11 cases, with the same names, pass before and after.

## User Stories

1. As a developer, I want one test class per Screen, so that I can find every case about a Screen in one short file.
2. As a developer, I want a case to live in the class of the Screen where the action under test happens, so that
   every case has exactly one clear home.
3. As a developer, I want a case that starts on, or reads its result from, another Screen to still live with the
   Screen whose action it tests, so that cross-Screen cases do not need a class of their own.
4. As a developer, I want the Training editor's adding and editing cases in one class, so that one Screen with two
   jobs is not treated as two Screens.
5. As a developer, I want one UI driver per Screen, so that a driver stays small enough to read in one go.
6. As a developer, I want a driver to know only its own Screen's nodes, so that changing one Screen's layout touches
   one driver.
7. As a developer, I want drivers never to reference each other, so that there is no hidden coupling between Screens
   in the Compose layer.
8. As a developer, I want one DSL per Screen, so that the DSL layer does not become the next oversized file.
9. As a developer, I want each Screen DSL to wrap exactly one driver, so that I always know which driver a step uses.
10. As a developer, I want every step prefixed with its Screen (`trainingList.delete("Nogi")`), so that I can see
    where each line of a case happens.
11. As a developer, I want autocomplete after a Screen prefix to list only that Screen's steps, so that writing a case
    is quick.
12. As a developer, I want step names without words the prefix already says, so that cases stay short.
13. As a developer, I want test bodies flat, with no nested block per Screen, so that short cases stay short.
14. As a developer, I want one `given` object for starting state, so that I never repeat the steps of adding
    Trainings in every case.
15. As a developer, I want `given` to be the only place a multi-Screen helper may live, so that "where do I put this
    helper?" has one answer.
16. As a developer, I want `given` written with Screen DSL steps, so that it reads like a test and I can see how it
    works.
17. As a developer, I want the behaviour under test spelled out step by step in the case, so that a helper never
    hides what is being tested.
18. As a developer, I want `given` to be replaceable later by a backdoor into the database, so that a slow suite can
    be sped up without editing a case.
19. As a developer, I want one base class that owns the Compose extension, the in-memory app extension and the DSL
    scope, so that a new test class has no wiring of its own.
20. As a developer, I want every acceptance test class to extend that base class, so that the wiring exists once.
21. As a developer, I want one object that holds every Screen DSL and `given`, so that adding a Screen to the suite
    is one new line there.
22. As a developer, I want a step that leaves a Screen to wait until that Screen is gone, so that the next step
    starts from a settled state.
23. As a developer, I want the first step on a Screen to wait for its own node before acting, so that no driver has
    to announce arrival on behalf of another.
24. As a developer, I want the folders of the suite unchanged, so that the restructure is about files, not packages.
25. As a developer, I want the old feature-wide test class, DSL and driver removed, so that there is one structure
    and no leftover to copy from by mistake.
26. As a developer, I want all 11 case names unchanged, so that I can verify the refactor by comparing test reports.
27. As a developer, I want the suite and `check` green after the change, so that I know behaviour coverage is intact.
28. As an agent adding a Screen later, I want the structure to match the `android-acceptance-testing` skill, so that
    following the skill produces files that fit.
29. As an agent, I want Screen, Training list and Training editor defined in the glossary, so that class names and
    case wording use one vocabulary.

## Implementation Decisions

### Screens

- A **Screen** is one navigation destination (see `GLOSSARY.md`). The suite has two: **Training list** and
  **Training editor**. The "new training screen" and "edit training screen" named in test cases are the Training
  editor's two modes, not separate Screens.

### Test classes

- `TrainingListAcceptanceTest` and `TrainingEditorAcceptanceTest` replace `TrainingsAcceptanceTest`. They stay in
  the existing Trainings test package.
- Placement rule: a case lives in the class of the Screen where the action under test happens.
- Training list (4): first launch shows that there are no trainings; deleting a training removes it from the list
  and offers to undo; undoing a delete puts the training back in its place; only the latest delete can be undone.
- Training editor (7): opening the new training screen shows an empty name ready to type; leaving the new training
  screen without saving changes nothing; saving the new training screen lists the training at the end; saving the
  new training screen trims the name; the new training screen cannot be saved with a blank name; opening the edit
  training screen shows the name of the training; saving the edit training screen renames the training in its place.
- Case names (the backtick sentences) are kept exactly. Both classes are flat; no `@Nested` groups yet.

### Base class

- An abstract `AcceptanceTest` in the root acceptance package owns: the Compose extension for `MainActivity`, the
  `InMemoryAppExtension` registration, and an `app { }` scope function that builds `AppDsl` and runs the case's
  steps on it.
- The extension ordering that exists today must be preserved: Koin starts before the activity launches and stops
  after it closes. Verify this still holds once the annotation and the extension field are inherited.

### DSL layer

- `AppDsl` builds the two drivers and exposes three properties: `trainingList`, `trainingEditor`, `given`.
- `TrainingListDsl` wraps only the Training list driver; `TrainingEditorDsl` wraps only the Training editor driver.
- `GivenDsl` takes the Screen DSLs (not drivers) and offers `trainings(names…)`: for each name, open a new Training
  from the list, enter the name, save.
- `TrainingsDsl` is deleted. `AppDsl`, `GivenDsl` and the Screen DSLs live in the existing DSL package.
- Step renames:

| Today | After |
|---|---|
| `givenTrainings(…)` | `given.trainings(…)` |
| `assertTrainingsListed(…)` | `trainingList.assertListed(…)` |
| `assertNoTrainings()` | `trainingList.assertEmpty()` |
| `openNewTrainingScreen()` | `trainingList.openNewTraining()` |
| `openEditTrainingScreen(name)` | `trainingList.openForEditing(name)` |
| `delete(name)` | `trainingList.delete(name)` |
| `assertUndoOffered(name)` | `trainingList.assertUndoOffered(name)` |
| `undoDelete()` | `trainingList.undoDelete()` |
| `assertNewTrainingScreenIsEmpty()` | `trainingEditor.assertEmptyNewTraining()` |
| `assertNameReadyToType()` | `trainingEditor.assertNameReadyToType()` |
| `assertEditTrainingScreenShows(name)` | `trainingEditor.assertEditing(name)` |
| `enterName(name)` | `trainingEditor.enterName(name)` |
| `replaceName(name)` | `trainingEditor.replaceName(name)` |
| `save()` | `trainingEditor.save()` |
| `assertSaveUnavailable()` | `trainingEditor.assertSaveUnavailable()` |
| `leaveWithoutSaving()` | `trainingEditor.leaveWithoutSaving()` |

### Drivers

- `TrainingListUiDriver` and `TrainingEditorUiDriver` replace `TrainingsUiDriver`, in the existing driver package.
- Training list driver takes: the empty-list assertion, the listed-in-order assertion, opening a new Training,
  opening a Training for editing, deleting, the undo-offered assertion, undoing, and the private row-options, more
  options and undo matchers.
- Training editor driver takes: the empty-for-adding and showing-for-editing assertions, the name-focused assertion,
  typing and replacing the name, saving, the cannot-save assertion, leaving, and the private name field, save button
  and input matchers.
- The old `addTraining` driver method is removed; `GivenDsl.trainings` replaces it.
- A driver waits only on its own Screen's nodes. The two list steps that open the editor stop waiting for the
  editor's name field and instead wait until the Training list is gone (for example, its add action no longer
  exists). Editor steps that leave keep waiting until the name field is gone. The editor's first steps
  (typing, replacing, asserting) must wait for the name field before acting if the transition makes that necessary.
- Drivers do not reference each other, and no Screen DSL holds a second driver.

### Skill and glossary

- `GLOSSARY.md` already defines Screen, Training list and Training editor (added during the design conversation).
- The `android-acceptance-testing` skill was already rewritten during the design conversation to describe this
  structure (section "One screen, one of each"). After the code is green, compare the skill's examples with the real
  code and correct the skill wherever they differ.

## Testing Decisions

- This is a refactor of test code; the acceptance suite is its own proof. No new test is written and no test of the
  test code is added.
- The seam is the existing one: the whole app driven through Compose on the managed device.
- Good outcome: the test report lists the same 11 case names as before, 4 under `TrainingListAcceptanceTest` and 7
  under `TrainingEditorAcceptanceTest`, all passing.
- Gate: `./gradlew check` and the managed-device acceptance task both pass (a running emulator with
  `connectedDebugAndroidTest` if the managed device cannot run).
- Only drivers import Compose test APIs; DSLs and test classes do not. Test code holds no literal UI copy.
- Prior art: the current `TrainingsAcceptanceTest`, `TrainingsDsl` and `TrainingsUiDriver`, whose behaviour is being
  redistributed.

## Out of Scope

- Any change to production code, including new `testTag`s. If a wait cannot be expressed without one, stop and ask.
- Adding, removing or rewording test cases.
- A backdoor (database) implementation of `given`.
- Regrouping packages by feature, or moving the suite to its own `:acceptance` module.
- `@Nested` groups inside the test classes.
- An ADR: the skill documents the structure.

## Further Notes

- Additions: none. Nothing user-visible changes.
- The `options.html` page in this directory shows the three options that were compared and why B was chosen.
