# 11: S5 Save with Training exercises

**What to build:** „Zapisz” stores the name and all Training exercises with their Sets together, in add and edit
mode; reopening the Training shows them in the same order. Leaving the Training editor without saving stores nothing.
See `../spec.md` (Saving).

**Blocked by:** 09 (S4 Add to Training), 10 (Storage of Training exercises).

**Status:** done

- [x] The Training editor loads a Training's Training exercises in edit mode and passes the list on save.
- [x] Cases, test-first, on both channels: „Zapisz” stores them and reopening the Training shows them; leaving
      without saving stores nothing.
- [x] `./gradlew check` and the device suite pass; mutation testing done.

## Validation

- New and edited Training save/reopen cases failed before their respective save-path fixes, then passed.
- Shared cases cover duplicate Exercises in added order, keeping existing exercises on edit, and leaving add/edit
  mode without storing unsaved changes.
- `./gradlew check pixel9DebugAndroidTest` passed; device suite: 25 cases, 2 expected skips, no failures.
- Device assertions count every exercise summary, including unexpected extra cards after discarding edits.

## Mutation testing

| Mutant | Outcome | Channel |
|--------|---------|---------|
| Omit exercises when loading an existing Training | KILLED | ViewModel |
| Save a new Training with an empty exercise list | KILLED | ViewModel |
| Save an edited Training with an empty exercise list | KILLED | ViewModel |
| Reverse exercises when loading an existing Training | KILLED | ViewModel |

The first run hit a stale Gradle daemon's file lock; stopping the daemon and retrying killed the mutant.
All mutants were restored; `mutant.sh status` reported `clean`.

## Code review

- Standards: no remaining findings. Removed an unrelated unchanged-save sequence from the edit-save case so each
  case checks one behaviour.
- Spec: no findings; both save paths and edit loading pass complete Training exercises and Sets unchanged.
