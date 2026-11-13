# Handoff: returning a result from one Screen to another

Start the fresh session with `/mattpocock-skills:wayfinder .scratch/exercises/map.md` and name the ticket
[Returning a result from one Screen to another](issues/wayfinding/07-screen-results.md). It is a grilling ticket:
first survey the options (use `/mattpocock-skills:research` for the Navigation 3 facts), then put the choice to the
user. The user considers this a crucial operation and wants it handled properly, not just the quickest thing that works.

## The concrete case

Flow (settled in [Architecture for Exercises and Training exercises](issues/wayfinding/05-architecture.md)):

```
Training editor → Exercise category picker → Exercise picker → Set editor
```

- **Add mode**: the Set editor's „Dodaj do treningu” must add a new Training exercise to the Training editor's
  **unsaved changes**, then return with `popTo(TrainingEditorKey)`, removing the Set editor and both pickers.
- **Edit mode**: a Training exercise row in the Training editor opens `SetEditorKey(exerciseId, index, sets)`.
  „Gotowe” must replace the Training exercise at `index` and pop the Set editor.
- Nothing is stored in Room until the Training editor's „Zapisz”, so the result has to reach the Training editor's
  ViewModel state. Re-observing a repository `Flow` (the current house answer in `android-navigation`, "Added only
  when needed") doesn't apply here.
- The pickers live in `feature:exercises` and the Set editor in `feature:trainings`. The result only travels inside
  `feature:trainings` (Set editor → Training editor), but the route crosses features through `:app`.

## Current setup (read the code, it's small)

- `app/.../AppNavDisplay.kt`: `rememberNavBackStack`, `NavDisplay` with `rememberSaveableStateHolderNavEntryDecorator`
  and `rememberViewModelStoreNavEntryDecorator`, so each back-stack entry owns its ViewModel and the Training editor's
  ViewModel stays alive under the Set editor.
- `app/.../AppNavigator.kt`: a Koin singleton that queues `NavRequest`s on a `Channel`. ViewModels never touch the
  back stack; they call their screen navigator, which `:app` implements (`TrainingsNavigators.kt`).
- `~/.claude/skills/android-navigation/SKILL.md`: the house rules, including "Deep links, the Nav3 Result API
  (`ResultEventBus`) ... enter when a feature needs them". This is the first feature that needs one.
- `docs/adr/0001-acceptance-cases-on-two-channels.md`: behaviour is tested on a device channel and a ViewModel
  channel (real Koin, in-memory Room). Whatever you pick has to work on **both**, ViewModel channel included.

## Option already on the table

**Result channel** (proposed in the architecture ticket, not accepted): a Koin `single` in
`feature:trainings:presentation`, e.g. `TrainingExerciseResults` with
`send(TrainingExerciseResult.Added(trainingExercise) / Replaced(index, trainingExercise))`. The Set editor's
ViewModel sends; the Training editor's ViewModel collects. Rejected alongside it: a shared mutable draft singleton
that both editors read and write (needs resetting whenever a Training editor opens).

## Options to investigate

At least the following. Get each fact from primary sources: the official Navigation 3 docs, the `nav3-recipes`
repository, the AndroidX source.

- What Navigation 3 offers officially for results: whether a Result API or `ResultEventBus` exists in the library or
  only in recipes; event-based versus state-based result recipes; which artifact and version they need.
- Delivering the result in the Composable layer (a result bus or CompositionLocal read in the Root, forwarded as an
  Action) versus straight between ViewModels.
- A ViewModel shared between the Training editor and the Set editor (scoped to the editor's entry, or a nested
  navigation scope), and whether Nav3 supports that.
- Callbacks or lambdas passed through the entry provider in `:app`.
- The result channel above, and any other pattern you find in common use.

## Questions to answer for each option

- Where does the result live while the Training editor is off-screen, and what happens on configuration change?
  Process death may lose it, which was decided in ticket 04.
- Can it be lost, delivered twice, or reach the wrong Training editor (only one exists at a time today)?
- Does it fit "ViewModels navigate through screen navigators; only `:app` touches the back stack" and `popTo`?
- Is it testable on the ViewModel channel without a UI?
- How much code is it? The user prefers the simplest code that keeps the features (see auto-memory).
- Is it reusable for the next result-returning Screen, or a one-off?

## When done

Record the decision as the ticket's `## Answer`, set `Status: resolved`, and add a one-line pointer to the map's
Decisions-so-far. Update `android-navigation` (via `/mattpocock-skills:writing-for-agents`) so the "Added only when
needed" paragraph names the chosen mechanism. If the choice is hard to reverse, surprising and a real trade-off,
offer an ADR. After that, [Spec-to-ticket slicing order](issues/wayfinding/06-slicing-order.md) is unblocked.
