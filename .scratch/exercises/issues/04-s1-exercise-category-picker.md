# 04: S1 Exercise category picker

**What to build:** the Training editor shows an outlined „Dodaj ćwiczenie” button under the name field. Tapping it
opens the Exercise category picker: title „Dodaj ćwiczenie”, subtitle „Krok 1 z 3: kategoria”, the 7 Exercise
categories in catalogue order, each a card row with its Exercise count in a badge. Back (top bar or system) returns to
the Training editor with its input intact. Tapping a category does nothing yet. See `../spec.md` (Navigation, Visual
details).

**Blocked by:** 01 (Modules and catalogue), 02 (AppCountBadge), 03 (Subtitle on AppTopBarWithBack).

**Status:** ready-for-agent

- [ ] „Dodaj ćwiczenie” is always enabled, also in add mode before a name is typed; it sits 24dp below the field.
- [ ] `TrainingEditorNavigator.openExercisePicker()`; `:app` pushes `ExerciseCategoryPickerKey`.
- [ ] The picker lives in `:feature:exercises:presentation` and reads `ExerciseCatalogue`; rows reuse `AppListRow`
      with `AppCountBadge` trailing.
- [ ] New DSL steps and drivers for the picker on both channels; `HeadlessApp` knows the new entry.
- [ ] Cases, test-first, on both channels: „Dodaj ćwiczenie” lists the 7 Exercise categories with their Exercise
      counts; back returns to the Training editor.
- [ ] Screen preview snapshotted.
- [ ] `./gradlew check` and the device suite pass; mutation testing done.
