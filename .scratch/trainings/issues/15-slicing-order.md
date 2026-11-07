# Spec-to-ticket slicing order

Type: grilling
Status: open
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
