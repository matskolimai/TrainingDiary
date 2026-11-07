Label: wayfinder:map

# Trainings + project foundation

## Destination

Every decision for the first feature — managing **Trainings** (list, add/edit form, delete with undo) — and for the
project foundation it sits on (modules, build-logic, error handling, data/persistence seam, DI, MVI, components/design
system, navigation, testing) is settled, and each Android skill reflects the user's overrides.
`/mattpocock-skills:to-spec` can then write `.scratch/trainings/spec.md` with no new questions.

## Notes

- **Domain**: Android app, Kotlin + Jetpack Compose, single user (the author), never distributed. Minimalism: only the
  features discussed, nothing more. Vocabulary in `GLOSSARY.md` (Training = template; Training session = performed
  instance, not modelled yet).
- **Skills baseline**: Philip Lackner's skills in `~/.claude/skills/android-*` (module-structure, error-handling,
  data-layer, di-koin, presentation-mvi, compose-components, navigation, unit-testing, ui-testing) are the *baseline*,
  not law. Each foundation grilling ticket: (1) present what the skill prescribes, (2) user accepts or overrides each
  point, (3) rewrite the skill file using `/mattpocock-skills:writing-for-agents` so it is well-formed for agents. The
  ticket's answer records the decisions *and* that the skill file was updated. The goal is skills reusable across the
  user's future projects.
- **Code style**: the user is a senior Android dev (10+ yrs) with no written style guide yet — the overrides gathered in
  these tickets *are* the style guide. Ask, don't assume.
- **UI**: dark theme only (no light theme at all). All user-visible strings in **Polish** (string resources); all code,
  identifiers, comments in English.
- **Downstream workflow**: `/to-spec` → `/to-tickets` → `/implement` per ticket. `/implement`'s automatic code review
  stays on, then the user reviews by hand. Therefore `/to-tickets` must produce small slices with one clear purpose
  each, reviewable in ~15 minutes, never mixing infrastructure scaffolding with feature behaviour in one ticket. The
  user may amend skills between implementation tickets.
- **Tracker**: local markdown, see `docs/agents/issue-tracker.md`. No git remote, so research branches stay local.

## Decisions so far

- [Training product rules](issues/01-training-product-rules.md): trimmed name, blank = empty, duplicates allowed,
  creation order, undo restores same identity, missing-on-edit navigates back.
- [Prototype: Training list screen](issues/04-prototype-training-list.md): variant E — centred top bar, plural count,
  separated
  16dp-rounded rows with monogram + ⋮, standard FAB, Edytuj/Usuń sheet, undo snackbar.
- [Library landscape for the AGP 9.4 stack](issues/02-library-landscape.md): Nav3 1.2.1, Room 3.0.3, Koin 4.2.2, KSP
  2.3.13, JUnit 6 via mannodermaus 2.0.2; Kotlin 2.2.10 must be bumped (≈2.4.21); stable Detekt doesn't support AGP 9
  (alpha or defer).
- [Prototype: Training form screen](issues/05-prototype-training-form.md): variant A — small top bar with ← and
  „Nowy trening” / „Edytuj trening”, outlined „Nazwa” field, full-width filled „Zapisz” pinned above the keyboard;
  autofocus in add mode only.
- [Module structure and build-logic](issues/03-module-structure.md): 3 layer modules per feature + 4 core modules; JVM
  domain;
  Kotlin 2.4.21 / AGP 9.5.0 / BOM 2026.09; `convention.*` included-build plugins; ktlint + Detekt 2.0 alpha +
  compose-rules;
  `internal` by default; skill rewritten Android-only.
- [Error handling](issues/06-error-handling.md): missing = `null`; blank name not an error; catch only named
  exceptions. Typed `Result` / exhaustive `toUiText()` are dormant (nothing produces an error yet); skill file
  unchanged.
- [Data layer and backend-ready seam](issues/07-data-layer.md): `TrainingRepository` in domain, `internal`
  `RoomTrainingRepository`; writes return `Unit`, nothing caught; plain `Long` AUTOINCREMENT id; all Room in
  `:core:database` with `AndroidSQLiteDriver`; no ADR; skill rewritten.
- [Dependency injection](issues/08-di-koin.md): Koin DSL only; one public `val` Koin module per Gradle module that
  provides something; `startKoin` in `Application`, no logger; ViewModels take their Nav3 key via `parametersOf`;
  `verifyAll` JVM test in `:app`; skill rewritten.
- [Presentation: MVI](issues/09-presentation-mvi.md): `TrainingList` / `TrainingEditor`; domain `Training` in State (no
  UI model); sheet in State; events named by what happened; Root intercepts nav Actions; keyboard Done saves; no
  `SavedStateHandle`; skill rewritten.
- [Compose components and design system](issues/10-components-design-system.md): every component app-agnostic
  `App*` in `:core:design-system`; dark-only `AppTheme` on M3 defaults; `AppIcons` over icons-extended; `Spacing`
  object; `AppPreview`; feature M3 allowlist; row tap does nothing; skill rewritten.
- [Navigation](issues/11-navigation.md): Nav3 1.2.1; public `<Screen>Key`s + `trainingsEntries(backStack)` (no Koin
  entry DSL); `AppNavDisplay` in `:app`; `push`/`pop(key)` guards in `:core:presentation`; skill rewritten.

## Not yet specified

- **Cross-cutting house style**: conventions no single skill owns (file/package layout inside a module, Kotlin idioms,
  comment policy; module naming and visibility are settled in Module structure and build-logic). May surface as a
  dedicated skill or as edits spread across skills, once the first few foundation tickets show the pattern.
- **ViewModel-driven navigation**: the user wants a reusable way for a ViewModel to navigate, replacing the
  Root-intercept plus Event pattern from Presentation: MVI. It isn't needed for this feature; Navigation deferred it
  (entries push/pop the back stack directly for now), so it is revisited in a later effort.
- **Spec-to-ticket slicing order**: the concrete order of implementation slices (scaffold modules → theme → data → list
  → form → delete/undo …) — derivable once the foundation decisions are in; may need its own grilling before
  `/to-tickets`.

## Out of scope

- Backend / remote sync, auth (only the swappable seam is built).
- Light theme.
- Unsaved-changes dialog on back; delete confirmation.
- Localization beyond Polish.
- CI pipeline.
- Training session and any training content beyond a name (future Wayfinder maps).
- Rewriting `android-error-handling`: this feature produces no typed error (see Data layer and backend-ready seam), so
  the rewrite waits for the first expected failure, likely a backend.
