# Navigation

Type: grilling
Status: resolved
Blocked by: 02, 03, 09

## Question

Which navigation library and patterns: (given the research) Navigation 3 vs type-safe Navigation Compose, route
definitions, feature nav graph, how the form receives add vs edit mode (optional Training id), how the list learns about
a save (re-observe Flow vs result), wiring in `:app`. Which parts of `android-navigation` are accepted or overridden?

Resolve point by point, then rewrite `~/.claude/skills/android-navigation/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

## Comments

- 2026-11-06 (from Dependency injection): the Root passes the Nav3 key to its ViewModel with
  `koinViewModel { parametersOf(key) }`, so each entry must hand its key to the Root. Whether to use
  `koin-compose-navigation3`'s entry DSL is decided here.
- 2026-11-06 (from Presentation: MVI): navigation callbacks live on the Root. The Root intercepts navigation Actions
  and calls `onAddTraining()`, `onEditTraining(id)` and `onBack()`. The editor's `TrainingSaved` / `TrainingMissing`
  Events map to `onBack()`. The list learns about a save by re-observing the Room `Flow`, so no result passing is
  needed. The user wants a reusable ViewModel-driven navigation mechanism later; it is not built now.

## Answer

Settled with the user on 2026-11-07, point by point against `android-navigation`. The skill was rewritten for Nav3
(`~/.claude/skills/android-navigation/SKILL.md`, Android-only, written with `/mattpocock-skills:writing-for-agents`).
The `convention.android.feature` row in `android-module-structure` now names `navigation3-runtime`, and the visibility
line in `android-presentation-mvi` now names the keys and entries function as the public surface.

- **Library**: Navigation 3 1.2.1 and `lifecycle-viewmodel-navigation3` 2.11.0. No Nav2. The user is fine with
  version bumps if any are needed.
- **Keys**: `<Screen>Key`, `@Serializable`, public: `data object TrainingListKey` and
  `data class TrainingEditorKey(val trainingId: Long? = null)`. They live in `TrainingsNavigation.kt` at the
  presentation module's root package, together with the entries function.
- **Feature entries**: a plain public `EntryProviderScope<NavKey>.trainingsEntries(backStack)`. No
  `koin-compose-navigation3`, because it is experimental, untested on Nav3 1.2 and hides the graph inside DI. The user
  may revisit this if it doesn't look right during implementation. Roots stay `internal`.
- **Navigation inside a feature**: entries push and pop the feature's own keys on `backStack`. Cross-feature
  navigation is a callback parameter wired in `:app` (none exist yet). ViewModel-driven navigation stays on the map
  as fog.
- **`:app`**: one `AppNavDisplay`, called from `MainActivity` inside the theme. It holds
  `rememberNavBackStack(TrainingListKey)` (typed `NavKey`) and passes `onBack = removeLastOrNull` and both the
  saveable-state and ViewModel-store entry decorators. System back on the list exits the app.
- **Guards** (required by the user): `NavBackStack<NavKey>.push(key)` adds a key only if it isn't already on top, and
  `pop(key)` removes a key only if it is on top. Both live in `:core:presentation` and are covered by JVM unit tests.
  Every Root `onBack` calls `pop(key)`, including the one triggered by `TrainingSaved` / `TrainingMissing`, so a double
  tap or a save racing a back press can't double-push or empty the stack.
- **Transitions and predictive back**: NavDisplay's defaults. The user doesn't care about predictive back but keeps it
  because it costs nothing.
- **Not built**: deep links, the Result API (the list re-observes the Room `Flow`), adaptive scenes.
- **Dependencies**: `navigation3-runtime` goes into `feature:*:presentation` (through `convention.android.feature`) and
  `:core:presentation`. `navigation3-ui` and `lifecycle-viewmodel-navigation3` go into `:app` only.
- No glossary changes and no ADR: everything is cheap to reverse.
