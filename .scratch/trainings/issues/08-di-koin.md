# Dependency injection

Type: grilling
Status: resolved
Blocked by: 02, 03

## Question

Which parts of `android-di-koin` does the user accept or override: Koin itself (vs Hilt/manual), one module per layer,
`single`/`factory`/`viewModel` conventions, constructor DSL vs annotations, assembling in `:app`, `koinViewModel()`
usage, passing nav args (Training id) into the ViewModel?

Resolve point by point, then rewrite `~/.claude/skills/android-di-koin/SKILL.md` with
`/mattpocock-skills:writing-for-agents`.

## Answer

Settled with the user on 2026-11-06, point by point against `android-di-koin`. The skill was rewritten to match
(`~/.claude/skills/android-di-koin/SKILL.md`, Android-only, written with `/mattpocock-skills:writing-for-agents`). The
Koin facts come from a source read of Koin 4.2.2 (`New.kt`, `CoreResolverV2.kt`, `koin-test` `verify`) and the
insert-koin.io docs.

- **Koin, DSL only.** No Koin Annotations (the user called them legacy) and no KSP processor for DI.
- **One Koin module per Gradle module that has something to provide**, created lazily. Domain modules get no Koin
  module and no Koin dependency. `coreDatabaseModule` provides the database and each DAO, so `data` modules inject
  DAOs, never the database. The user may rework the granularity if it proves too much across the project.
- **Visibility**: each Koin module is one top-level public `val` in `<Name>Module.kt` at the Gradle module's root
  package. It is that module's public DI surface, and everything it binds stays `internal`.
- **Naming**: `<feature><Layer>Module` and `core<Name>Module` (`trainingsDataModule`, `trainingsPresentationModule`,
  `coreDatabaseModule`).
- **Definitions**: `singleOf` / `viewModelOf` by default, binding with the block form `{ bind<Interface>() }`. Lambdas
  only for builders and accessor calls (the Room builder, the DAO accessor). No `factory` unless a concrete need
  appears.
- **Startup**: the documented standard, `startKoin { androidContext(...); modules(...) }` in
  `TrainingDiaryApp : Application`. **No logger**; the user adds logs when they need them. `KoinStartup` is
  experimental and not faster.
- **Nav arguments**: the user first chose `viewModelOf` with a nullable `trainingId: Long?`. Research showed it
  crashes in add mode: `new` resolves every parameter with a non-null `get()`, so `parametersOf(null)` throws
  `NoDefinitionFoundException`. Decision: a ViewModel that needs nav arguments **takes its Nav3 key** as a
  constructor parameter (`TrainingEditorViewModel(key: TrainingEditorKey, repository)`), passed with
  `koinViewModel { parametersOf(key) }`. Optional arguments live inside the key.
- **Injection point**: `koinViewModel()` only in Root composables, as the last defaulted parameter. ViewModels are
  never passed down. The Koin-Nav3 entry DSL (`koin-compose-navigation3`) is left to Navigation.
- **Graph test**: one `KoinModulesTest` in `:app/src/test` calls `verifyAll(extraTypes = listOf(Context::class),
  injections = injectedParameters(definition<TrainingEditorViewModel>(TrainingEditorKey::class)))`, with
  `@OptIn(KoinExperimentalAPI::class)` at the use site. It runs in `./gradlew check`. Every new `parametersOf` type
  gets an entry. `verifyAll` is JVM-only, ignores nullability and skips lambda bodies.
- **No dispatcher bindings** until something other than Room does blocking work. Tests use `Dispatchers.setMain`.
- **`convention.koin`** adds only `platform(koin-bom)` and `koin-core`. Each module declares its extra artifacts
  explicitly: `koin-androidx-compose` (presentation), `koin-android` (`:app`), `koin-test` (`:app` tests). The
  `convention.koin` row in `android-module-structure` was updated to match.
- No glossary changes and no ADR: everything is cheap to reverse.
