# Spec: Acceptance cases on a ViewModel channel

Status: ready-for-agent

Source: the grilling session of 2026-11-10 on `issues/01-discover-viewmodel-channel.md`, the prototype on branch
`prototype/viewmodel-channel` (commit `4a67e9e`), and `docs/adr/0001-acceptance-cases-on-two-channels.md`. The
prototype is a reference, not a base: build every ticket properly from scratch. Where the prototype and this spec
disagree, this spec wins.

## Problem Statement

The acceptance suite runs only on the managed device, through the UI. On 2026-11-10 four of five hand-applied mutants
of the Training editor's ViewModel survived it, because the behaviour they break cannot be staged through the UI: two
taps on save before the Screen redraws, the app left in the background for over 5 s mid-edit, and opening a Training
that no longer exists. Those behaviours are real, yet no test would notice if they broke. Every device run also takes
77-102 s, which makes checking a mutant slow.

## Solution

Every acceptance case runs through the same DSL on two **channels**:

- the **device channel**, as today: the whole app on the managed device, driven through Compose;
- the **ViewModel channel**: the whole app on the JVM without its UI, driven through the ViewModels' actions and
  state, over the real repository and in-memory Room.

A case runs on both channels unless it is tagged for one. The ViewModel channel stages what the UI cannot, so
the double-tap and come-back-later behaviours get cases. It runs all cases in about a second, so the mutation-testing
skill tries mutants there first. The unreachable missing-Training branch is replaced by a crash, which removes the
fourth survivor instead of testing a path no user can reach.

## User Stories

### Developer writing cases

1. As a developer, I want to write a case once and have it run on both channels, so that I describe each behaviour in
   one place.
2. As a developer, I want the existing case bodies to stay as they are, so that moving to two channels does not
   rewrite the suite.
3. As a developer, I want a new case that uses only existing steps to need no driver work, so that adding cases stays
   cheap.
4. As a developer, I want a new step to need one method on each channel's driver, so that I know exactly what to add.
5. As a developer, I want to tag a case that only one channel can stage, so that it runs where it can and is
   skipped, not failed, where it cannot.
6. As a developer, I want a step a channel cannot honour to fail loudly with a message naming the step, so that no case
   passes on a channel without checking anything.
7. As a developer, I want that failure to tell me to tag the case, so that I know how to fix it.
8. As a developer, I want each channel's test class for a Screen to be a single line, so that wiring never hides cases.
9. As a developer, I want the shared cases, DSL and driver interfaces in one place both channels compile, so that
   there is one copy of each.
10. As a developer, I want a case for saving twice quickly, so that the double-tap protection in the Training editor
    is tested.
11. As a developer, I want a case for leaving the app for a while mid-edit, so that keeping typed input when the
    Training editor comes back is tested.
12. As a developer, I want the case that checks the name field is ready to type to run on the device only, so that
    focus, which only the Screen knows, is still checked.

### Developer running the suite

13. As a developer, I want the ViewModel channel to run as ordinary unit tests in `./gradlew check`, so that the gate
    covers it with no new task.
14. As a developer, I want the ViewModel channel to finish in seconds, so that I can run it after every change.
15. As a developer, I want the ViewModel channel to use the real repository and Room, so that it does not test
    fakes.
16. As a developer, I want the ViewModel channel to use the real Koin graph, ViewModels and screen navigators, so that
    their wiring is tested too.
17. As a developer, I want the ViewModel channel to apply navigation with the same guards as the app, so that a double
    push or a racing pop behaves as on device.
18. As a developer, I want each back stack entry to have its own ViewModel, cleared when the entry is popped, so that
    reopening a Screen starts fresh as on device.
19. As a developer, I want the ViewModel channel to collect only the top Screen's state, and only while the app is in
    the foreground, so that state that stops and restarts behaves as on device.
20. As a developer, I want the ViewModel channel to be deterministic, so that a case never fails by timing.
21. As a developer, I want leaving the app for a while to advance virtual time, so that a 10 s absence costs nothing.
22. As a developer, I want the device channel to keep running every case not tagged for the ViewModel channel, so
    that the UI wiring stays proven.

### Developer adding a Screen

23. As a developer, I want adding a Screen to mean one DSL, one driver interface, one driver per channel, one test
    class per channel and one line registering the Screen's ViewModel in the headless app, so that the checklist is
    short and complete.
24. As a developer, I want the acceptance-testing skill to list that checklist, so that I don't forget the headless
    app.

### Developer checking tests with mutants

25. As a developer, I want the mutation-testing skill to run each mutant on the ViewModel channel first, so that most
    mutants are judged in seconds.
26. As a developer, I want only mutants that survive the ViewModel channel to run on the device, so that the device
    only checks what the cases tagged for it can catch.
27. As a developer, I want mutants in Screen or Root composables to go straight to the device, so that no time is
    spent on a channel that never runs that code.

### Developer reading production code

28. As a developer, I want no unreachable branch in the Training editor, so that no surviving mutant points at code no
    user can reach.
29. As a developer, I want opening a Training that does not exist to fail loudly, so that a future path that reaches
    it (sync, a deep link) shows up at once rather than closing the editor silently.

### User of the app (behaviour that must not change)

30. As a user, I want every Training list and Training editor behaviour to work exactly as before, so that the change
    is invisible to me.

## Additions

None.

## Implementation Decisions

### Vocabulary

- **Channel**: one way of running the whole app under the acceptance cases. There are two: device and ViewModel.
- **Driver**: one channel's implementation of one Screen's steps (or of the app-level steps).
- **Case**: one acceptance test, written against the DSL only.
- These words belong to the tests, not the domain: they go in the `android-acceptance-testing` skill, not
  `GLOSSARY.md`.

### Shared cases, DSL and driver interfaces

- They live in one shared test source directory of `:app`, compiled into both its unit-test and its
  instrumented-test source sets. Move it to its own module only if a second app module ever appears.
- Each Screen's cases are an interface extending a common cases interface, so each channel's test class can implement
  them while extending its own base class. JUnit runs the `@Test` default methods of the interface. From the
  prototype:

  ```kotlin
  @ExtendWith(ChannelCondition::class)
  interface AcceptanceCases {
      fun app(steps: AppDsl.() -> Unit)
  }

  interface TrainingEditorCases : AcceptanceCases { /* @Test cases, bodies as today */ }

  class TrainingEditorAcceptanceTest : AcceptanceTest(), TrainingEditorCases          // device
  class TrainingEditorViewModelTest : ViewModelAcceptanceTest(), TrainingEditorCases  // JVM
  ```

- Each Screen's DSL depends on that Screen's driver interface, not on a concrete driver. `AppDsl` takes one driver per
  Screen plus an app driver, and exposes `leaveAppFor(duration)` next to `trainingList`, `trainingEditor` and `given`.
- The driver interfaces carry today's UI-driver methods unchanged, plus `tapSaveTwiceQuickly()` on the Training editor
  driver and `leaveAppFor(duration)` on a new app driver.

### Channel tags

- A channel's base test class names its channel. A case only one channel can stage carries `@OnlyOn(channel)`. A JUnit
  execution condition registered on the common cases interface skips a tagged case on the other channel and reports
  it as skipped.
- A driver step its channel cannot stage throws a dedicated exception naming the step and telling the developer to tag
  the case. It never passes silently.
- Device only: "opening the new training screen shows an empty name ready to type", because focus is set by the
  Screen and is not in the ViewModel state.
- ViewModel only: the two new cases below.

### Device channel

- The existing base test class keeps its Koin and Compose extensions, implements the common cases interface and
  builds `AppDsl` from the UI drivers and a UI app driver.
- The UI drivers implement the driver interfaces. `tapSaveTwiceQuickly` and `leaveAppFor` throw the cannot-stage
  exception.

### ViewModel channel

- **Headless app**: stands in for `AppNavDisplay` and the Activity only. Everything below it is real.
  - It collects `AppNavigator`'s requests and applies them to a real Navigation 3 back stack, using the real push and
    pop guards from `:app`.
  - It keeps one ViewModel store per back stack entry, builds each entry's ViewModel from Koin (with the nav key as
    a parameter where the ViewModel takes one), and clears the store when the entry is popped.
  - It collects the state of the top entry only, and its events where it has any (the Training list's undo
    snackbar), only while the app is in the foreground.
  - It maps each nav key to its ViewModel in one explicit `when`; each new Screen adds one line.
  - `settle()` runs everything due at the current virtual time. `leaveFor(duration)` stops collecting, advances
    virtual time by the duration, then resumes collecting the top entry.
- **Koin and Room**: the channel starts the app's own module list, overriding only the database with an in-memory
  Room built with Room's context-free in-memory builder, the bundled SQLite driver, and the test dispatcher as its
  query coroutine context. No Robolectric, no Android `Context`.
- **Coroutines**: one standard test dispatcher is set as Main and used by Room, so `viewModelScope` and every query
  run on it, deterministically, in virtual time.
- **ViewModel drivers**: each step sends the action the matching tap would send, settles, then reads state:
  - typing appends to the current name, as text input does with the cursor at the end, and replacing sets it;
  - "cannot save" reads `canSave`;
  - "showing for editing" requires edit mode, not loading, and the name;
  - saving and leaving require that the Training editor is no longer on top;
  - "undo offered" reads the last Training list event;
  - rows are found by name to get their id;
  - `tapSaveTwiceQuickly` sends two save actions before settling, as two taps landing before the Screen redraws.
- New unit-test dependencies of `:app`: Room runtime, the JVM artifact of the bundled SQLite driver, and coroutines
  test.

### New cases

- "saving the new training screen twice quickly adds the training once": open new, enter a name, save twice quickly,
  the Training is listed once. Kills M1 (no `isSaving`) and M3 (no `canSave` guard in `save()`).
- "leaving the app for a while keeps the name being typed": given a Training, open it for editing, replace the name,
  leave the app for 10 s, the editor still shows the replaced name. Kills M2 (no `hasLoaded`).

### Production change

- In the Training editor ViewModel's load, the branch that navigates back when the Training is missing is replaced by
  a not-null check on the repository result. Nothing in the app can reach a missing Training today: Trainings are
  local, and deleting happens on the Training list. The `save()` guard stays: it is what enforces the double-tap
  protection.

### Skills (outside this repo, in the user's skills directory)

- `android-acceptance-testing`: define channel, driver and case; one DSL with two drivers; every case on both channels
  unless tagged; the cannot-stage rule; the headless app and what it mirrors; the add-a-Screen checklist, including
  registering the Screen's ViewModel in the headless app.
- `android-mutation-testing`: run each mutant on the ViewModel channel first; run only survivors on the device; send
  mutants in Screen or Root composables straight to the device.

### Repo documents

- `docs/adr/0001-acceptance-cases-on-two-channels.md` records the decision and the rejected alternatives.

## Testing Decisions

### What makes a good test

A case describes behaviour the user can see, in domain language, through the DSL only. It names no driver,
ViewModel, Compose node or database. Whether it runs through the UI or the ViewModels is the channel's business.

### Seams

- **The acceptance DSL** (existing, highest): every case, old and new, is written against it.
- **Driver interfaces** (new): the only new seam, where each channel plugs in.

### Not tested separately

- The headless app has no tests of its own: every case passing on the ViewModel channel covers it, and every case
  failing when its mutant is applied shows that it does not hide behaviour.
- The removal of the missing-Training branch is covered by the existing cases still passing and by the
  mutation-testing skill.

### Prior art

- The existing per-Screen acceptance suite: base class, DSLs, UI drivers and `given`.
- The prototype branch, which ran 12 cases (1 skipped) on the JVM in about 1 s and 11 cases (2 skipped) on the device,
  and killed M1, M2, M3 and the control mutant on the JVM.

### Gate

`./gradlew check` (which now includes the ViewModel channel) and the managed-device acceptance task both pass. Each new
case is shown to kill its mutant by applying the mutant by hand and seeing the case fail.

## Out of Scope

- Running the device suite on the JVM through Robolectric.
- Pitest or any other automatic mutant generator.
- A fake repository or fake navigators.
- A separate Gradle module for the shared cases.
- New user-visible behaviour.
- Cases for Screens other than the Training list and the Training editor.

## Further Notes

- After the shared directory was introduced, the device compile once failed to find a moved DSL class; forcing that
  compile task to rerun fixed it. Expect a stale incremental build after moving the files.
- The device channel's run time does not change: it runs the same 11 cases as before; the two new cases are
  skipped there.
