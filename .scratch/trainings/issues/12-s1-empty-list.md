# 12: S1 Empty list

**What to build:** on first launch the user sees the „Treningi” screen with a FAB and the empty state „Brak
treningów” / „Dodaj pierwszy trening przyciskiem +”. The empty state appears only once the data has loaded. See the
spec's "Presentation", "Visual details" and user stories 1, 3, 8–11 and 13.

**Blocked by:** 09 (Koin wiring + navigation shell), 10 (AppScaffold + AppTopBar + AppFab), 11 (AppEmptyState)

**Status:** done

- [x] Written test-first: acceptance case 1 "First launch shows „Brak treningów”", in domain words, through new
      `TrainingsDsl` and `TrainingsUiDriver`; it replaces the smoke test
- [x] `TrainingList` Root and Screen, wired into `trainingsEntries` for `TrainingListKey`; the Root gets its
      ViewModel with `koinViewModel()`
- [x] `TrainingListViewModel`: nothing in `init`; state from `observeTrainings()` +
      `stateIn(WhileSubscribed(5_000))`; `isLoading = true` until the first emission (top bar and FAB only, no
      spinner)
- [x] The FAB has the content description „Dodaj trening” and does nothing yet
- [x] Polish string resources named as in the spec; no literal Polish in tests
- [x] Previews `TrainingListPreviewLoading` and `TrainingListPreviewEmpty`, snapshotted (Paparazzi applied to the
      presentation module); reference images committed
- [x] Detekt Compose adaptations added only if a composable needs them
- [x] `./gradlew check` and the managed-device test task are green
