# Spec-to-ticket slicing order

Type: grilling
Status: resolved
Blocked by: 14

## Question

In what order, and cut how, should the Trainings feature and its foundation be implemented, so that `/to-tickets` can
slice the spec without new questions? Each slice must have one clear purpose, be reviewable in about 15 minutes, and
never mix infrastructure scaffolding with feature behaviour (see the map's Notes).

- The sequence: build-logic and toolchain bump, module skeletons, static analysis and git hook, design system and
  theme, database, repository, Koin wiring, navigation shell, acceptance-test harness (runner, test application,
  managed device), Paparazzi wiring, then list, add, edit, delete and undo.
- Whether the acceptance-test harness lands before the first screen, so every behaviour slice is written test-first.
  Which of the 12 acceptance cases belong to which behaviour slice.
- Where the early-risk checks sit: Paparazzi alpha rendering on AGP 9.5 / Compose 1.12, the managed device on
  Windows, JUnit 6 on device.
- Whether a slice may leave the app in a state that builds but does nothing visible (e.g. an empty `AppNavDisplay`).
- How screenshot reference images and design-system components are spread over the slices.

## Answer

Settled with the user on 2026-11-07. No skill rewritten for slicing (no Android skill covers it); the user may fold
this into skills after a retro once the tickets have been reviewed. `android-compose-components` was edited to drop
`AppMonogram` (see below). No glossary changes, no ADR.

- **Infrastructure just before it's needed**, in this order; each component ticket sits right before the first
  behaviour slice that uses it:
  1. Toolchain bump + build-logic (`convention.*` plugins, version catalog).
  2. Static analysis + git hook.
  3. Module skeletons (empty modules wired; template `ui/theme` and `Example*` tests deleted).
  4. Acceptance-test harness (runner, test application, in-memory override, managed device).
  5. Paparazzi wiring.
  6. Design-system foundation (`AppTheme`, `Spacing`, `AppIcons`, `AppPreview`).
  7. Database + repository.
  8. Koin wiring + navigation shell (`AppNavDisplay`, keys, empty entries).
  9. Behaviour slices S1–S7.
- **Risk first**: the harness and Paparazzi tickets come right after the modules exist. If a risk check fails, the
  ticket stops and reports, and switches only to the fallbacks already approved: JUnit 6 on device → JUnit 4;
  managed device on Windows → `connectedDebugAndroidTest` on a running emulator. Paparazzi has no agreed fallback, so
  a failure goes back to the user.
- **Self-tests**: the harness ticket ships one throwaway smoke test (the app launches under
  `AcceptanceTestApplication` with the in-memory override); case 1's ticket replaces it. The Paparazzi ticket
  snapshots one trivial preview; the design-system foundation's previews replace it. This is the only exception to
  "scaffolding tickets have no tests": it tests the harness, not app behaviour.
- **Nothing visible is fine**: every ticket leaves `./gradlew check` (plus the managed-device task once it exists)
  green and the app launching; it need not show anything new.
- **Behaviour slices**, each written test-first with only its acceptance cases (numbers from Testing strategy):

  | Slice | Cases |
  |---|---|
  | S1 Empty list | 1 |
  | S2 Open editor for adding | 11, 10 |
  | S3 Add a Training (with `isValidTrainingName`) | 2, 3, 4 |
  | S4 Top-bar count | 9 |
  | S5 Edit (⋮ → sheet with Edytuj → editor) | 12, 5 |
  | S6 Delete (Usuń in the sheet) | 6 |
  | S7 Undo | 7, 8 |

- **Components**: one small ticket per `App*` component (component + preview + snapshot) just before its first
  user; components that always travel together (e.g. top bar and FAB) may share one. Behaviour slices add only
  `presentation` previews and snapshots for their own screen states.
- **Monogram dropped** (overrides Prototype: Training list screen, Compose components and design system, Presentation:
  MVI and Testing strategy): the row is name + ⋮ only. No `AppMonogram`, no monogram function, no unit tests for it.
  This feature therefore has no unit tests unless `isValidTrainingName` grows many inputs.
