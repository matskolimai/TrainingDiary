# Navigation

Type: grilling
Status: open
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
