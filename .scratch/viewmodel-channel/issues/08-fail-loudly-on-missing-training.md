# 08: Fail loudly when opening a missing Training

**What to build:** the Training editor no longer has a branch that silently closes the editor when the Training it
opens does not exist; it fails loudly instead. No path in the app reaches a missing Training today (Trainings are
local, and deleting happens on the Training list), so nothing changes for the user, and a future path (sync, a deep
link) would show up at once. This removes M4. The `save()` guard stays. See `../spec.md` (Production change).

**Blocked by:** None (can start immediately).

**Status:** implemented (reviewed; awaiting user-directed merge)

- [x] Loading a Training in edit mode uses a not-null check on the repository result; the navigate-back branch is
      gone.
- [x] Every existing case passes on every channel that exists when this lands; `./gradlew check` passes.
- [x] The `android-mutation-testing` skill has run on the change (this ticket changes production Kotlin).

## Implementation

- `TrainingEditorViewModel.load()` checks the repository result with `checkNotNull` before populating the editor.
- Removed the missing-Training navigation branch. The loading guard and `save()` guard are unchanged.
- Isolated on branch `ticket/08-fail-loudly-on-missing-training`, based on `2f96e05`; no merge to master.

## Validation

- Kotlin compilation and the focused `TrainingEditorScreenshotTest` run passed.
- `./gradlew check :app:pixel9DebugAndroidTest` passed: the full gate and all 11 device acceptance cases.
- After restoring both mutants, a final device run of all seven `TrainingEditorAcceptanceTest` cases passed.
- The device is the only acceptance channel on this branch; ticket 02 is being implemented separately.
- No new test seam: the spec explicitly covers this branch removal through existing cases and mutation testing.

## Mutation testing

Applied `android-mutation-testing` from the user's Claude skills directory after the gate and device suite passed.
A fresh agent inspected production code only and selected two distinct mutations in the changed load statements.
Each ran against `TrainingEditorAcceptanceTest` through `:app:pixel9DebugAndroidTest`.

| Mutant | Change | Outcome | Settlement |
| --- | --- | --- | --- |
| M1 | Omit `isLoading = false` when loading the Training. | KILLED (111 s) | Existing edit cases fail. |
| M2 | Omit populating the loaded Training name. | KILLED (94 s) | Existing edit cases fail. |

The first M1 attempt hit a Java security-file sandbox restriction before tests ran; its rerun produced the result
above. The runner restored the implementation after each attempt, and `mutant.sh status` reports `clean`.
No survivors. Missing-Training fallback mutants were excluded because that path is currently unreachable.

## Code review

Reviewed against the worktree's starting commit, `2f96e05`, with fresh parallel standards and spec reviewers.

- Standards: no findings.
- Spec: no findings.
- Total: zero findings on each axis; no outstanding review issues.
