# 04: Module skeletons

**What to build:** the project has its final module layout, empty but wired, and the template leftovers are gone.
See the spec's "Toolchain and modules".

**Blocked by:** 03 (Static analysis + git hook)

**Status:** done

- [x] Modules `:feature:trainings:domain` (JVM), `:feature:trainings:data`, `:feature:trainings:presentation`,
      `:core:presentation`, `:core:design-system`, `:core:database`, each on its convention plugin
- [x] No `:core:domain` and no `:core:data`
- [x] Namespace and package equal the module path under `pl.skolim.trainingdiary`
- [x] Dependencies as agreed: presentation → domain, `:core:presentation`, `:core:design-system`; data → domain,
      `:core:database`; `:app` → every module; features never depend on each other
- [x] Template `ui/theme` (colours, typography, light scheme) and `Example*` tests are deleted; `MainActivity` still
      shows placeholder content
- [x] `./gradlew check` is green and the app launches

## Comments

- `:core:design-system` has namespace and package `pl.skolim.trainingdiary.core.designsystem`: a hyphen is not legal
  in a package name, so the hyphen is dropped.
- The modules are build files only; packages appear with their first source files.
- The root `.gitignore` now ignores every `build/` directory, so the new modules need no `.gitignore` of their own.
- `MainActivity` wraps its placeholder in plain `MaterialTheme` until `AppTheme` arrives (07).
- The Detekt Compose adaptations stay although the template composables that needed them are gone; the spec lists
  them and the first real composables need them again.
