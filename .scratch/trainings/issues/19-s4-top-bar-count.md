# 19: S4 Top-bar count

**What to build:** above the list the user sees how many Trainings they have, in correct Polish („1 trening”,
„2 treningi”, „5 treningów”); the count is hidden when the list is empty. See the spec's "Visual details" and user
stories 4 and 5.

**Blocked by:** 18 (S3 Add a Training)

**Status:** wontfix

- [x] Written test-first: acceptance case 9 "The top-bar count follows the number of Trainings, with Polish plural
      forms (1 / 2 / 5)"
- [x] A `<plurals>` resource with the Polish forms, chosen with `pluralStringResource` in the composable (State holds
      no strings)
- [x] Label in titleSmall, `primary` colour, padding 16dp top / 32dp start; hidden when the list is empty
- [x] List previews and snapshots updated; reference images committed
- [x] `./gradlew check` and the managed-device test task are green

## Comments

- Built in 5f2eabb, then reverted after review: the user finds the count not useful and does not want it shown.
  The spec's user stories 4–5 and acceptance case 9 are dropped.
