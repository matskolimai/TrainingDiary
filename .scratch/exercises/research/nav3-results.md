# Returning a result to a Screen below on the back stack (Navigation 3)

Researched 2026-11-13 against primary sources only. Each claim carries its source. Statements marked
**(inference)** are my reading of the source code, not documented behaviour.

## Source shorthand

`AX` = <https://github.com/androidx/androidx/blob/androidx-main> (GitHub mirror of AOSP `frameworks/support`).
`NAV3RES` = `navigation3/navigation3-runtime/src/commonMain/kotlin/androidx/navigation3/runtime/result`.

## Versions at the time of writing

- `androidx.navigation3:navigation3-*`: stable **1.2.1** (2026-11-04), alpha **1.3.0-alpha04** (2026-11-04).
  Source: <https://developer.android.com/jetpack/androidx/releases/navigation3>
- `androidx.lifecycle:lifecycle-*` (incl. `lifecycle-viewmodel-navigation3`): stable **2.11.0**, alpha
  **2.12.0-alpha05**. Source: <https://developer.android.com/jetpack/androidx/releases/lifecycle>
- nav3-recipes builds against `navigation3 = "1.3.0-SNAPSHOT"` and `lifecycleViewmodel = "2.11.0"`.
  Source: <https://github.com/android/nav3-recipes/blob/main/gradle/libs.versions.toml>

## 1. The library ships a results API (since 1.2.0)

**Library fact.** `ResultEventBus` is part of `androidx.navigation3:navigation3-runtime`, package
`androidx.navigation3.runtime.result`, in `commonMain` (so KMP). It is not recipe-only.

- Added in 1.2.0-alpha02 (2026-04-22): "Added a `ResultEventBus` API to allow for passing results between
  NavEntries with the `LocalResultEventBus` provided by the new `ResultEventBusNavEntryDecorator`."
- `rememberResultEventBus` (hoisting) added in 1.2.0-alpha05 (2026-07-01).
- `ResultEffect` uses `rememberUpdatedState` for `onResult` since 1.2.0 (b/531709234).
- Stable in **1.2.0** (2026-09-23) under the heading "Result API".
  Source: <https://developer.android.com/jetpack/androidx/releases/navigation3>
- Guide: "Starting in Navigation 3 1.2.0, you can return results from destinations using the `ResultEventBus`
  API." Source: <https://developer.android.com/guide/navigation/navigation-3/return-results>

Public API (from `navigation3-runtime/api/current.txt`,
`AX/navigation3/navigation3-runtime/api/current.txt`):

- `class ResultEventBus` — `sendResult<T>(result)`, `sendResult(resultKey, result)`,
  `@Composable conflateAsState<T>(defaultValue)`, `@Composable conflateAsState(resultKey, defaultValue)`,
  `removeResult<T>()`, `removeResult(resultKey)`.
- `@Composable ResultEffect<T>(resultEventBus = LocalResultEventBus.current, onResult: suspend (T) -> Unit)`
  and `ResultEffect(resultKey, resultEventBus, onResult)`.
- `object LocalResultEventBus` (`current` throws "No ResultEventBus has been provided" if absent).
- `class ResultEventBusNavEntryDecorator<T>(bus = ResultEventBus())`,
  `rememberResultEventBusNavEntryDecorator(resultEventBus = rememberResultEventBus())`, `rememberResultEventBus()`.

### Implementation (load-bearing details)

Source: `AX/NAV3RES/ResultEventBus.kt`

```kotlin
/**
 * An EventBus for passing results between multiple sets of screens.
 * ...
 * These results are not saved across configuration changes or process death.
 */
public class ResultEventBus {
    internal val channelMap: SnapshotStateMap<String, Channel<Any?>> = mutableStateMapOf()

    @Composable
    public fun <T> conflateAsState(resultKey: String, defaultValue: T): State<T> {
        val flow = remember(resultKey) { getResultFlow(resultKey) }
        return flow.collectAsState(defaultValue) as State<T>
    }

    internal fun getResultFlow(resultKey: String): Flow<Any?> {
        if (!channelMap.contains(resultKey)) {
            channelMap[resultKey] =
                Channel(capacity = BUFFERED, onBufferOverflow = BufferOverflow.SUSPEND)
        }
        return channelMap[resultKey]!!.receiveAsFlow()
    }

    public fun <T> sendResult(resultKey: String, result: T) {
        if (!channelMap.contains(resultKey)) {
            channelMap[resultKey] =
                Channel(capacity = BUFFERED, onBufferOverflow = BufferOverflow.SUSPEND)
        }
        channelMap[resultKey]?.trySend(result)
    }

    public fun removeResult(resultKey: String) {
        channelMap.remove(resultKey)?.close()
    }
}
```

Type-derived key = `T::class.toString()`.

`ResultEffect` (`AX/NAV3RES/ResultEffect.kt`):

```kotlin
val currentOnResult by rememberUpdatedState(onResult)
LaunchedEffect(resultKey, resultEventBus.channelMap[resultKey]) {
    resultEventBus.getResultFlow(resultKey).collect { result ->
        currentOnResult.invoke(result as T)
    }
}
```

Decorator (`AX/NAV3RES/ResultEventBusNavEntryDecorator.kt`):
one bus shared by every entry; `decorate` just wraps `entry.Content()` in
`CompositionLocalProvider(LocalResultEventBus provides bus)`; `onPop = {}`. The default bus lives in a plain
`remember { ResultEventBus() }`.

### Behaviour that follows from the code (inference)

- **Receiver not composed:** results sit in the per-key `Channel` (capacity `BUFFERED`, 64 by default) until a
  collector appears, so a receiver lower on the back stack gets the result once it re-enters composition. If the
  buffer is full, `trySend` fails silently and the result is dropped.
- **Single consumer:** `receiveAsFlow()` delivers each element to one collector only. Two composed receivers of the
  same key split the results; there is no broadcast and no double delivery of one item.
- **Stale results:** an unconsumed result stays buffered and is delivered to whichever receiver of that key composes
  next, even a different screen later. This is what `removeResult` guards against.
- **`conflateAsState` across forward navigation:** the value is held by `collectAsState` inside the receiver's
  composition (plain `remember`), so it should reset to `defaultValue` when the receiver leaves composition
  (e.g. user navigates forward again) and comes back. The event recipe stores the result in a ViewModel instead.
  Not stated in docs; not verified by running.
- **Bus is not scoped per entry:** keys are global across all entries using the decorator.

### Official guide: documented caveats and advice

Source: <https://developer.android.com/guide/navigation/navigation-3/return-results>

- Caution: "`ResultEventBus` is an in-memory communication bus. Results aren't saved across process death or
  activity recreation automatically. If a result must survive process death, persist the value using
  `rememberSaveable`, a `SavedStateHandle` inside a scoped `ViewModel`, or by embedding the data in a `NavKey`."
- Keys: explicit (`resultKey = "pickup_address"`) for common types or several results of one type; type-derived
  otherwise. "Type-derived keys don't receive events sent with explicit keys ... Senders and receivers must both
  use explicit keys or both use type-derived keys."
- `ResultEffect` is **queued** ("Processes all results emitted for the key in order"), for one-time events and
  "forwarding to a `ViewModel`"; body is a coroutine, so suspend calls are allowed.
- `conflateAsState` is **conflated** ("Drops intermediate results and retains only the latest"). Tip: "Use
  `conflateAsState` for lightweight, optional UI modifiers ... For primary screen data backed by repositories, use
  `ResultEffect` to update a `ViewModel`."
- Lifecycle: sender sends and pops; receiver enters composition and `ResultEffect` starts listening; processes
  results in order; stops listening when the receiver leaves composition.
- "When a destination consumes a one-time result, clear it from the event bus using `removeResult`. This prevents
  the bus from re-delivering past events to new observers when destinations re-enter composition." (Given the
  `receiveAsFlow` consumption above, I could not see from the source how an already-received item would be
  re-delivered; the practical effect of `removeResult` is closing the channel and dropping still-buffered items.)
- Hoisting: create with `rememberResultEventBus()` and pass to `rememberResultEventBusNavEntryDecorator(bus)` to
  use the bus from DI graphs or app scaffolding outside the destinations.

## 2. The nav3-recipes "results" recipes

Index: <https://github.com/android/nav3-recipes#returning-results>. All three now use the **library**
`ResultEventBus`; none defines its own bus class any more. Setup in each:

```kotlin
entryDecorators = listOf(
    rememberSaveableStateHolderNavEntryDecorator(),
    rememberResultEventBusNavEntryDecorator()
),
```

### 2a. Event-based

<https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/results/event>

```kotlin
entry<Home> {
    val viewModel = viewModel<HomeViewModel>(key = Home.toString())
    ResultEffect<Person> { person ->
        viewModel.person = person
    }
    HomeScreen(person = viewModel.person, onNext = { backStack.add(PersonDetailsForm()) })
}
entry<PersonDetailsForm> {
    val resultBus = LocalResultEventBus.current
    PersonDetailsScreen(
        onSubmit = { person ->
            resultBus.sendResult<Person>(result = person)
            backStack.removeLastOrNull()
        }
    )
}
```

`HomeViewModel` is `class HomeViewModel : ViewModel() { var person by mutableStateOf<Person?>(null) }`. The recipe
does not add the ViewModel decorator, so this ViewModel is Activity-scoped, keyed by `Home.toString()`.
README caveat: "This approach is useful for results that are transient and should be handled as one-time events."

### 2b. State-based

<https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/results/state>

```kotlin
entry<Home> {
    val person = LocalResultEventBus.current.conflateAsState<Person?>(null).value
    HomeScreen(person = person, onNext = { backStack.add(PersonDetailsForm()) })
}
// sender identical to 2a: resultBus.sendResult(result = person); backStack.removeLastOrNull()
```

README caveat: "suitable when only the latest result is required. The result state does not survive configuration
change or process death."

### 2c. Serializable state-based

<https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/results/serializable>

A recipe-only extension `ResultEventBus.conflateAsSerializableState<T>(defaultValue, vararg inputs, ...)` in
`Extensions.kt`: stores the latest result in `rememberSerializable { mutableStateOf(NullableWrapper(default)) }`
and fills it from `ResultEffect`. Receiver:
`LocalResultEventBus.current.conflateAsSerializableState<Person?>(null)`. Result types must be `@Serializable`.
README: "suitable when the result needs to survive configuration changes and process death, whereas the standard
`conflateAsState` does not." Code warning: do not use the reified overload for generic types (`List`, `Map`)
because "JVM type erasure will cause key collisions"; use an explicit `resultKey`.

Note: only the *received value* is saved. A result sent but not yet collected when the process dies is still lost,
since the channel is in memory (inference).

## 3. Shared ViewModel scoping

**Library fact.** `androidx.lifecycle:lifecycle-viewmodel-navigation3` ships only
`ViewModelStoreNavEntryDecorator` / `rememberViewModelStoreNavEntryDecorator(...)`: one `ViewModelStoreOwner` per
entry, keyed by `entry.contentKey`, cleared in `onPop`. There is **no** library API for scoping a ViewModel to
another entry or to a group of entries.
Sources: `AX/lifecycle/lifecycle-viewmodel-navigation3/api/current.txt`,
`AX/lifecycle/lifecycle-viewmodel-navigation3/src/commonMain/kotlin/androidx/lifecycle/viewmodel/navigation3/`
`ViewModelStoreNavEntryDecorator.kt`,
<https://developer.android.com/guide/navigation/navigation-3/save-state>

The building blocks a custom decorator uses are in `lifecycle-viewmodel` 2.11.0: "Introduction of
`ViewModelStoreProvider`, `rememberViewModelStoreProvider`, and `rememberViewModelStoreOwner` to tie ViewModels to
specific UI scopes". 2.11.0-alpha03 deprecated `removeViewModelStoreOnPop` ("All decorator state must clear at the
same time on pop"). Source: <https://developer.android.com/jetpack/androidx/releases/lifecycle>

**Recipe fact.** "Shared ViewModel" recipe, a custom `SharedViewModelStoreNavEntryDecorator` that reads a parent
content key from entry metadata and provides the parent's owner under a second CompositionLocal:
<https://github.com/android/nav3-recipes/tree/main/app/src/main/java/com/example/nav3recipes/sharedviewmodel>
(also <https://developer.android.com/guide/navigation/navigation-3/recipes/sharedviewmodel>).

```kotlin
// decorate =
val localOwner = rememberViewModelStoreOwner(entry.contentKey, viewModelStoreProvider,
    savedStateRegistryOwner = LocalSavedStateRegistryOwner.current)
val values = mutableListOf<ProvidedValue<*>>(LocalViewModelStoreOwner provides localOwner)
val parentContentKey = entry.metadata[ParentKey]
if (parentContentKey != null) {
    val parentOwner = rememberViewModelStoreOwner(parentContentKey, viewModelStoreProvider,
        savedStateRegistryOwner = LocalSavedStateRegistryOwner.current)
    values.add(LocalSharedViewModelStoreOwner provides parentOwner)
}
// companion: fun parent(key: Any) = metadata { put(ParentKey, key) }; object ParentKey : NavMetadataKey<Any>

// usage
entry<ParentScreen>(clazzContentKey = { key -> key.toContentKey() }) {
    val vm = viewModel<CounterViewModel>()
}
entry<ChildScreen>(metadata = SharedViewModelStoreNavEntryDecorator.parent(ParentScreen.toContentKey())) {
    val parentViewModel =
        viewModel<CounterViewModel>(viewModelStoreOwner = LocalSharedViewModelStoreOwner.current)
}
```

The parent entry must set a matching `contentKey`. It replaces (not stacks with) the library ViewModel decorator.
Scoping to a "group of entries" isn't covered by any recipe; the parent-key pattern covers child-to-parent only.
No "nested navigation" recipe exists in the repo tree (checked 2026-11-13).

Shared-ViewModel as a result channel: a child can write into the parent's ViewModel directly, which survives config
change (ViewModel) and, with `SavedStateHandle`, process death. The recipe demonstrates shared state, not results
specifically.

## 4. Official guidance on callbacks/lambdas and ViewModels

- Return-results guide: "To keep screen composables reusable and testable, don't access `LocalResultEventBus`
  directly inside your screen UI. Instead, expose callback lambdas from your screen. In your `entryProvider`, handle
  the callback by sending the result using `LocalResultEventBus.current` and navigating back."
  Source: <https://developer.android.com/guide/navigation/navigation-3/return-results>
- Receiving side: the guide's examples call `viewModel.onPickupAddressSelected(address)` from `ResultEffect`, and
  recommend `ResultEffect` -> `ViewModel` for primary screen data.
- The basics guide shows lambdas that mutate the back stack inside entry content
  (`Button(onClick = { backStack.add(Product("123")) })`) but says nothing about results.
  Source: <https://developer.android.com/guide/navigation/navigation-3/basics>
- I found no official page that recommends returning a result by passing a lambda that edits the receiver's
  `NavKey` / state in the back stack. The guide's caution only lists "embedding the data in a `NavKey`" as one way
  to survive process death.
- No official guidance on sending a result from inside a ViewModel. Hoisting the bus is described as allowing it to
  be passed "into non-composable components or dependency injection graphs", but no example is given.

## 5. Release notes on a results API

- 1.2.0-alpha02 (2026-04-22): `ResultEventBus`, `LocalResultEventBus`, `ResultEventBusNavEntryDecorator`,
  `conflateAsState`, `ResultEffect` added.
- 1.2.0-alpha05 (2026-07-01): `rememberResultEventBus`.
- 1.2.0 (2026-09-23): stable, "Result API" headline; `ResultEffect` `rememberUpdatedState` fix.
- 1.2.1 and 1.3.0-alpha01..04: no result-API changes.
  Source: <https://developer.android.com/jetpack/androidx/releases/navigation3>

## Not verified

- Runtime behaviour items marked (inference) were read from source, not exercised.
- The guide's "re-delivering past events to new observers" wording did not match my reading of `receiveAsFlow`.
- Whether `ResultEffect` collects while the receiver is in a pane next to the sender (list-detail scene) was not
  checked beyond the existing dialog test names (`testResultEventBusWithDialog`, `testConflateAsStateWithDialog`).
