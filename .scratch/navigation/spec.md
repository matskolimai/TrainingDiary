# Spec: ViewModel-driven navigation

Status: done

Source: the design conversation of 2026-11-10. `options.html` in this directory is the comparison page from that
conversation; it shows an earlier per-feature variant of the chosen option, so where the page and this spec disagree,
this spec wins.

## Problem Statement

Navigation in this app is split between two places. When I tap Edit on a Training, the Root composable handles the
Action (it navigates) and the ViewModel handles the same Action (it closes the options sheet). To learn what one tap
does I have to read two files. The ViewModel also carries branches that do nothing (`OnAddClick -> Unit`,
`OnBackClick -> Unit`) only because the Root took the Action first, and the Training editor sends Events
(`TrainingSaved`, `TrainingMissing`) whose only purpose is to make the Root navigate back.

I want one place that decides what happens after a tap, a UI layer that is as simple as possible, and a pattern I can
carry unchanged into future apps, including apps where one feature opens another feature's screen.

## Solution

The ViewModel owns all navigation.

- Every screen that navigates declares a small **screen navigator** interface listing exactly where that screen can
  go. Reading the interface tells me every exit from the screen.
- The ViewModel receives its screen navigator and calls it. Every Action is handled in the ViewModel and nowhere else.
- `:app` implements every screen navigator, for navigation inside a feature and across features alike, so there is one
  approach and no second mechanism at feature borders.
- Root composables carry no navigation: no navigation callbacks, no intercepting of Actions, no navigate-back Events.
- The app behaves exactly as before. Nothing the user sees or does changes.
- The skills that describe this architecture (`android-navigation`, `android-presentation-mvi`, `android-di-koin`,
  and `android-module-structure` where it mentions the affected parts) are rewritten to this design, with this app as
  the reference example.

## User Stories

### Developer (the author maintaining this and future apps)

1. As a developer, I want every Action handled only in the ViewModel, so that I read one file to learn what a tap does.
2. As a developer, I want the ViewModel to trigger navigation itself, so that logic such as "close the options sheet,
   then open the editor" lives in one place.
3. As a developer, I want each screen to have its own navigator interface, so that I can see at a glance where it is
   possible to navigate from that screen.
4. As a developer, I want a screen that navigates nowhere to have no navigator, so that no empty interfaces exist.
5. As a developer, I want navigator methods named after intent (`openEditor`), so that ViewModel code reads as what it
   means and never mentions the back stack.
6. As a developer, I want one `back` method per screen navigator, so that explicit back, back after save and back
   after a missing Training all go through the same call.
7. As a developer, I want `back` to take the screen's own key type, so that a ViewModel cannot remove a screen that is
   not its own.
8. As a developer, I want ViewModels free of branches that do nothing, so that every branch of the Action `when`
   carries real behaviour.
9. As a developer, I want Roots without navigation callbacks, so that a Root only collects state, observes UI Events
   and renders the Screen.
10. As a developer, I want Events used only for one-off UI effects such as snackbars, so that a screen whose only
    Events were navigation has no Event type at all.
11. As a developer, I want every screen navigator implemented in `:app`, so that navigation inside a feature and
    between features is written the same way.
12. As a developer, I want a feature to open another feature's screen by adding one method to a screen navigator and
    one line in `:app`, so that features never import each other's keys.
13. As a developer, I want the feature's entries function to only register Roots, so that it needs no back stack and
    no callbacks.
14. As a developer, I want double taps to stay idempotent and a save racing a system back press to pop once, so that
    the existing guarantees of the push and pop guards survive the refactor.
15. As a developer, I want the back stack to survive configuration changes and process death as it does today, so
    that moving navigation into ViewModels costs no restoration behaviour.
16. As a developer, I want a navigation requested while the app is in the background to be applied when the UI is
    visible again, so that it is neither lost nor applied to a stale back stack.
17. As a developer, I want the Koin graph test to fail when a screen navigator has no implementation bound, so that a
    missing binding is caught before the app runs.
18. As a developer, I want the existing acceptance tests to pass unchanged, so that I know the refactor kept
    behaviour.
19. As a developer, I want the skills updated to this design, so that the next screen and the next app are built this
    way without me explaining it again.
20. As a developer, I want the skills to show one navigation approach only, so that an agent never mixes Root-driven
    and ViewModel-driven navigation.

### User of the app (behaviour that must not change)

21. As a user, I want the FAB on the Training list to open an empty Training editor, so that I can add a Training.
22. As a user, I want Edit in the options sheet to close the sheet and open the editor for that Training, so that I
    can rename it.
23. As a user, I want the back arrow in the editor to return to the Training list without saving, so that I can
    abandon a change.
24. As a user, I want saving to return me to the Training list, so that I see the result.
25. As a user, I want the editor to close when its Training no longer exists, so that I am not left on a dead screen.
26. As a user, I want system back to behave as before (leave the editor; exit the app from the list), so that the app
    feels the same.

## Additions

None. This is a refactor; no user-visible behaviour is added.

## Implementation Decisions

### Screen navigators (in `feature:*:presentation`)

- A **screen navigator** is a public interface named `<Screen>Navigator`, declared in the screen's package next to its
  ViewModel. It is public because `:app` implements it; Roots and Screens stay `internal`.
- One interface per screen, never one per feature. A screen with no navigation declares none.
- Opening a screen is an intent-named method whose parameters are the destination's arguments (IDs and scalars), not a
  key. Going back is a single method `back(key)` taking the screen's own key type.
- For this app (shape agreed in the design conversation):

  ```kotlin
  interface TrainingListNavigator {
      fun openEditor(trainingId: Long? = null) // null means add mode
  }

  interface TrainingEditorNavigator {
      fun back(key: TrainingEditorKey)
  }
  ```

- ViewModels and screen navigators never reference the back stack. Keys keep their current role and rules; a
  ViewModel still receives its own key as a constructor parameter.

### ViewModels

- A ViewModel takes its screen navigator as a constructor dependency, injected by Koin.
- Training list: `OnAddClick` opens the editor in add mode; `OnEditClick` dismisses the options sheet and opens the
  editor for that Training.
- Training editor: `OnBackClick`, a successful save and a Training missing on load all call `back` with the
  ViewModel's own key. `isSaving` keeps its current behaviour.
- The Training editor's Event type is deleted, since `TrainingSaved` and `TrainingMissing` were its only Events. The
  Training list keeps `TrainingDeleted` for the undo snackbar.
- No Action maps to `Unit` because something else handles it.

### Roots and entries

- A Root takes its key (if any) and its ViewModel, nothing else. It passes `viewModel::onAction` to the Screen
  directly; it does not inspect Actions.
- A Root observes Events only when the screen has UI Events; the Training editor Root observes none.
- The feature's entries function takes no parameters and only registers one entry per key, each rendering its Root.

### `:app`

- `:app` holds one implementation per screen navigator, named `App<Screen>Navigator`, and binds each to its interface
  in an app-level Koin module.
- The implementations share one **app navigator**: a single app-wide object that accepts push and pop requests for
  keys and queues them. It does not hold the back stack, because the back stack is recreated with the Activity while
  ViewModels and Koin singletons outlive it.
- `AppNavDisplay` still owns the back stack through `rememberNavBackStack`. It observes the app navigator's queue with
  the same lifecycle-aware mechanism Roots use for Events, and applies each request to the back stack through the
  existing push and pop guards.
- The queue is buffered and never drops a request; requests are applied in order while the UI is at least started.
- The app navigator and the push and pop guards live in `:app`: with this design no feature and no core module
  navigates. `:core:presentation` loses the guards and, with them, its Navigation 3 dependency.
- System back stays `NavDisplay`'s own `onBack`; it does not go through the app navigator.

### Skills (outside this repo, in the user's skills directory)

Use the `writing-for-agents` skill when editing them. Each skill must describe this design as the only approach.

- `android-navigation`: replace "feature entries turn Root callbacks into back-stack operations" and "cross-feature
  navigation is a callback parameter" with screen navigators, their `:app` implementations, the app navigator and the
  parameterless entries function. Move the guards to `:app`. Update the wiring example, the dependencies section and
  the new-screen checklist. Show a cross-feature example as one more method on a screen navigator.
- `android-presentation-mvi`: remove "the Root intercepts navigation Actions and forwards every Action", the
  `-> Unit` convention for intercepted Actions, navigate-back Events and the "planned replacement" note. State that
  the ViewModel navigates through its screen navigator, Events are for one-off UI effects only, and the Root passes
  `viewModel::onAction`. Update examples and the checklist.
- `android-di-koin`: add the screen-navigator bindings in `:app` and the navigator as a ViewModel dependency; keep the
  key-parameter rule.
- `android-module-structure`: add screen navigators to the feature's public surface and correct `:core:presentation`'s
  dependencies.

### Repo documents

- No glossary change: screen navigator is an implementation term, and `GLOSSARY.md` holds domain language only.
- No ADR: the decision is recorded in the skills, which are the reusable source of truth.

## Testing Decisions

### What makes a good test

A test states behaviour the user can observe and survives any refactor that keeps that behaviour. This refactor is
the proof: the acceptance cases must pass without edits.

### Seams

- **Acceptance tests** (existing, whole app on a managed device) are the only behavioural seam. They already cover
  adding, editing, leaving the editor without saving, deleting and undo, which exercises every navigation path through
  the real Koin graph, real ViewModels and the real back stack. No test case, DSL or driver changes are expected; a
  needed change signals a behaviour change and must be questioned.
- **Koin graph test** (existing, in `:app`) must cover the new bindings: every ViewModel's screen navigator resolves.
- **Screenshot tests** (existing) are unaffected: Screens keep the `(state, onAction)` shape.

### Not tested separately

No unit tests for ViewModels, screen-navigator implementations, the app navigator or the guards. They are covered
through the acceptance flows, consistent with the earlier decision for this repo.

### Gate

The full existing gate passes: static analysis, screenshot verification, the Koin graph test and the acceptance suite.

## Out of Scope

- Any new screen, feature or cross-feature navigation path. The design supports it; none is built here.
- Deep links, the Navigation 3 result API, adaptive scenes and custom transitions.
- Changes to keys, to how a ViewModel receives its key, or to state restoration.
- Any user-visible change, including copy, layout and animation.
- A shared library or template module for the navigation code; the skills are the reuse vehicle.

## Further Notes

- The user rejected mixing two approaches: do not keep Root callbacks or navigation Events for any case, and do not
  introduce a second mechanism for cross-feature navigation.
- The user accepted the queue-based app navigator without having seen it in code and will judge it in review. Keep it
  small and obvious, and say so in the hand-off if a simpler shape that still survives Activity recreation turns up.
- The skills live outside the repository, so their edits are not part of this repo's commits; report them separately
  in the hand-off.
