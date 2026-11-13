# 01: Modules and catalogue

**What to build:** the app knows the fixed catalogue of 7 Exercise categories and their Exercises, behind one
interface both features can use, with no visible change yet. Adds `:core:domain` (pure JVM) with `Exercise(id, name,
category, isTimed)`, `ExerciseCategory(id, name)` and `ExerciseCatalogue` (`categories()`, `exercises(categoryId)`,
`exercise(id)`, synchronous); `:feature:exercises:data` with the hardcoded catalogue from `../catalogue.md` bound in
Koin; and an empty `:feature:exercises:presentation` wired into `:app`. No `:feature:exercises:domain`. See
`../spec.md` (Catalogue and modules). Scaffolding: no tests.

**Blocked by:** None (can start immediately).

**Status:** done

- [x] `:core:domain`, `:feature:exercises:data` and `:feature:exercises:presentation` exist, use the convention
      plugins and follow the `android-module-structure` dependency rules (no feature depends on another feature).
- [x] The catalogue holds exactly the categories and Exercises of `../catalogue.md`, in that display order, with
      `Long` ids 1, 2, … for categories and for Exercises, and Polish names as plain strings.
- [x] `isTimed` is true only for Plank and Deska boczna.
- [x] `ExerciseCatalogue` is bound in a Koin module included in the app's modules; `KoinModulesTest` passes.
- [x] `./gradlew check` passes and the app launches unchanged.
