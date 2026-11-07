# Testing strategy

Type: grilling
Status: resolved
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
- 2026-11-07 (from Navigation): add the back-stack guards in `:core:presentation` to the units under test:
  `push(key)` does nothing when the key is already on top, and `pop(key)` does nothing unless the key is on top
  (a double push, a double pop, and popping a key that isn't on top).

## Answer

Settled with the user on 2026-11-07. The user replaced the unit-test-first baseline with acceptance-test-driven
development, and the UI testing ticket was merged into this one (renamed from "Unit testing"). Skills were rewritten
with `/mattpocock-skills:writing-for-agents`: new `android-acceptance-testing` and `android-screenshot-testing`;
`android-unit-testing` shrunk to pure functions; `android-ui-testing` deleted; testing parts of
`android-module-structure`, `android-compose-components` (previews `internal`), `android-navigation` and
`android-di-koin` updated.

- **Acceptance tests are the primary suite**, in Farley's four layers: test cases in domain words (no UI or
  implementation words) → one DSL class per feature (`TrainingsDsl`) → protocol driver (`TrainingsUiDriver`, the only
  layer that knows Compose) → the real app. Tests must not care whether data comes from Room or a backend.
  - **Whole app under test**: real Koin graph, ViewModels, repository and navigation. Only the database is swapped for
    in-memory Room through a Koin override. A future backend is mocked with OkHttp `MockWebServer`.
  - **Isolation**: `AcceptanceTestRunner` swaps in `AcceptanceTestApplication`; an extension starts Koin with
    `appModules` + the in-memory override before each test and stops it after, so every test gets a fresh database.
    `:app` exposes its module list as `appModules`. No Test Orchestrator.
  - **Preconditions through the UI** (`givenTrainings` adds them as the user would). If the suite gets slow, the agent
    reports it and the user decides about a backdoor driver.
  - **Locators**: visible text, then content description, then `testTag` only where neither exists; all text from
    string resources, never literal Polish in tests.
  - **Sync**: driver steps wait on conditions (`waitUntil…`), never `Thread.sleep`.
  - **Placement**: `:app/src/androidTest`, packages `…acceptance.trainings`, `…acceptance.dsl`,
    `…acceptance.driver`. A `com.android.test` module only once that gets crowded.
  - **Runtime**: instrumented, not Robolectric (the user may revisit if the suite becomes too slow). JUnit 6 on device
    through mannodermaus' Compose extension; JUnit 4 is an accepted fallback if JUnit 6 is problematic.
  - **Gradle Managed Device** with the Pixel 9 profile (the user's phone), headless. **Gate before review**:
    `./gradlew check` + the managed-device test task. Fallback: `connectedDebugAndroidTest` on a running emulator.
- **Acceptance cases for this feature** (12):
  1. First launch shows „Brak treningów”.
  2. An added Training appears in the list.
  3. Names are trimmed.
  4. Save is unavailable for a blank name.
  5. Editing renames the Training, keeping its position.
  6. Delete removes it and offers undo.
  7. Undo restores it in its original position.
  8. Only the latest delete can be undone.
  9. The top-bar count follows the number of Trainings, with Polish plural forms (1 / 2 / 5).
  10. Back from the editor without saving changes nothing.
  11. Starting to add opens an empty editor titled „Nowy trening”.
  12. Starting to edit opens the editor titled „Edytuj trening” with the name filled in.
  - "Editing a missing Training navigates back" is left out until a backdoor driver exists.
- **Unit tests only for pure functions with many inputs**: the monogram (emoji, surrogate pairs, ZWJ) and possibly
  `isValidTrainingName`. No unit tests for ViewModels, the repository, mappers or the push/pop guards (this
  supersedes the comments above and the Navigation answer's "covered by JVM unit tests"). Written test-first with
  `/mattpocock-skills:tdd`, in the same ticket as the function; the break-vector list goes into the change summary.
  JUnit 6 + Truth; no Turbine, no MockK.
- **Screenshot tests with Paparazzi `2.0.0-alpha5.1`** (the user's choice; known to work on Windows):
  - Every preview in `:core:design-system` and `feature:trainings:presentation` is snapshotted by a test in the same
    module that calls the preview directly, so previews become `internal` (was `private`).
  - One configuration: Pixel 9 (custom `DeviceConfig` if no constant), dark, `pl`, font scale 1.0.
  - Reference images in each module's `src/test/snapshots`, plain git.
  - `check` runs `verifyPaparazziDebug`; a ticket that changes UI on purpose records and commits new images.
  - `convention.paparazzi` plugin, only on modules with screenshot tests: Paparazzi + JUnit 4 + vintage engine.
  - Risk: if the alpha doesn't render with AGP 9.5 / Compose 1.12, the first screenshot ticket brings it back.
- **Slicing**: tests ship in the same ticket as the behaviour they cover; scaffolding tickets have none.
- No glossary changes and no ADR: all reversible, and the skills document it.
