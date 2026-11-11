package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.acceptance.OnlyOn
import kotlin.time.Duration.Companion.seconds

interface TrainingEditorCases : AcceptanceCases {
    // Focus is set by the Screen, so only the device channel can see it.
    @OnlyOn(Channel.DEVICE)
    @Test
    fun `opening the new training screen shows an empty name ready to type`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.assertEmptyNewTraining()
            trainingEditor.assertNameReadyToType()
        }

    @Test
    fun `leaving the new training screen without saving changes nothing`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.leaveWithoutSaving()
            trainingList.assertEmpty()
        }

    @Test
    fun `saving the new training screen lists the training at the end`() =
        app {
            given.trainings("Push day")
            trainingList.openNewTraining()
            trainingEditor.enterName("Nogi")
            trainingEditor.save()
            trainingList.assertListed("Push day", "Nogi")
        }

    @OnlyOn(Channel.VIEW_MODEL)
    @Test
    fun `saving the new training screen twice quickly adds the training once`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.saveTwiceQuickly()
            trainingList.assertListed("Push day")
        }

    @Test
    fun `saving the new training screen trims the name`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("  Push day  ")
            trainingEditor.save()
            trainingList.assertListed("Push day")
        }

    @Test
    fun `the new training screen cannot be saved with a blank name`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.assertSaveUnavailable()
            trainingEditor.enterName("   ")
            trainingEditor.assertSaveUnavailable()
            trainingEditor.leaveWithoutSaving()
            trainingList.assertEmpty()
        }

    @Test
    fun `opening the edit training screen shows the name of the training`() =
        app {
            given.trainings("Push day", "Nogi")
            trainingList.openForEditing("Nogi")
            trainingEditor.assertEditing("Nogi")
        }

    @OnlyOn(Channel.VIEW_MODEL)
    @Test
    fun `leaving the app for a while keeps the name being typed`() =
        app {
            given.trainings("Nogi")
            trainingList.openForEditing("Nogi")
            trainingEditor.replaceName("Leg day")
            leaveAppFor(10.seconds)
            trainingEditor.assertEditing("Leg day")
        }

    @Test
    fun `saving the edit training screen renames the training in its place`() =
        app {
            given.trainings("Push day", "Nogi", "Plecy")
            trainingList.openForEditing("Nogi")
            trainingEditor.replaceName("Nogi i pośladki")
            trainingEditor.save()
            trainingList.assertListed("Push day", "Nogi i pośladki", "Plecy")
        }
}
