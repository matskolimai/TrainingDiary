# 08: Fail loudly when opening a missing Training

**What to build:** the Training editor no longer has a branch that silently closes the editor when the Training it
opens does not exist; it fails loudly instead. No path in the app reaches a missing Training today (Trainings are
local, and deleting happens on the Training list), so nothing changes for the user, and a future path (sync, a deep
link) would show up at once. This removes M4. The `save()` guard stays. See `../spec.md` (Production change).

**Blocked by:** None (can start immediately).

**Status:** ready-for-agent

- [ ] Loading a Training in edit mode uses a not-null check on the repository result; the navigate-back branch is
      gone.
- [ ] Every existing case passes on every channel that exists when this lands; `./gradlew check` passes.
- [ ] The `android-mutation-testing` skill has run on the change (this ticket changes production Kotlin).
