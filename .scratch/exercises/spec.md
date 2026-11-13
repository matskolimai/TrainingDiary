# Spec: Exercises in Trainings

Status: ready-for-agent

Source: the Wayfinder map `map.md` in this directory, its resolved tickets under `issues/wayfinding/` (01–07), the
catalogue in `catalogue.md`, and `docs/adr/0002-screen-results-through-navigators.md`. Where a ticket carries a
"Superseded" note, the later decision wins: in particular **nothing is stored until „Zapisz”** (ticket 04 reversed
the charting note on immediate saving). Implementation tickets go directly under `issues/`.

## Problem Statement

My Trainings are only names. „Push day” tells me nothing about what I do in it: which Exercises, how many Sets, how
many reps and at what weight. I want each Training to hold its Training exercises with their Sets, so that the app
becomes the plan I follow at the gym, not just a list of labels. I don't want to type Exercise names or numbers on a
phone between Sets: picking from a fixed catalogue and stepping values with buttons should be enough. And I don't
want to lose an edit by pressing back by accident.

## Solution

The Training editor gains a list of **Training exercises** and an outlined „Dodaj ćwiczenie” button under it.

- **Adding** walks three Screens titled „Dodaj ćwiczenie”: the **Exercise category picker** (7
  categories with an Exercise count badge), the **Exercise picker** (a plain list of that category's
  Exercises) and the **Set editor** („Krok 3 z 3: <Exercise>”). „Dodaj do treningu” returns
  straight to the Training editor with the new Training exercise at the end of the list.
- The **Set editor** shows a „Serie” count with − / + and one card per Set holding a weight pill („0 kg”) and a reps
  pill („12 powt.”, or „12 s” for timed Exercises). Exactly one pill is focused; − / + at the right of the focused row
  change that value (weight by 1.5 kg, reps by 1); holding a button repeats. A new Training exercise starts with
  3 Sets of 0 kg × 12; a new Set copies the last one.
- Each Training exercise is a card with the Exercise name and „<category> · N serie”. Tapping it, or ⋮ → „Edytuj”,
  opens the Set editor titled with the Exercise name; „Gotowe” puts the changed Training exercise back in place.
  ⋮ → „Usuń” removes it.
- **Nothing is stored until „Zapisz”**, which stores the name and all Training exercises with their Sets at once.
- Leaving the Training editor or the Set editor with changes asks „Odrzucić zmiany?” („Anuluj” / „Odrzuć”).
- Deleting a Training deletes its Training exercises; „Cofnij” on the Training list brings all of it back.

Dark theme only, Polish UI, English code, as in the rest of the app.

## User Stories

### Training editor with Training exercises

1. As the user, I want the Training editor to list the Training's Training exercises under the name field, so that I
   see the whole plan of the Training in one place.
2. As the user, I want Training exercises listed in the order I added them, so that the list follows the order I
   train in.
3. As the user, I want each Training exercise as a separate rounded card like the Training list rows, so that the
   Screen looks consistent.
4. As the user, I want each card to show the Exercise name, so that I know which Exercise it is.
5. As the user, I want long Exercise names to wrap rather than be cut off, so that I can always read them.
6. As the user, I want each card to show „<Exercise category> · <N> serie” with correct Polish plurals („1 seria”,
   „3 serie”, „5 serii”), so that I see the category and the size of the Training exercise at a glance.
7. As the user, I want the card not to show reps or weight, so that the list stays calm and short.
8. As the user, I want an outlined „Dodaj ćwiczenie” button with a + icon under the last card, so that I can add a
   Training exercise where the list ends.
9. As the user, I want „Dodaj ćwiczenie” to scroll with the list, so that it never covers a card.
10. As the user, I want nothing between the name field and „Dodaj ćwiczenie” when a Training has no Training
    exercises, no header and no hint, so that an empty Training stays minimal.
11. As the user, I want „Dodaj ćwiczenie” available even before I have typed a name for a new Training, so that I can
    build the plan first and name it later.
12. As the user, I want the title to stay „Nowy trening” until I save a new Training, so that I know it isn't stored
    yet.
13. As the user, I want „Zapisz” to stay pinned at the bottom above the keyboard, so that I can save from anywhere in
    a long list.
14. As the user, I want „Zapisz” enabled whenever the name is valid, even if I changed nothing, so that it behaves as
    it does today.

### Picking an Exercise

15. As the user, I want „Dodaj ćwiczenie” to open a Screen listing the 7 Exercise categories (Klatka piersiowa, Plecy,
    Barki, Biceps, Triceps, Nogi, Brzuch) in that order, so that I narrow down the Exercise by body part first.
16. As the user, I want each Exercise category row to show how many Exercises it holds in a small badge, so that I
    know what to expect before opening it.
17. As the user, I want the picker titled „Dodaj ćwiczenie”, so that I know I'm adding an Exercise. (The step
    subtitle „Krok 1 z 3: kategoria” was dropped: the top bar shows only its title.)
18. As the user, I want tapping an Exercise category to list its Exercises in catalogue order, so that I can pick one.
19. ~~As the user, I want the Exercise picker's subtitle to read „Krok 2 z 3: <Exercise category>”, so that I see
    which category I opened.~~ Dropped with the other step subtitles: the top bar shows only „Dodaj ćwiczenie”.
20. As the user, I want the whole row to be the tap target in both pickers, so that picking is easy with one thumb.
21. As the user, I want plain lists without search, so that the pickers stay simple; the catalogue is short.
22. As the user, I want back on the Exercise picker to return to the Exercise categories, so that I can choose
    another category.
23. As the user, I want back on the Exercise category picker to return to the Training editor with my unsaved changes
    intact, so that starting to add costs nothing.
24. As the user, I want back in the pickers never to ask for confirmation, so that I'm not bothered when nothing can
    be lost.
25. As the user, I want to be able to pick an Exercise that is already in the Training, so that I can plan it twice
    (e.g. at the start and end).

### Set editor, adding

26. As the user, I want picking an Exercise to open the Set editor titled „Dodaj ćwiczenie” with the subtitle
    „Krok 3 z 3: <Exercise>”, so that I know which Exercise I'm planning.
27. As the user, I want a new Training exercise to start with 3 Sets of 0 kg × 12, so that a typical plan needs few
    taps.
28. As the user, I want the Set editor to open with Set 1's weight focused, so that I can start adjusting right away.
29. As the user, I want a pinned „Dodaj do treningu” button, so that I can confirm the Training exercise.
30. As the user, I want „Dodaj do treningu” to take me straight back to the Training editor, past both pickers, with
    the new Training exercise at the end of the list, so that adding ends where it started.
31. As the user, I want the same Exercise added twice to show as two cards in added order, so that duplicates behave
    predictably.
32. As the user, I want back in the Set editor without changes to return to the Exercise picker without adding
    anything, so that I can pick a different Exercise.

### Number of Sets

33. As the user, I want a „Serie” row with − and + around the count at the top of the Set editor, so that I can change
    how many Sets there are.
34. As the user, I want + to append a Set with the weight and reps of the current last Set, so that adding a Set
    rarely needs further changes.
35. As the user, I want − to remove the last Set, so that the Sets I already adjusted at the top stay as they are.
36. As the user, I want at least 1 and at most 10 Sets, with − disabled at 1 and + disabled at 10, so that I can't
    create an empty or absurd Training exercise.
37. As the user, I want focus to move to the same field of the new last Set when the focused Set is removed, so that
    something is always focused.

### Reps and weight

38. As the user, I want one card per Set showing its number, a weight pill and a reps pill, so that I see every Set at
    once.
39. As the user, I want tapping a pill to focus it, with a clear highlight on the focused pill, so that I know which
    value the buttons will change.
40. As the user, I want exactly one value focused at a time, so that there is never doubt about what ± changes.
41. As the user, I want − and + only at the right end of the focused row, so that the other rows stay uncluttered.
42. As the user, I want weight to step by exactly 1.5 kg, so that it matches my dumbbell increments.
43. As the user, I want weight shown with a Polish decimal comma and no „,0” on whole numbers („0 kg”, „1,5 kg”,
    „3 kg”, „13,5 kg”), so that it reads naturally.
44. As the user, I want weight limited to 0–300 kg and reps to 1–999, with the button disabled at a limit, so that
    values stay sensible.
45. As the user, I want reps to step by 1, so that I can set any count.
46. As the user, I want holding − or + to repeat the step until I release it or hit a limit, so that large changes
    don't take dozens of taps.
47. As the user, I want ± to change only the focused value of the focused row, so that other Sets are never changed by
    accident.
48. As the user, I want timed Exercises (Plank, Deska boczna) to show „12 s” instead of „12 powt.”, with the same
    start, step and limits, so that their numbers mean seconds.
49. As the user, I want values only stepped, never typed, so that I don't need the keyboard between Sets.

### Editing a Training exercise

50. As the user, I want tapping a Training exercise card to open the Set editor with its Sets, so that I can adjust
    them in one tap.
51. As the user, I want a ⋮ button on each card that opens a bottom sheet with „Edytuj” and „Usuń”, like the Training
    list, so that actions are found where I expect them.
52. As the user, I want ⋮ → „Edytuj” to open the same Set editor as a row tap, so that both ways behave the same.
53. As the user, I want the Set editor in edit mode titled with the Exercise name and subtitled with its Exercise
    category, so that I know what I'm editing.
54. As the user, I want the Exercise of a Training exercise to be fixed while editing, so that editing only means
    changing Sets; I delete and re-add to change the Exercise.
55. As the user, I want a pinned „Gotowe” button that returns to the Training editor with the changed Training
    exercise in the same place, so that the order is kept.
56. As the user, I want back without changes in edit mode to return to the Training editor, so that looking costs
    nothing.

### Deleting a Training exercise

57. As the user, I want ⋮ → „Usuń” to remove the Training exercise from the list immediately, so that deleting is
    quick.
58. As the user, I want no undo snackbar for a deleted Training exercise, so that the Screen stays simple; I can
    still leave without saving.
59. As the user, I want a removal to be stored only when I press „Zapisz”, so that it follows the same rule as every
    other change.

### Saving

60. As the user, I want „Zapisz” to store the name and all Training exercises with their Sets together, so that a
    Training is never half saved.
61. As the user, I want reopening a saved Training to show its Training exercises and Sets exactly as I saved them, in
    the same order, so that the plan is reliable.
62. As the user, I want nothing stored by the pickers or the Set editor, so that only one button commits changes.
63. As the user, I want leaving the Training editor without saving to store nothing, so that I can abandon an edit.

### Leaving with unsaved changes

64. As the user, I want back on the Training editor after a change to ask „Odrzucić zmiany?”, so that I don't lose
    work by accident.
65. As the user, I want the same question in the Set editor, in add and edit mode, so that adjusted Sets aren't lost
    silently.
66. As the user, I want the top-bar back arrow and system back to behave the same, so that there's no hidden way to
    lose changes.
67. As the user, I want the dialog to have only the title „Odrzucić zmiany?” and the buttons „Anuluj” and „Odrzuć”,
    so that it reads at a glance.
68. As the user, I want „Odrzuć” to leave and drop my changes, so that I can abandon an edit on purpose.
69. As the user, I want „Odrzuć” in the Set editor's add mode to return to the Exercise picker, so that it matches
    plain back.
70. As the user, I want „Anuluj”, a tap outside or back to close the dialog and keep me on the Screen with my changes,
    so that I can change my mind.
71. As the user, I want a change I undid myself (typing a letter and deleting it, adding and removing a Training
    exercise, + then −) not to count, so that I'm only asked when something would really be lost.
72. As the user, I want leading or trailing whitespace typed in the name to count as a change, so that the rule is
    simple and predictable.
73. As the user, I want back without changes to just go back, so that I'm never asked needlessly.

### Deleting a Training

74. As the user, I want deleting a Training to delete its Training exercises and Sets, so that nothing orphaned
    remains.
75. As the user, I want „Cofnij” on the Training list to restore the Training with all its Training exercises and
    Sets, in their order, so that undo is complete.

### Look and language

76. As the user, I want all new Screens dark, Material 3 and in Polish, so that they fit the rest of the app.
77. As the user, I want all rows, cards and pills to follow the visual details from the prototypes, so that the
    feature looks as agreed.

### Developer (the author maintaining the app)

78. As the developer, I want the catalogue behind an `ExerciseCatalogue` interface in a shared module, so that
    user-defined Exercises later replace only its implementation.
79. As the developer, I want stable `Long` ids on Exercises and Exercise categories, so that stored Training exercises
    keep pointing at the right Exercise and a later Room table can seed the same ids.
80. As the developer, I want weight stored in whole grams, so that 1.5 kg steps stay exact.
81. As the developer, I want „Zapisz” to write in one transaction, so that a crash can't leave half a Training.
82. As the developer, I want results between Screens to travel through the screen navigators, so that the ViewModel
    acceptance channel tests the real path (ADR 0002).
83. As the developer, I want one reusable discard dialog in the design system, so that its look lives in one place.
84. As the developer, I want every new case to run on both acceptance channels, so that mutants are found quickly on
    the JVM.

## Implementation Decisions

### Product rules

- **Saving**: nothing is stored until „Zapisz” in the Training editor, in add and edit mode. „Zapisz” stores the
  trimmed name and the full list of Training exercises with their Sets in one go, then closes the Screen. The Set
  editor and pickers never store.
- „Dodaj do treningu” appends the Training exercise to the Training editor's unsaved list; „Gotowe” replaces the
  edited one at its index; „Usuń” removes it from the unsaved list. No undo for removing a Training exercise.
- Duplicates allowed; order = added order; no reordering.
- Limits: 1–10 Sets; weight 0–300 kg in 1.5 kg steps; reps 1–999 for every Exercise. A button at its limit is
  disabled. A new Training exercise = 3 Sets of 0 kg × 12. + appends a copy of the last Set; − removes the last Set.
- Values are stepped only. Hold-to-repeat: first repeat after ~400 ms, then every ~90 ms, until release or a limit.
- Focus: exactly one value (Set index + weight or reps). Opens on Set 1's weight. Removing the focused Set moves focus
  to the same field of the new last Set.
- Timed Exercises (`isTimed`: only Plank and Deska boczna) show reps with „s” instead of „powt.”.
- Weight display: Polish decimal comma, no „,0”: „0 kg”, „1,5 kg”, „3 kg”, „13,5 kg”.
- **Discard dialog** on the Training editor and Set editor (both modes), for top-bar and system back. **Changed** =
  current values differ by value from the Screen's starting values (no "touched" flag). The Set editor in add mode
  starts from 3 × 0 kg × 12. Whitespace in the name is a change. „Odrzuć” leaves; „Anuluj”, outside tap or back
  dismisses. The pickers never ask.
- Deleting a Training cascades to its Training exercises and Sets; „Cofnij” restores all of it with the old ids.
- Process death loses unsaved input, including changes spanning several Screens; no saved-state machinery.

### Catalogue and modules

- **`:core:domain`** (new, pure JVM): `Exercise(id: Long, name: String, category: ExerciseCategory, isTimed:
  Boolean)`, `ExerciseCategory(id: Long, name: String)`, and `ExerciseCatalogue` with `categories()`,
  `exercises(categoryId)`, `exercise(id)`, synchronous while the catalogue is constants.
- **`:feature:exercises:data`** (new): the hardcoded catalogue from `catalogue.md` implementing `ExerciseCatalogue`,
  bound in Koin. Ids are `Long` constants 1, 2, … for categories and Exercises; names are plain Polish strings (data,
  not string resources); list order is display order.
- **`:feature:exercises:presentation`** (new): the Exercise category picker and Exercise picker. No
  `:feature:exercises:domain` (it would be empty). Nothing is built for user-defined Exercises beyond this shape.
- **`:feature:trainings`** holds the Set editor and the Training exercise model. Domain:
  `TrainingExercise(exercise: Exercise, sets: List<ExerciseSet>)`, `ExerciseSet(reps: Int, weightGrams: Int)`;
  `Training` gains `exercises: List<TrainingExercise>`. Code says `ExerciseSet` to avoid `kotlin.collections.Set`; the
  glossary term stays **Set**.
- No feature depends on another feature; only `:app` names another feature's keys.

### Storage

- Three Room tables with autogenerated ids: `trainings`; `training_exercises(id, training_id FK ON DELETE CASCADE,
  exercise_id)`; `exercise_sets(id, training_exercise_id FK ON DELETE CASCADE, reps, weight_grams)`.
- Read through a `@Relation` class; the mapper sorts children by id. A save deletes and reinserts a Training's
  children, so id order is added order; no position column.
- `@Transaction` DAO methods: insert a Training with its exercises and Sets; replace a Training's exercises and Sets
  along with its name. Delete cascades. Restore reinserts the kept rows with their old ids.
- Repository (`TrainingRepository`): `observeTrainings()` and `getTraining(id)` return Trainings with exercises,
  mapping `exercise_id` to an `Exercise` through `ExerciseCatalogue`; writes become `addTraining(name, exercises)`,
  `updateTraining(training)` (replaces its exercises), `deleteTraining(id)`, `restoreTraining(training)`.
- The database **stays at version 1**: the committed schema JSON is overwritten and the user clears the app's data
  once. No migration and no `fallbackToDestructiveMigration`.

### Navigation (ADR 0002)

- One key per Screen: `ExerciseCategoryPickerKey`, `ExercisePickerKey(categoryId)`, `SetEditorKey(exerciseId,
  index: Int? = null, sets)`. `index == null` is add mode (starting at 3 × 0 kg × 12); otherwise the key carries the
  edited Training exercise's position and Sets.
- „Dodaj ćwiczenie”: `TrainingEditorNavigator.openExercisePicker()` → `:app` pushes `ExerciseCategoryPickerKey`.
  Category tap pushes `ExercisePickerKey(categoryId)` within the exercises feature. Exercise tap:
  `ExercisePickerNavigator.onExercisePicked(exerciseId)` → `:app` pushes `SetEditorKey(exerciseId)`. Row tap or
  „Edytuj”: the Training editor's navigator pushes `SetEditorKey(exerciseId, index, sets)`.
- **Results**: `interface NavResult` (a marker, not sealed) in `:core:presentation`. The Set editor declares
  `sealed interface SetEditorResult : NavResult` with `Added(trainingExercise)` and `Replaced(index,
  trainingExercise)`.
- **Sending** is one call for both modes: `SetEditorNavigator.backToTrainingEditor(result)`, implemented in `:app` as
  `AppNavigator.popTo(TrainingEditorKey::class, result)`. It removes both pickers and the Set editor in add mode and
  only the Set editor in edit mode. `back(key)` stays for leaving without a result.
- **Receiving**: `TrainingEditorNavigator.setEditorResults: Flow<SetEditorResult>`, implemented as
  `appNavigator.results.filterIsInstance<SetEditorResult>()`. The Training editor's ViewModel collects it in `init`
  on `viewModelScope`, so the result lands while it sits under the Set editor.
- **`AppNavigator`** gains `results: Flow<NavResult>` backed by `MutableSharedFlow(extraBufferCapacity = 1)` and
  `popTo(keyType, result: NavResult? = null)`, which emits the result before queuing `NavRequest.PopTo` (removes the
  entries above the topmost entry of that key type). `AppNavDisplay` and `HeadlessApp` apply `PopTo`.
- Back from the Set editor in add mode lands on the Exercise picker; from the Exercise picker on the category picker;
  from the category picker on the Training editor.

### Presentation (MVI)

- Each editor's ViewModel keeps its starting values; `hasChanges` compares current state to them by value. A
  `BackHandler` in the Root sends `OnBackClick`; the ViewModel either goes back or shows the discard dialog (dialog
  visibility is State).
- Training editor State gains the list of Training exercises; its actions add open-picker, open-edit(index),
  delete(index), and the ⋮ sheet; results from the Set editor update the list.
- Set editor State: Exercise, mode, Sets, focus; actions for Set count ±, focus(set, field), value ±, confirm, back,
  dismiss/confirm discard. Hold-to-repeat lives in the composable and sends repeated value ± actions.
- Pickers are stateless lists from `ExerciseCatalogue`.
- `android-presentation-mvi` already records that unsaved input is lost on process death; no `SavedStateHandle`.

### Design system (`:core:design-system`)

New components, one ticket each, each with a preview and snapshot, landing right before its first user:

- `AppCountBadge` (28dp, fully rounded, `surfaceContainerHighest`, 13sp medium, `onSurfaceVariant`).
- ~~A subtitle parameter on `AppTopBarWithBack`~~: dropped (ticket 03 reverted); the top bar shows only its title.
- `AppValuePill` (tappable, 40dp high, 12dp corners, 16sp; focused = `secondaryContainer` fill + 2dp `primary`
  border).
- `AppRepeatIconButton` (tonal 40dp icon button, hold to repeat, disabled state).
- `AppCountStepper` (− N +, built on `AppRepeatIconButton`, count 22sp tabular digits).
- `AppDiscardDialog(onDiscard, onDismiss)`: Material 3 `AlertDialog`, title „Odrzucić zmiany?”, no body, „Anuluj” /
  „Odrzuć”; its strings in design-system resources.

Exercise category rows, Exercise rows and Training exercise cards reuse `AppListRow` (extended if needed).

### Visual details (from the prototypes)

- **Training editor**: list starts 24dp below the name field; horizontal padding 16dp, 12dp gap; cards
  `surfaceContainer`, 16dp corners, min height 72dp, padding 12dp vertical, 16dp start / 4dp end; headline bodyLarge
  16sp `onSurface`; supporting bodyMedium 14sp `onSurfaceVariant`; trailing 48dp `more_vert`, content description
  „Więcej opcji”. „Dodaj ćwiczenie”: outlined, `add` icon, full width minus 16dp margins, 40dp high, fully rounded,
  16dp below the last card (24dp below the field when empty), 24dp bottom margin. ⋮ sheet: `edit` „Edytuj”, `delete`
  „Usuń”, same as the Training list.
- **Pickers**: rows `surfaceContainer`, 16dp corners, 12dp gap, 16dp horizontal padding, 8dp above the first row,
  24dp below the last; no chevrons, no dividers. Category row min height 72dp with trailing count badge. Exercise row
  min height 56dp, name only, 16dp padding both sides.
- **Set editor**: Set count container `surfaceContainerLow`, 16dp corners, 8dp top / 16dp bottom / 16dp side
  margins: „Serie” 16sp, then the stepper. Set cards `surfaceContainer`, 16dp corners, 12dp gap, min height 64dp,
  padding 8dp, 12dp start: Set number in a 32dp circle (`surfaceContainerHighest`, 14sp, `onSurfaceVariant`), weight
  pill, reps pill, then on the focused row only two tonal 40dp `remove` / `add` buttons. Pinned filled full-width
  „Dodaj do treningu” (add) / „Gotowe” (edit).
- Prototypes for reference: `prototypes/training-editor.html?variant=D`, `prototypes/pickers.html?variant=E`,
  `prototypes/set-editor.html?variant=E`.

### Strings

- New Polish strings as resources in the module that shows them: „Dodaj ćwiczenie”,
  „Krok 3 z 3: %s”, „Serie”, „%s kg”, „%d powt.”, „%d s”, „Dodaj do treningu”, „Gotowe”, plurals
  seria / serie / serii. Catalogue names are data, not resources.

## Testing Decisions

### What makes a good test

- A test describes behaviour the user can observe, in domain words (Training exercise, Set, Exercise category), and
  never names Compose, Room, ViewModels or modules.
- Tests ship in the same ticket as the behaviour, written test-first, each slice with only its own cases.
  Scaffolding and storage tickets add no tests; existing cases must stay green.

### Seams

No new seams. The existing ones carry everything:

1. **Acceptance cases through one DSL on two channels** (ADR 0001): the device channel (whole app, Compose drivers)
   and the ViewModel channel (headless app, real Koin, real `AppNavigator`, in-memory Room). The real `AppNavigator`
   carries `NavResult`s, so the ViewModel channel tests the real result path; `HeadlessApp` only needs the `PopTo`
   branch and entries for the new Screens.
2. **Previews snapshotted with Paparazzi**: every new design-system component and every new Screen State.

Plus `KoinModulesTest`: every new `parametersOf` type (`ExercisePickerKey`, `SetEditorKey`) gets an
`injectedParameters` entry.

### Acceptance cases

New DSL and drivers for the Exercise category picker, Exercise picker and Set editor on both channels; the Training
editor DSL gains Training exercise steps. Locators: visible text from resources, then content description, then
`testTag` only where neither exists. Cases per slice (exact wording left to the tickets):

- **S1 Category picker**: „Dodaj ćwiczenie” lists the 7 Exercise categories with their Exercise counts; back returns
  to the Training editor.
- **S2 Exercise picker**: a category tap lists its Exercises in catalogue order; back returns to the categories.
- **S3 Set editor, add mode**: picking an Exercise opens it at 3 × 0 kg × 12; back without changes returns to the
  Exercise picker.
- **S4 Add to Training**: „Dodaj do treningu” returns to the Training editor showing the name and „category · 3
  serie”; the same Exercise twice shows two cards in added order.
- **S5 Save with Training exercises**: „Zapisz” stores them and reopening the Training shows them; leaving without
  saving stores nothing.
- **S6 Number of Sets**: + adds a Set copying the last; − removes the last; limits 1 and 10.
- **S7 Reps and weight**: ± changes only the focused value of the focused row; 1.5 kg steps shown „1,5 kg” / „3 kg”;
  limits 0–300 kg, 1–999 reps; timed Exercises show „12 s”; holding + repeats.
- **S8 Edit a Training exercise**: row tap or ⋮ → „Edytuj” opens the Set editor with its Sets; „Gotowe” replaces it
  in place.
- **S9 Delete a Training exercise**: ⋮ → „Usuń” removes it; saving stores the removal.
- **S10 Discard dialog, Training editor**: back after a change asks „Odrzucić zmiany?”; „Odrzuć” leaves storing
  nothing; „Anuluj” stays; an undone change doesn't ask.
- **S11 Discard dialog, Set editor**: the same in both modes; „Odrzuć” in add mode returns to the Exercise picker.
- **S12 Delete and undo with exercises**: deleting a Training and „Cofnij” restore it with its Training exercises
  and Sets.

Channels: every new case runs on both. One exception: „holding + repeats” is `@OnlyOn(Channel.DEVICE)` (the hold
lives in the composable) and asserts at least two steps (≥ 3 kg) after about 1 s so slow emulators don't flake it.
Focus is ViewModel state, so both channels check it. No ViewModel-only case.

### Other tests

- **No unit tests**: weight takes only 201 values, so S7's cases plus mutation testing cover formatting.
- Mutation testing (`android-mutation-testing`) after each ticket that changes production Kotlin, ViewModel channel
  first.

### Gate and prior art

- Gate: `./gradlew check` (ktlint, Detekt, JVM tests including the ViewModel channel, Koin graph, Paparazzi verify)
  plus the managed-device acceptance task. Every ticket leaves the gate green and the app launching.
- Prior art: `TrainingEditorCases` / `TrainingListCases` with their DSLs and both driver sets under `app/src`
  (`sharedTest`, `androidTest`, `test`); design-system screenshot tests such as `AppListRowScreenshotTest`; the
  `android-acceptance-testing`, `android-screenshot-testing` and `android-navigation` skills.

## Out of Scope

- User-defined Exercises or Exercise categories (only the module shape anticipates them).
- Recording a Training session; progress history.
- Timed or distance Sets as their own model; weight units other than kg; typing values.
- Search in the pickers; icons or illustrations for Exercise categories.
- Reordering Training exercises; changing a Training exercise's Exercise.
- Undo for deleting a Training exercise.
- Restoring unsaved changes after process death.
- Room migration of existing data.

## Further Notes

- **Ticket order for `/to-tickets`** (from the slicing ticket): (1) modules and catalogue (`:core:domain`,
  `:feature:exercises:data` with Koin binding, empty wired `:feature:exercises:presentation`); `AppCountBadge`;
  `AppTopBarWithBack` subtitle; S1; S2; `AppValuePill`; S3; S4 (including `NavResult`, `AppNavigator.results`,
  `popTo` with a result and `HeadlessApp`'s `PopTo` branch, tested only by S4's cases); (2) storage of Training
  exercises (tables, `@Relation`, transactional writes, repository methods, `Training.exercises`, schema JSON
  overwritten); S5; `AppRepeatIconButton`; `AppCountStepper`; S6; S7; S8; S9; `AppDiscardDialog`; S10; S11; S12.
- Each slice adds only what its cases need: after S3 nothing in the Set editor can change yet (S4 adds the default);
  S6 brings the count stepper; S7 the focused row and ±. Until S11, back from the Set editor always just goes back.
- After the storage ticket lands, the user clears the app's data on the phone once (DB stays version 1).
- No skill, glossary or ADR changes are expected; `android-navigation` and `android-presentation-mvi` were already
  updated during wayfinding.

## Additions

Kept additions carried from the prototypes and grilling (already approved by the user): row tap opens the Set editor;
~~step subtitles „Krok N z 3”~~ (dropped by the user); Exercise count badge on category rows; ± only on the focused row;
hold to repeat; the „s” label for timed Exercises. No new additions in this spec.
