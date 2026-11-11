package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases

interface TrainingListCases : AcceptanceCases {
    @Test
    fun `first launch shows that there are no trainings`() =
        app {
            trainingList.assertEmpty()
        }

    @Test
    fun `deleting a training removes it from the list and offers to undo`() =
        app {
            given.trainings("Push day", "Nogi", "Plecy")
            trainingList.delete("Nogi")
            trainingList.assertListed("Push day", "Plecy")
            trainingList.assertUndoOffered("Nogi")
        }

    @Test
    fun `undoing a delete puts the training back in its place`() =
        app {
            given.trainings("Push day", "Nogi", "Plecy")
            trainingList.delete("Nogi")
            trainingList.undoDelete()
            trainingList.assertListed("Push day", "Nogi", "Plecy")
        }

    @Test
    fun `only the latest delete can be undone`() =
        app {
            given.trainings("Push day", "Nogi", "Plecy")
            trainingList.delete("Nogi")
            trainingList.delete("Plecy")
            trainingList.assertUndoOffered("Plecy")
            trainingList.undoDelete()
            trainingList.assertListed("Push day", "Plecy")
        }
}
