package pl.skolim.trainingdiary.acceptance.trainings

import androidx.compose.ui.test.ExperimentalTestApi
import de.mannodermaus.junit5.compose.createAndroidComposeExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import pl.skolim.trainingdiary.MainActivity
import pl.skolim.trainingdiary.acceptance.driver.InMemoryAppExtension
import pl.skolim.trainingdiary.acceptance.driver.TrainingsUiDriver
import pl.skolim.trainingdiary.acceptance.dsl.TrainingsDsl

@OptIn(ExperimentalTestApi::class)
@ExtendWith(InMemoryAppExtension::class)
class TrainingsAcceptanceTest {
    @JvmField
    @RegisterExtension
    val compose = createAndroidComposeExtension<MainActivity>()

    @Test
    fun `first launch shows that there are no trainings`() =
        trainings {
            assertNoTrainings()
        }

    @Test
    fun `starting to add shows a new training with an empty name`() =
        trainings {
            startAdding()
            assertNewTrainingWithEmptyName()
        }

    @Test
    fun `leaving a new training without saving changes nothing`() =
        trainings {
            startAdding()
            enterName("Push day")
            leaveWithoutSaving()
            assertNoTrainings()
        }

    private fun trainings(steps: TrainingsDsl.() -> Unit) =
        compose.use {
            TrainingsDsl(TrainingsUiDriver(compose = this, resources = compose.activity.resources)).steps()
        }
}
