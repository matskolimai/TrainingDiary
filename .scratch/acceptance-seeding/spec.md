# Spec: Seeded starting state, `startOn` and owning cases

Status: ready-for-agent

Source: the grilling conversation of 2026-11-17 about which cases belong in `TrainingListCases`. Rules for every
project go into the `android-acceptance-testing` skill; this spec covers this repo's code.

## Problem Statement

I am using this app as a proof of concept that acceptance-test-driven development scales to a bigger project, and
three things make the suite hard to understand and slow:

- I cannot tell from a Screen's cases what that Screen lets the user do. Opening a Training for editing from the
  Training list has no case in `TrainingListCases`; it is tested only as a side effect inside Training editor cases.
  If those cases ever reached the editor some other way, the behaviour would lose its test and nothing would fail.
- `given` mixes two jobs: `given.trainings` stores Trainings, while `given.newTrainingExercise` clicks through four
  Screens to put the user on the Set editor. Every `given` step goes through the UI, so a case that needs many stored
  Trainings would take tens of seconds on the device.
- Nothing protects the last Training of a long list. If someone deletes the list's bottom padding, the add button
  covers the last row's options and the user can no longer delete or edit that Training, and every case still passes
  because they all use three rows.

## Solution

- Every user-visible behaviour has exactly one **owning case**: a case whose purpose and name is that behaviour.
  Using it as a step in another case does not count. Cases stay grouped by Screen.
- Opening one Screen from another is split: the Screen the user opens from owns "the right Screen opened, for the
  right Training"; the opened Screen owns what it shows and does on entry.
- `given` only stores data, through the app's real repositories, without touching the UI. Each flow it skips has
  its own owning case that goes through the UI.
- A new `startOn` holds the UI shortcuts that put the user on a Screen at the start of a case.
- A device case proves the last Training of a long list can still be deleted.

## User Stories

1. As the developer, I want each Screen's cases to list everything the user can do on that Screen, so that I can
   read one file and know the Screen's behaviour.
2. As the developer, I want `TrainingListCases` to own opening a new Training, so that the add button keeps a test
   even if no other case uses it.
3. As the developer, I want `TrainingListCases` to own opening a Training for editing, so that the Edit option
   keeps a test even if no other case uses it.
4. As the developer, I want the list's opening cases to assert only that the right Screen opened for the right
   Training, so that they do not repeat what the Training editor's cases check.
5. As the developer, I want the Training editor's cases to keep owning what it shows on entry (the empty name ready
   to type, the stored name), so that a new way into the editor needs only one short case on its own Screen.
6. As the developer, I want `given.trainings("Push day", "Nogi")` to store Trainings without driving the UI, so that
   cases start quickly.
7. As the developer, I want `given` to write through the real repositories from the real Koin graph, so that cases
   stay blind to whether Trainings live in Room or, later, a backend.
8. As the developer, I want `given.training(name, Training exercises…)` to store a Training with its Training
   exercises and Sets, so that a case about editing a stored Training does not rebuild it through four Screens.
9. As the developer, I want `given.training` to take the same `PlannedTrainingExercise` the cases already assert
   with, so that what a case stores and what it checks read the same.
10. As the developer, I want `given` to behave the same on the device and ViewModel channels, so that one case body
    still runs on both.
11. As the developer, I want the steps that put the user on a Screen to live in `startOn`, so that `given` means
    only "what exists" and `startOn` means only "where the user starts".
12. As the developer, I want `startOn.newTrainingExercise(category, exercise)` to replace
    `given.newTrainingExercise`, so that Set editor cases read as starting on the Set editor.
13. As the developer, I want `startOn` built only from Screen DSL steps, so that it never bypasses the UI and the
    drivers never reference each other.
14. As the developer, I want the behaviour under test spelled out step by step even when a case uses `given` or
    `startOn`, so that shortcuts never hide what the case checks.
15. As a user with many Trainings, I want to delete the last Training in my list, so that no Training is stuck out
    of reach behind the add button.
16. As the developer, I want the long-list case to fail when the list's bottom padding is removed, so that I know it
    protects against that regression.
17. As the developer, I want the long-list case to run only on the device, so that the ViewModel channel, which has
    no layout, does not report a pass it cannot earn.
18. As the developer, I want the Training list's device driver to scroll to a row before using its options, so that
    steps work on Trainings that start off screen.

## Implementation Decisions

- **`GivenDriver`**: a new driver interface behind `GivenDsl`, with one implementation per channel
  (`GivenDeviceDriver`, `GivenViewModelDriver`). Each gets `TrainingRepository`, and the Exercise catalogue where
  needed, from the real Koin graph. The channels differ only in how they run the suspend call: the device blocks;
  the ViewModel channel launches it on the test dispatcher and settles, because Room's queries run on that
  dispatcher and a blocking call would probably deadlock.
- **`GivenDsl` steps**:
  - `trainings(vararg names)` stores each Training with no Training exercises, in the given order.
  - `training(name, vararg PlannedTrainingExercise)` looks up each Exercise by category and name in the real
    catalogue, builds the Sets the Set editor would add by default for that number of Sets, and stores the Training
    through `addTraining(name, exercises)`.
  - `GivenDsl` no longer depends on Screen DSLs.
- **`StartOnDsl`**: a new DSL registered in `AppDsl` as `startOn`, next to `given`. It depends only on Screen DSLs
  and holds `newTrainingExercise(category, exercise)`, moved from `given` (19 uses). It is the only place a step may
  span several Screens' UI steps, and only for the opening of a case.
- **`TrainingEditorDsl.assertAdding()`**: a new step that asserts the Training editor is open for adding. The
  ViewModel driver checks that the top nav key is the editor in add mode; the device driver checks for the new
  training Screen's title from string resources. `assertEditing(name)` already covers the editing side.
- **Training list device driver**: `chooseOption` scrolls the list to the Training's row before opening its options.
  The rows are in a `LazyColumn`, which does not compose rows that are off screen.
- **Seeding the database before or after the Activity starts** is the implementer's call; the list observes its
  Trainings, so either works.
- No production Kotlin changes are expected.

## Testing Decisions

- A good case states one user-visible behaviour in domain words, through the DSL only: no gesture, widget or
  implementation words. A refactor that keeps behaviour keeps the case unchanged.
- New cases in `TrainingListCases`, on both channels:
  - `opening a new training shows the new training screen`: opens a new Training and asserts `assertAdding()`.
  - `opening a training for editing shows that training's edit screen`: given "Push day" and "Nogi", opens
    "Nogi" and asserts `assertEditing("Nogi")`.
- New device-only case in `TrainingListCases`, tagged `@OnlyOn(Channel.DEVICE)`:
  `the last training of a long list can still be deleted`. It stores 20 Trainings with `given.trainings`, deletes
  the last one and asserts the remaining 19 are listed in order.
- The Training editor keeps `opening the new training screen shows an empty name ready to type` and
  `opening the edit training screen shows the name of the training`; their opening steps are now only setup.
- `given.training(…)` replaces the UI-built stored Training in the three Training editor cases that rebuild one:
  `leaving an edited training without saving keeps its stored name exercises and sets`,
  `saving an edited training keeps existing and added exercises with their sets` and
  `saving after deleting a training exercise stores the removal`.
- Every existing use of `given.trainings` now seeds storage; every `given.newTrainingExercise` becomes
  `startOn.newTrainingExercise`. All existing cases still pass on both channels.
- Proof for the long-list case: remove the Training list's bottom padding, confirm the case fails on the device,
  restore the padding. Record the result in the ticket's mutation table.
- Gate: `./gradlew check` and the managed-device suite pass. No production Kotlin changes, so mutation testing is
  only the padding check above.
- Prior art: `TrainingListCases` (delete and undo), `TrainingEditorCases` (stored Training cases),
  `SetEditorCases` (`given.newTrainingExercise`), and the per-Screen driver pairs under the acceptance `driver`
  packages.

## Out of Scope

- Reviewing every case Screen by Screen to apply the owning-case rule and switch setup to `given` or `startOn`. The
  user will write that ticket later; only the cases listed above change here.
- Screenshot tests of the list's options sheet.
- Cases for other rows of a long list, or for the add button moving elsewhere on the Screen.
- Editing the `android-acceptance-testing` skill: done directly, outside this spec, with the owning-case rule, the
  navigation split, the `given` and `startOn` rules and an updated `GivenDsl` example.

## Further Notes

- Seeding through the real repository keeps ADR 0001's choice against a fake repository: the trimming and blank-name
  rules still run.
- Bypassing the UI in `given` is safe only because each skipped flow (opening a new Training, saving it, adding a
  Training exercise) has its own owning case. A future `given` step needs the same.
- No Additions: nothing the user sees changes.
