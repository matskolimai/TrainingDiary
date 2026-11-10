package pl.skolim.trainingdiary.acceptance

import androidx.compose.ui.test.ExperimentalTestApi
import de.mannodermaus.junit5.compose.AndroidComposeExtension
import de.mannodermaus.junit5.compose.createAndroidComposeExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import pl.skolim.trainingdiary.MainActivity
import pl.skolim.trainingdiary.acceptance.driver.InMemoryAppExtension
import pl.skolim.trainingdiary.acceptance.dsl.AppDsl

// `@ExtendWith` is inherited and class-level extensions run before field extensions, so Koin starts before the
// Compose extension launches the activity and stops only after the activity has closed.
@OptIn(ExperimentalTestApi::class)
@ExtendWith(InMemoryAppExtension::class)
@Suppress("AbstractClassCanBeConcreteClass") // Abstract so it is only ever extended, never run on its own.
abstract class AcceptanceTest {
    @JvmField
    @RegisterExtension
    val compose: AndroidComposeExtension<MainActivity> = createAndroidComposeExtension<MainActivity>()

    protected fun app(steps: AppDsl.() -> Unit) =
        compose.use {
            AppDsl(compose = this, resources = compose.activity.resources).steps()
        }
}
