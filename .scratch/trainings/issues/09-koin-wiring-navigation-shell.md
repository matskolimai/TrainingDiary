# 09: Koin wiring + navigation shell

**What to build:** the app starts its Koin graph and shows an empty Navigation 3 display, and each acceptance test
runs against a fresh in-memory database. Scaffolding: nothing new is visible. See the spec's "Dependency injection",
"Navigation" and "Acceptance tests".

**Blocked by:** 05 (Acceptance-test harness), 07 (Design-system foundation), 08 (Database + repository)

**Status:** done

- [x] `TrainingDiaryApp : Application` calls `startKoin { androidContext(...); modules(appModules) }` with no logger;
      `:app` exposes `appModules`
- [x] Public Koin module `val`s: `coreDatabaseModule` (the database with `AndroidSQLiteDriver`, and the DAO),
      `trainingsDataModule` (binds the repository) and `trainingsPresentationModule` (empty until a ViewModel exists)
- [x] Public keys `TrainingListKey` and `TrainingEditorKey(trainingId: Long? = null)`, and an empty public
      `trainingsEntries(backStack)`
- [x] `AppNavDisplay` in `:app`, called from `MainActivity` inside `AppTheme`, with
      `rememberNavBackStack(TrainingListKey)`, `onBack = removeLastOrNull` and both entry decorators
- [x] `push(key)` / `pop(key)` back-stack guards in `:core:presentation` (no unit tests, per the spec)
- [x] `KoinModulesTest` in `:app` JVM tests runs `verifyAll` with `Context` as an extra type
- [x] The harness's JUnit extension starts Koin with `appModules` + an in-memory database override before each test
      and stops it after; `AcceptanceTestApplication` doesn't start Koin itself; the smoke test stays green
- [x] `./gradlew check` and the managed-device test task are green; the app launches
