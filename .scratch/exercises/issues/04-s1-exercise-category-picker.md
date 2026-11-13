# 04: S1 Exercise category picker

**What to build:** the Training editor shows an outlined „Dodaj ćwiczenie” button under the name field. Tapping it opens
the Exercise category picker: title „Dodaj ćwiczenie” (no subtitle; see 03), the 7 Exercise categories in catalogue
order, each a card row with its Exercise count in a badge. Back (top bar or system) returns to the Training editor with
its input intact. Tapping a category does nothing yet. See `../spec.md` (Navigation, Visual details).

**Blocked by:** 01 (Modules and catalogue), 02 (AppCountBadge).

**Status:** done

- [x] „Dodaj ćwiczenie” is always enabled, also in add mode before a name is typed; it sits 24dp below the field.
- [x] `TrainingEditorNavigator.openExercisePicker()`; `:app` pushes `ExerciseCategoryPickerKey`.
- [x] The picker lives in `:feature:exercises:presentation` and reads `ExerciseCatalogue`; rows reuse `AppListRow`
      with `AppCountBadge` trailing.
- [x] New DSL steps and drivers for the picker on both channels; `HeadlessApp` knows the new entry.
- [x] Cases, test-first, on both channels: „Dodaj ćwiczenie” lists the 7 Exercise categories with their Exercise
      counts; back returns to the Training editor.
- [x] Screen preview snapshotted.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Mutation testing

| Mutant | Outcome | Channel |
|---|---|---|
| Training editor: „Dodaj ćwiczenie” action no longer calls `openExercisePicker()` | KILLED | ViewModel |
| Category picker: back action no longer calls `navigator.back(…)` | KILLED | ViewModel |
| Category picker: Exercise count is `lastIndex` instead of `size` | KILLED | ViewModel |
| Category picker: categories listed in reverse order | KILLED | ViewModel |
| `AppNavDisplay`: `exercisesEntries()` not registered | KILLED | device |
