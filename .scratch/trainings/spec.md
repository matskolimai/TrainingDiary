# Spec: Trainings + project foundation

Status: ready-for-agent

Source: the Wayfinder map `map.md` in this directory and its resolved tickets under `issues/wayfinding/`. Where this
spec and a ticket disagree, the later "Superseded" note or comment wins (the monogram is gone; error-handling decisions
are dormant; no unit tests for ViewModels, the repository, mappers or nav guards). Implementation tickets live directly
under `issues/`.

## Problem Statement

I train regularly and want a personal training diary on my phone. Before I can record anything I perform, I need a
place to configure the Trainings I do ("Push day", "Nogi", …) — a named, reusable template. Today the app is an empty
Android Studio template: there is nothing to see, nowhere to store a Training, and no project structure, conventions
or test harness on which later features (Training sessions, training content, maybe a backend) could be built
without rework.

## Solution

A first feature for managing **Trainings**, built on a deliberately chosen foundation:

- A **Training list** screen („Treningi”) showing my Trainings in the order I created them, with a Polish plural
  count, a centred empty state when there are none, and a FAB to add one.
- Each row shows the Training's name and a ⋮ button that opens a bottom sheet with **Edytuj** and **Usuń**.
- A **Training editor** screen used both to add („Nowy trening”) and edit („Edytuj trening”) a Training: one name
  field and a full-width „Zapisz” button pinned above the keyboard, disabled while the trimmed name is empty.
- Deleting is immediate; a snackbar „Usunięto „<name>”” offers „Cofnij”, which puts the Training back exactly where
  it was.
- Underneath: a multi-module Gradle project with convention plugins, Room persistence behind a repository seam that a
  backend could later replace, Koin DI, MVI presentation, Navigation 3, an app-agnostic design system, static analysis,
  and an acceptance-test suite that drives the whole app through its UI.

The app is dark-theme only, all user-visible text is Polish, and all code is English.

## User Stories

### Training list

1. As the user, I want to see all my Trainings on the start screen, so that I know what I have configured.
2. As the user, I want Trainings listed in the order I created them, oldest first, so that the list is stable and
   predictable.
3. As the user, I want the screen titled „Treningi” in a centred top bar, so that I know where I am.
4. As the user, I want a count such as „1 trening”, „2 treningi”, „5 treningów” above the list, so that I see how many
   Trainings I have in correct Polish.
5. As the user, I want the count hidden when I have no Trainings, so that the empty screen stays clean.
6. As the user, I want each Training as a separate rounded row, so that rows are easy to tell apart.
7. As the user, I want long names to wrap onto several lines instead of being cut off, so that I can always read the
   full name.
8. As the user, I want a centred „Brak treningów” message with a hint „Dodaj pierwszy trening przyciskiem +” when the
   list is empty, so that I know what to do on first launch.
9. As the user, I want the empty message to appear only once the data has actually loaded, so that it never flashes
   briefly when I do have Trainings.
10. As the user, I want a FAB in the bottom-right corner, so that I can add a Training from the list at any time.
11. As the user, I want the FAB visible even when the list is empty, so that the hint in the empty state makes sense.
12. As the user, I want tapping a row itself to do nothing for now, so that I don't open something by accident; only
    ⋮ opens options.
13. As the user, I want system back on the list to leave the app, so that it behaves like any start screen.

### Options sheet

14. As the user, I want a ⋮ button on every row, so that I can reach actions for that Training.
15. As the user, I want ⋮ to open a bottom sheet with „Edytuj” and „Usuń”, so that the actions don't clutter the list.
16. As the user, I want the sheet to close as soon as I choose an action, so that I immediately see its effect.
17. As the user, I want to dismiss the sheet by swiping it down or tapping outside, so that I can change my mind.

### Adding a Training

18. As the user, I want tapping the FAB to open an empty editor titled „Nowy trening”, so that I can name a new
    Training.
19. As the user, I want the name field focused and the keyboard open when adding, so that I can start typing at once.
20. As the user, I want „Zapisz” disabled while the name is empty or only whitespace, so that I can't create a nameless
    Training.
21. As the user, I want the name trimmed before it is saved, so that stray spaces don't end up in my list.
22. As the user, I want duplicate names allowed, so that I'm never blocked by a name I already used.
23. As the user, I want no limit on name length, so that I can name a Training however I like.
24. As the user, I want „Zapisz” pinned to the bottom and lifted above the keyboard, so that I can save without closing
    the keyboard.
25. As the user, I want the keyboard's Done key to save when saving is allowed, so that I can add a Training without
    reaching for the button.
26. As the user, I want to return to the list after saving, with the new Training at the end, so that I see the result.
27. As the user, I want a double tap on „Zapisz” to save only once, so that I don't get duplicate Trainings by
    accident.

### Editing a Training

28. As the user, I want „Edytuj” in the sheet to open the editor titled „Edytuj trening” with the current name filled
    in, so that I can rename it.
29. As the user, I want the keyboard to stay closed when editing until I tap the field, so that I can first check the
    current name.
30. As the user, I want a renamed Training to keep its position in the list, so that editing doesn't reorder my
    Trainings.
31. As the user, I want the same trimming and blank-name rules when editing as when adding, so that the rules are
    consistent.
32. As the user, I want the editor to simply close if the Training I'm editing no longer exists, so that I never edit
    something that's gone.

### Leaving the editor

33. As the user, I want a ← button in the editor's top bar, so that I can leave without saving.
34. As the user, I want back (← or system back) to discard my input without asking, so that leaving is quick.
35. As the user, I want back from the editor without saving to change nothing in the list, so that I can safely abandon
    an edit.
36. As the user, I want rapid taps on back, or a back press racing a save, never to pop more than the editor or open it
    twice, so that navigation never breaks.

### Deleting and undo

37. As the user, I want „Usuń” to delete the Training immediately, without a confirmation dialog, so that deleting is
    fast.
38. As the user, I want a snackbar „Usunięto „<name>”” with a „Cofnij” action after deleting, so that I can recover from
    a mistake.
39. As the user, I want „Cofnij” to restore the Training in its original position, so that undo really undoes.
40. As the user, I want only my latest delete to be undoable, with an older snackbar replaced by the newer one, so that
    undo is never ambiguous.
41. As the user, I want the FAB to move up while the snackbar is shown, so that the snackbar never hides it.

### Look and language

42. As the user, I want a dark theme everywhere, including system bars, so that the app is easy on my eyes.
43. As the user, I want all text and accessibility descriptions in Polish, so that the app speaks my language.
44. As the user, I want icon buttons to have spoken descriptions („Więcej opcji”, „Dodaj trening”, „Wstecz”), so that
    the app works with TalkBack.
45. As the user, I want my Trainings to survive closing and reopening the app, so that I configure them only once.

### Developer (the author maintaining the app)

46. As the developer, I want each feature split into domain, data and presentation modules, so that layers can't leak
    into each other.
47. As the developer, I want build configuration in reusable `convention.*` plugins, so that adding a module is a few
    lines.
48. As the developer, I want all versions in the version catalog, so that bumps happen in one place.
49. As the developer, I want ktlint and Detekt (with Compose rules) in `./gradlew check` and ktlint in a pre-commit
    hook, so that style is enforced by tools, not by memory.
50. As the developer, I want persistence behind a domain `TrainingRepository`, so that Room can later be replaced or
    fronted by a backend without touching the UI.
51. As the developer, I want every UI component as an app-agnostic `App*` composable in a design-system module, so that
    the look can change in one place and the module could be reused.
52. As the developer, I want acceptance tests that describe behaviour in domain words and drive the real app, so that
    tests survive refactors and a future backend.
53. As the developer, I want screenshot tests for every preview, so that visual regressions show up in review.
54. As the developer, I want a test that verifies the Koin graph on the JVM, so that a missing binding fails the build,
    not the app.
55. As the developer, I want every implementation ticket small, single-purpose and reviewable in about 15 minutes, so
    that I can review each one by hand.

## Implementation Decisions

### Product rules

- A Training is `id: Long` + `name: String`. The name is trimmed before saving; a whitespace-only name counts as empty.
  Duplicates allowed, no length limit.
- List order is creation order (ascending id); the repository guarantees it, callers never sort.
- Delete has no confirmation; undo re-inserts the deleted Training with its original id, so it reappears in place.
  Only the latest delete is undoable; the pending undo does not survive process death.
- Edit mode loads the Training by id; if it is missing, the editor navigates back.
- Back from the editor never asks; input is discarded.

### Toolchain and modules

- Kotlin 2.4.21, KSP 2.3.13, AGP 9.5.0, Compose BOM 2026.09.00 (Compose 1.12.1), JVM target/toolchain 17. A single
  `kotlin` catalog version for built-in Kotlin and `kotlin-jvm`. Never apply `kotlin-android`.
- Libraries: Navigation 3 1.2.1, `lifecycle-viewmodel-navigation3` 2.11.0, Room 3.0.3 (`androidx.room3`) with KSP,
  Koin 4.2.2 (BOM), kotlinx-serialization, coroutines, `material-icons-extended`, JUnit 6 via mannodermaus plugin
  2.0.2, Truth, Paparazzi 2.0.0-alpha5.1. Every coordinate in `libs.versions.toml`; BOMs where available.
- Modules: `:app`, `:feature:trainings:domain` (pure Kotlin/JVM), `:feature:trainings:data`,
  `:feature:trainings:presentation`, `:core:presentation`, `:core:design-system`, `:core:database`. `:core:domain` is
  **not created** — nothing in this feature needs it (no `Result`/`DataError` is built); it appears with its first
  content. No `:core:data` until a remote API exists. Features never depend on each other.
- Namespace and package equal the module path under `pl.skolim.trainingdiary`. Type-safe project accessors on.
  `org.gradle.parallel=true`.
- Included build `build-logic/convention` with plugins `convention.android.application`, `.android.library`,
  `.android.feature` (applies kotlinx-serialization and `navigation3-runtime`), `.jvm.library`, `.android.compose`,
  `.android.room` (sets the `room3` schema directory), `.koin` (only `platform(koin-bom)` + `koin-core`), `.testing`,
  `.static-analysis`, `.paparazzi` (only on modules with screenshot tests). Base plugins apply `testing` and
  `static-analysis`. Untyped `CommonExtension`, `compilerOptions`.
- Compiler: `allWarningsAsErrors = true` with a per-module Gradle-property opt-out for unfixable experimental-API
  warnings; `@OptIn` at the use site.
- Visibility: `data` and `presentation` are `internal` by default. Their public surface is the Koin module `val`, the
  Nav3 keys and the entries function. Domain types are public. Explicit API mode off.
- The template's `ui/theme` (`Color.kt`, `Type.kt`, light scheme) and `Example*` tests are deleted.

### Static analysis and style

- Tools are the whole style guide. Root `.editorconfig`: `ktlint_code_style = ktlint_official`, `max_line_length = 120`,
  plus the one Compose adaptation `ktlint_function_naming_ignore_when_annotated_with = Composable` (ktlint 1.8.0 via
  ktlint-gradle 14.2.0).
- Detekt 2.0.0-alpha.6 (`dev.detekt`), `buildUponDefaultConfig`, no baseline, plus compose-rules
  `io.nlopez.compose.rules:detekt` 0.6.8. Detekt 2 has no `maxIssues`; `failOnSeverity = Info` makes any finding fail.
  `config/detekt/detekt.yml` holds only Compose adaptations a composable proves necessary: `FunctionNaming` ignoring
  `@Composable` and `UnusedPrivateFunction` ignoring `@Preview` (the template's composables needed both);
  `LongParameterList` ignoring `@Composable` when a composable trips it. No other rule toggled.
- The gate runs the type-resolving `detektMain` / `detektTest` tasks. Backtick test names count toward 120 columns.
- Versioned pre-commit hook running `ktlintCheck`, installed with `git config core.hooksPath config/git-hooks`.
- Unenforced review preferences (expression bodies, `when` without `else` for sealed/enum, named literal arguments,
  no KDoc, no committed TODO, …) are in the house-style ticket; nothing fails on them.

### Domain (`:feature:trainings:domain`)

- `data class Training(val id: Long, val name: String)`.
- Pure function `isValidTrainingName(name)`: true when the trimmed name is not blank. Used by the editor's `canSave`
  and by the repository's safety net.
- `TrainingRepository` interface:
  - `observeTrainings(): Flow<List<Training>>` — creation order.
  - `suspend getTraining(id: Long): Training?` — `null` means missing.
  - `suspend addTraining(name: String)`, `suspend updateTraining(training: Training)`,
    `suspend deleteTraining(id: Long)`, `suspend restoreTraining(training: Training)` — all return `Unit`.
- No Koin module and no Koin dependency in domain.

### Persistence (`:core:database`, `:feature:trainings:data`)

- `:core:database` (public): `TrainingEntity` (table `trainings`, `@PrimaryKey(autoGenerate = true) id: Long`, `name`),
  `TrainingDao` (Flow query `ORDER BY id`, suspend by-id lookup, insert, update, delete by id, insert-with-id for
  restore), `TrainingDiaryDatabase` (`version = 1`, `exportSchema = true`, schema JSON committed).
- Room 3 defaults to `AUTOINCREMENT`, so deleted ids are never reused and the old id can be re-inserted on undo.
- Driver set explicitly: `setDriver(AndroidSQLiteDriver())`. No `withContext` (Room 3 uses `Dispatchers.IO`).
- Migrations: auto-migrations first; never `fallbackToDestructiveMigration`.
- `coreDatabaseModule` provides the database and each DAO; data modules inject DAOs, never the database.
- `:feature:trainings:data`: `internal RoomTrainingRepository` (no `Impl` suffix) using the DAO as its only source;
  mapper extensions `TrainingEntity.toTraining()` / `Training.toTrainingEntity()`. It trims the name and `require`s
  `isValidTrainingName`.
- Error policy: nothing is caught; every SQLite exception crashes. Catch only exception types that can be named, never
  `Exception`/`Throwable`. No logging library, no `printStackTrace`. Typed `Result`, `DataError`, `toUiText()` and a
  write-failure snackbar are **not built** (dormant until the first expected failure). The installed
  `android-error-handling` skill still holds baseline content that contradicts this; this spec wins.

### Dependency injection (Koin)

- Koin DSL only, no annotations. One public top-level `val` Koin module per Gradle module that provides something:
  `coreDatabaseModule`, `trainingsDataModule`, `trainingsPresentationModule`. Everything they bind stays `internal`.
- `singleOf` / `viewModelOf` by default, binding via `{ bind<Interface>() }`; lambdas only for the Room builder and DAO
  accessors; no `factory`.
- `startKoin { androidContext(...); modules(appModules) }` in `TrainingDiaryApp : Application`; no logger. `:app`
  exposes its module list as `appModules` (reused by the acceptance tests).
- A ViewModel needing nav arguments takes its Nav3 key: `TrainingEditorViewModel(key: TrainingEditorKey, repository)`,
  injected with `koinViewModel { parametersOf(key) }`. `koinViewModel()` only in Root composables, as the last
  defaulted parameter; ViewModels never passed down.
- No dispatcher bindings.
- Extra artifacts declared per module: `koin-androidx-compose` (presentation), `koin-android` (`:app`), `koin-test`
  (`:app` tests).

### Navigation (Navigation 3)

- Public keys in the presentation module's root package, next to the entries function:
  `@Serializable data object TrainingListKey : NavKey` and
  `@Serializable data class TrainingEditorKey(val trainingId: Long? = null) : NavKey` (`null` = add mode).
- Public `EntryProviderScope<NavKey>.trainingsEntries(backStack)`; Roots stay `internal`. No
  `koin-compose-navigation3`.
- `:app` has one `AppNavDisplay`, called from `MainActivity` inside `AppTheme`, holding
  `rememberNavBackStack(TrainingListKey)`, `onBack = removeLastOrNull`, and the saveable-state and ViewModel-store
  entry decorators. Default transitions and predictive back.
- Guards in `:core:presentation`: `NavBackStack<NavKey>.push(key)` adds only if the key isn't already on top;
  `pop(key)` removes only if the key is on top. Every Root `onBack` calls `pop(key)`.
- The list learns about saves by re-observing the Room `Flow`; no result passing. No deep links, no adaptive scenes.
- `navigation3-runtime` in feature presentation modules and `:core:presentation`; `navigation3-ui` and
  `lifecycle-viewmodel-navigation3` in `:app` only.

### Presentation (MVI)

- Two screens, one package each: `TrainingList` and `TrainingEditor` (one editor for add and edit, mode derived from
  `TrainingEditorKey.trainingId`). One type per file; Root and Screen share `<Screen>Screen.kt`. Actions named
  `On<Thing><Gesture>`.
- Screen composables are `(state, onAction)` (+ `snackbarHostState` on the list). The Root intercepts navigation
  Actions (`OnAddClick`, `OnEditClick(id)`, `OnBackClick`), calls `onAddTraining()` / `onEditTraining(id)` /
  `onBack()`, and forwards every Action to the ViewModel.
- Nothing runs in `init`.
- **List ViewModel**: state = `combine(observeTrainings(), UI-only MutableStateFlows)` +
  `stateIn(WhileSubscribed(5_000))`. State holds the domain `Training` list directly (no UI model), `isLoading = true`
  until the first emission (no spinner; only top bar and FAB until then), and `optionsSheetTrainingId: Long?`.
  Choosing Edytuj or Usuń closes the sheet. Delete keeps the deleted `Training` and emits `TrainingDeleted(name)`;
  `OnUndoClick` restores the kept Training.
- **List Root** owns `SnackbarHostState`; it dismisses the current snackbar before showing a new one, and
  `ActionPerformed` sends `OnUndoClick`.
- **Editor ViewModel**: private `MutableStateFlow`, loaded from `onStart` + `stateIn` with a `hasLoaded` guard. State:
  `name`, `isEditMode`, `isLoading` (only while edit mode loads), `isSaving`; computed
  `canSave = !isSaving && isValidTrainingName(name)`. Edit mode draws the form only after loading. `OnSaveClick` (and
  keyboard Done) is ignored unless `canSave`. Events `TrainingSaved` and `TrainingMissing`; the Root maps both to
  `onBack()` (which calls `pop(key)`).
- Events transport: `Channel` → `receiveAsFlow()` → `ObserveAsEvents` (in `:core:presentation`, `Main.immediate`,
  `STARTED`).
- Add-mode autofocus is a Compose-internal effect; edit mode focuses nothing.
- No `SavedStateHandle`; losing input on process death is accepted.
- Strings are chosen in composables from State (`isEditMode`, `pluralStringResource` on the list size); State holds no
  strings; `UiText` is not built. Provisional — the user may revisit after seeing code.

### Design system (`:core:design-system`)

- Every component is app-agnostic, public, `App`-prefixed: `AppScaffold`, `AppTopBar` (centre-aligned),
  `AppTopBarWithBack` (small, ← with `design_system_cd_back`), `AppIconButton`, `AppFab`, `AppTextField`, `AppButton`,
  `AppListRow`, `AppBottomSheet` + `AppSheetItem`, `AppEmptyState`. Slots only on `AppListRow` and `AppBottomSheet`.
  M3 counterparts are wrapped where they exist; containers/layouts use `Surface` + `Row`/`Column`/`Box` (no M3
  `ListItem`). No `AppMonogram`.
- `AppTheme(content)`: dark-only, `darkColorScheme()`, default `Typography()` and `Shapes()`. `enableEdgeToEdge` with
  dark system-bar styles; XML window theme with a dark parent.
- `Spacing` object (4/8/12/16/24/32dp); component dimensions stay `dp` literals.
- `AppIcons` object exposing Outlined `ImageVector`s from `material-icons-extended` (add, more_vert, edit, delete,
  arrow_back, fitness_center); a missing icon goes to `res/drawable` and joins the object.
- `AppPreview { }` public wrapper used by every preview.
- The feature keeps only `TrainingRow` (`AppListRow` with name + ⋮). Feature code may use from M3 only
  `MaterialTheme`, `Text`, `SnackbarHostState`, `SnackbarResult` (checked in review).
- Clickable icons sit in a 48dp `IconButton` with a `contentDescription` from resources. Modifier extensions are never
  `@Composable`.

### Visual details (from the prototypes)

- **List**: centred top bar „Treningi” (titleLarge). Count label titleSmall in `primary`, padding 16dp top / 32dp
  start, hidden when empty. List horizontal padding 16dp, 12dp between rows, bottom padding leaving room for the FAB.
  Row: `surfaceContainer`, 16dp corners, min height 64dp, padding 16dp start / 4dp end; name bodyLarge `onSurface`,
  wrapping; trailing 48dp `more_vert` in `onSurfaceVariant` („Więcej opcji”). FAB: standard 56dp, 16dp corners,
  `primaryContainer`, `add` („Dodaj trening”), 16dp margin, moves above the snackbar. Sheet: modal,
  `surfaceContainerLow`, 28dp top corners, drag handle, two 56dp items `edit` „Edytuj” and `delete` „Usuń”, no title.
  Snackbar: standard M3, default duration. Empty state: centred, dimmed 64dp `fitness_center`, „Brak treningów”
  (22sp `onSurface`), „Dodaj pierwszy trening przyciskiem +” (14sp `onSurfaceVariant`).
- **Editor**: small top bar, ← („Wstecz”), title „Nowy trening” / „Edytuj trening”, no actions. One outlined
  full-width single-line field labelled „Nazwa”, 16dp screen padding, no supporting/error text. Filled „Zapisz”,
  full width, 56dp high, fully rounded, label 16sp medium, in a 16dp-padded bottom area pinned to the bottom with
  `imePadding`. Disabled look is the standard M3 disabled filled button.

### Strings

- Polish in the default `values/` of the module that uses it. Names `<screen>_<element>`, `<screen>_cd_<thing>`;
  design-system strings prefixed `design_system_`. `<plurals>` with Polish forms (trening / treningi / treningów);
  typographic quotes and „…” written literally.

## Testing Decisions

### What makes a good test

- A test describes behaviour the user can observe, in domain words, and never names Compose, Room, ViewModels or
  modules. It must not care whether data comes from Room or a future backend.
- Tests ship in the same ticket as the behaviour they cover, written test-first. Scaffolding tickets have no tests,
  except the one throwaway self-test each risk check ships (below).

### Seams

There are two seams, both already agreed in the Testing strategy ticket:

1. **The whole app through its UI** (primary). Real Koin graph, ViewModels, repository and navigation; only the
   database is swapped for in-memory Room through a Koin override. A future backend would be faked with OkHttp
   `MockWebServer` at the same seam.
2. **Previews** (visual). Every preview in `:core:design-system` and `:feature:trainings:presentation` is snapshotted
   with Paparazzi.

Plus one JVM check of the DI graph (`verifyAll`), and unit tests only for pure functions with many inputs.

### Acceptance tests (primary suite)

- Farley's four layers: test cases in domain words → `TrainingsDsl` → `TrainingsUiDriver` (the only layer that knows
  Compose) → the real app.
- Isolation: `AcceptanceTestRunner` swaps in `AcceptanceTestApplication`; a JUnit extension starts Koin with
  `appModules` + the in-memory database override before each test and stops it after, so every test has a fresh
  database. No Test Orchestrator.
- Preconditions go through the UI (`givenTrainings` adds them as the user would). If the suite gets slow, report it;
  the user decides about a backdoor driver.
- Locators: visible text, then content description, then `testTag` only where neither exists. All text read from
  string resources, never literal Polish in tests. Steps wait on conditions (`waitUntil…`), never `Thread.sleep`.
- Placement: `:app` instrumented tests, packages `…acceptance.trainings`, `…acceptance.dsl`, `…acceptance.driver`.
- Runtime: instrumented (not Robolectric), JUnit 6 on device via mannodermaus' Compose extension; JUnit 4 is the
  approved fallback. Gradle Managed Device, Pixel 9 profile, headless; approved fallback `connectedDebugAndroidTest`
  on a running emulator.
- The 12 cases:
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
- "Editing a missing Training navigates back" is not tested until a backdoor driver exists.

### Screenshot tests

- Paparazzi 2.0.0-alpha5.1 via `convention.paparazzi` (Paparazzi + JUnit 4 + vintage engine), only on modules with
  screenshot tests. A test in the same module calls each preview directly, so previews are `internal`.
- Previews: one per design-system component; per-State Screen previews named `<Screen>Preview<State>` with literal
  State.
- One configuration: Pixel 9 (custom `DeviceConfig` if no constant), dark, `pl`, font scale 1.0. Reference images in
  each module's snapshot directory, plain git. `check` runs `verifyPaparazziDebug`; a ticket that changes UI on
  purpose records and commits new images.

### Other tests

- `KoinModulesTest` in `:app` JVM tests: `verifyAll` with `extraTypes = listOf(Context::class)` and an
  `injectedParameters` entry for `TrainingEditorViewModel` ← `TrainingEditorKey`. Every new `parametersOf` type gets an
  entry.
- Unit tests (JUnit 6 + Truth, no Turbine, no MockK) only for pure functions with many inputs, written with
  `/mattpocock-skills:tdd`, break vectors listed in the change summary. In this feature that is at most
  `isValidTrainingName`. No unit tests for ViewModels, the repository, mappers or the push/pop guards.

### Gate and prior art

- Gate before review: `./gradlew check` (ktlint, Detekt, JVM tests, Koin graph, Paparazzi verify) plus the
  managed-device test task once it exists. Every ticket leaves the gate green and the app launching.
- There is no prior art in the codebase (only template `Example*` tests, which are deleted). The installed skills are
  the reference: `android-acceptance-testing`, `android-screenshot-testing`, `android-unit-testing`.

## Out of Scope

- Backend / remote sync, auth (only the swappable repository seam is built), `:core:data`, Ktor.
- Typed `Result`, `DataError`, `toUiText()`, write-failure snackbars, `:core:domain`.
- Light theme; localization beyond Polish.
- Unsaved-changes dialog on back; delete confirmation.
- Row tap behaviour; drag-to-reorder; search; sorting other than creation order.
- Training session and any Training content beyond a name.
- ViewModel-driven navigation (the Root-intercept pattern is used for now); deep links; Nav3 Result API; adaptive
  layouts; `koin-compose-navigation3`.
- Process-death restoration of editor input or pending undo.
- Monogram in the row.
- CI pipeline.

## Further Notes

- **Ticket order for `/to-tickets`** (from the slicing ticket): infrastructure lands just before it is needed, in this
  order: (1) toolchain bump + build-logic and catalog; (2) static analysis + git hook; (3) module skeletons, template
  theme and `Example*` tests deleted; (4) acceptance-test harness with one throwaway smoke test; (5) Paparazzi wiring
  with one trivial snapshot; (6) design-system foundation (`AppTheme`, `Spacing`, `AppIcons`, `AppPreview`), replacing
  the trivial snapshot; (7) database + repository; (8) Koin wiring + navigation shell (`AppNavDisplay`, keys, empty
  entries, guards); (9) behaviour slices.
- **Behaviour slices**, test-first with their acceptance cases: S1 Empty list (1, replaces the smoke test); S2 Open
  editor for adding (11, 10); S3 Add a Training, with `isValidTrainingName` (2, 3, 4); S4 Top-bar count (9); S5 Edit
  via ⋮ → sheet → Edytuj (12, 5); S6 Delete via Usuń (6); S7 Undo (7, 8).
- **Components**: one small ticket per `App*` component (component + preview + snapshot) right before its first user;
  components that always travel together may share one. Behaviour slices add only presentation previews and snapshots
  for their own screen states.
- **Risk checks**: if the harness or Paparazzi ticket fails, it stops and reports, switching only to approved
  fallbacks (JUnit 4; `connectedDebugAndroidTest`). Paparazzi has no agreed fallback, so a failure goes back to the
  user. Kotlin above 2.2.10 is not officially tested with AGP 9.x; Detekt is an alpha.
- Each slice must have one purpose, be reviewable in about 15 minutes, and never mix scaffolding with behaviour.
  `/implement`'s automatic review stays on before the user's manual review. The user may amend skills between tickets;
  the installed `android-*` skills reflect every decision here except `android-error-handling` (see Persistence).
