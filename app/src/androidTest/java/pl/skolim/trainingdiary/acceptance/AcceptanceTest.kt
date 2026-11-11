package pl.skolim.trainingdiary.acceptance

import androidx.compose.ui.test.ExperimentalTestApi
import de.mannodermaus.junit5.compose.AndroidComposeExtension
import de.mannodermaus.junit5.compose.createAndroidComposeExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import pl.skolim.trainingdiary.MainActivity
import pl.skolim.trainingdiary.acceptance.driver.AppUiDriver
import pl.skolim.trainingdiary.acceptance.driver.InMemoryAppExtension
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorUiDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListUiDriver
import pl.skolim.trainingdiary.acceptance.dsl.AppDsl

// `@ExtendWith` is inherited and class-level extensions run before field extensions, so Koin starts before the
// Compose extension launches the activity and stops only after the activity has closed.
@OptIn(ExperimentalTestApi::class)
@ExtendWith(InMemoryAppExtension::class)
@Suppress("AbstractClassCanBeConcreteClass") // Abstract so it is only ever extended, never run on its own.
abstract class AcceptanceTest : AcceptanceCases {
    @JvmField
    @RegisterExtension
    val compose: AndroidComposeExtension<MainActivity> = createAndroidComposeExtension<MainActivity>()

    override val channel = Channel.DEVICE

    override fun app(steps: AppDsl.() -> Unit) =
        compose.use {
            val resources = compose.activity.resources
            AppDsl(
                appDriver = AppUiDriver(),
                trainingListDriver = TrainingListUiDriver(compose = this, resources = resources),
                trainingEditorDriver = TrainingEditorUiDriver(compose = this, resources = resources),
            ).steps()
        }
}
