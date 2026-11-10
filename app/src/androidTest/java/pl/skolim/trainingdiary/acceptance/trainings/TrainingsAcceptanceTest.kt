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
    fun `opening the new training screen shows an empty name ready to type`() =
        trainings {
            openNewTrainingScreen()
            assertNewTrainingScreenIsEmpty()
            assertNameReadyToType()
        }

    @Test
    fun `leaving the new training screen without saving changes nothing`() =
        trainings {
            openNewTrainingScreen()
            enterName("Push day")
            leaveWithoutSaving()
            assertNoTrainings()
        }

    @Test
    fun `saving the new training screen lists the training at the end`() =
        trainings {
            givenTrainings("Push day")
            openNewTrainingScreen()
            enterName("Nogi")
            save()
            assertTrainingsListed("Push day", "Nogi")
        }

    @Test
    fun `saving the new training screen trims the name`() =
        trainings {
            openNewTrainingScreen()
            enterName("  Push day  ")
            save()
            assertTrainingsListed("Push day")
        }

    @Test
    fun `the new training screen cannot be saved with a blank name`() =
        trainings {
            openNewTrainingScreen()
            assertSaveUnavailable()
            enterName("   ")
            assertSaveUnavailable()
            leaveWithoutSaving()
            assertNoTrainings()
        }

    private fun trainings(steps: TrainingsDsl.() -> Unit) =
        compose.use {
            TrainingsDsl(TrainingsUiDriver(compose = this, resources = compose.activity.resources)).steps()
        }
}
