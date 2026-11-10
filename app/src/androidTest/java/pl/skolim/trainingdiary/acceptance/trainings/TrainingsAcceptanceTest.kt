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

    @Test
    fun `opening the edit training screen shows the name of the training`() =
        trainings {
            givenTrainings("Push day", "Nogi")
            openEditTrainingScreen("Nogi")
            assertEditTrainingScreenShows("Nogi")
        }

    @Test
    fun `saving the edit training screen renames the training in its place`() =
        trainings {
            givenTrainings("Push day", "Nogi", "Plecy")
            openEditTrainingScreen("Nogi")
            replaceName("Nogi i pośladki")
            save()
            assertTrainingsListed("Push day", "Nogi i pośladki", "Plecy")
        }

    @Test
    fun `deleting a training removes it from the list and offers to undo`() =
        trainings {
            givenTrainings("Push day", "Nogi", "Plecy")
            delete("Nogi")
            assertTrainingsListed("Push day", "Plecy")
            assertUndoOffered("Nogi")
        }

    @Test
    fun `undoing a delete puts the training back in its place`() =
        trainings {
            givenTrainings("Push day", "Nogi", "Plecy")
            delete("Nogi")
            undoDelete()
            assertTrainingsListed("Push day", "Nogi", "Plecy")
        }

    @Test
    fun `only the latest delete can be undone`() =
        trainings {
            givenTrainings("Push day", "Nogi", "Plecy")
            delete("Nogi")
            delete("Plecy")
            assertUndoOffered("Plecy")
            undoDelete()
            assertTrainingsListed("Push day", "Plecy")
        }

    private fun trainings(steps: TrainingsDsl.() -> Unit) =
        compose.use {
            TrainingsDsl(TrainingsUiDriver(compose = this, resources = compose.activity.resources)).steps()
        }
}
