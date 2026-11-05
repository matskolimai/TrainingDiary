# Library landscape for the AGP 9.4 stack

Type: research
Status: resolved
Blocked by: —

## Question

For this project's stack (AGP 9.4.1 with built-in Kotlin, Kotlin 2.2.x, Compose BOM 2026.02, minSdk 35, targetSdk 37), what are the current stable versions and known compatibility constraints of the libraries the skills assume — and where does the ecosystem now diverge from them?

- **Navigation**: Navigation 3 vs type-safe Navigation Compose — stability, recommended choice for new apps, typed args (edit mode by Training id), back handling.
- **Room**: latest stable (Room 2.x vs Room 3), KSP compatibility with AGP 9 built-in Kotlin, Flow support.
- **Koin**: latest stable, Compose/ViewModel artifacts, compatibility with the navigation choice.
- **Testing**: JUnit 5 on Android (unit + instrumented), Turbine, Truth versions; AGP 9 friction.
- **Static analysis**: ktlint and Detekt support for Kotlin 2.2 / AGP 9.
- **build-logic**: convention-plugin gotchas with AGP 9's new DSL / built-in Kotlin.

Findings feed the module-structure, data-layer, DI, navigation and testing tickets.

## Answer

Full findings, with sources: branch `research/library-landscape` (commit 1c171dd), file `.scratch/trainings/research/library-landscape.md`. Local only, never merged. Everything below comes from documentation; nothing was built to test it.

- **Navigation**: Navigation 3 1.2.1 plus `lifecycle-viewmodel-navigation3` 2.11.0. `androidx.navigation` is in maintenance mode. Edit mode: `@Serializable TrainingEditorKey(val trainingId: Long? = null) : NavKey`. Back: `NavDisplay(onBack = { backStack.removeLastOrNull() })`.
- **Room**: Room 3.0.3 (`androidx.room3`, stable since 2026-07). It requires KSP and a SQLite driver; DAOs are `suspend` or return `Flow`. Fallback is Room 2.8.5, which now only gets bug fixes.
- **Kotlin, the biggest issue**: the template's Kotlin 2.2.10 is too old for Koin 4.2.2 (built on Kotlin 2.3.20) and kotlinx-serialization 1.12 (built on 2.4.10). Recommendation: Kotlin 2.4.21 through the build classpath. Kotlin above 2.2.10 has not been officially tested with AGP 9.4.
- **KSP**: 2.3.13. KAPT does not work with built-in Kotlin.
- **Koin**: 4.2.2 with `koin-androidx-compose` and `koin-compose-navigation3` (the Nav3 API is still experimental). Avoid 4.3.0-beta.
- **Testing**: the `de.mannodermaus.android-junit` plugin 2.0.2 (its id changed in 2.0). JUnit 6 (6.1.3) runs on devices with minSdk 35. Turbine 1.2.1, Truth 1.4.5, coroutines-test 1.11.0. Declare `junit-platform-launcher` explicitly. Compose UI tests can run on JUnit 5/6.
- **Static analysis**: ktlint 1.8.0 works fine. Detekt is the problem: stable 1.23.8 predates AGP 9; only 2.0.0-alpha.6 (`dev.detekt`) supports built-in Kotlin. The choice is the alpha or deferring Detekt.
- **build-logic**: never apply `kotlin-android`. Use the untyped `CommonExtension`, `androidComponents.onVariants`, and `KotlinAndroidProjectExtension.compilerOptions`. Now in Android is a working reference.
- **Where the skills diverge**: navigation needs a Nav3 rewrite. Data-layer assumes Room 2. UI-testing says JUnit 4 is required, which is no longer true. UI-testing uses AssertK while unit-testing uses Truth.
- **Open**: Room 3's default Android driver; whether Nav3 fills `SavedStateHandle` from key fields (the safe assumption is to pass the key into the ViewModel constructor).

## Comments

- 2026-11-05: an independent second research run, on branch `research/library-landscape-b` (commit f510e5d), reaches the
  same conclusions. It adds two facts:
  - Compose BOM 2026.02.01 maps to Compose 1.10.4. That is below the `android-ui-testing` skill's 1.11 dispatcher change.
    The latest BOM is 2026.09.00 (Compose 1.12.1), and AGP 9.5.0 is the latest stable AGP.
  - JUnit 6 needs Java 17, so the template's JVM target of 11 has to be raised.
