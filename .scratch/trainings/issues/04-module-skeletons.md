# 04: Module skeletons

**What to build:** the project has its final module layout, empty but wired, and the template leftovers are gone.
See the spec's "Toolchain and modules".

**Blocked by:** 03 (Static analysis + git hook)

**Status:** ready-for-agent

- [ ] Modules `:feature:trainings:domain` (JVM), `:feature:trainings:data`, `:feature:trainings:presentation`,
      `:core:presentation`, `:core:design-system`, `:core:database`, each on its convention plugin
- [ ] No `:core:domain` and no `:core:data`
- [ ] Namespace and package equal the module path under `pl.skolim.trainingdiary`
- [ ] Dependencies as agreed: presentation → domain, `:core:presentation`, `:core:design-system`; data → domain,
      `:core:database`; `:app` → every module; features never depend on each other
- [ ] Template `ui/theme` (colours, typography, light scheme) and `Example*` tests are deleted; `MainActivity` still
      shows placeholder content
- [ ] `./gradlew check` is green and the app launches
