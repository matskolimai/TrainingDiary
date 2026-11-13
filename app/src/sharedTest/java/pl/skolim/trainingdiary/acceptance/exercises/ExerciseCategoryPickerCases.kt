package pl.skolim.trainingdiary.acceptance.exercises

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases

interface ExerciseCategoryPickerCases : AcceptanceCases {
    @Test
    fun `adding an exercise lists the exercise categories with their exercise counts`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.assertListed(
                "Klatka piersiowa" to 5,
                "Plecy" to 5,
                "Barki" to 5,
                "Biceps" to 4,
                "Triceps" to 4,
                "Nogi" to 6,
                "Brzuch" to 5,
            )
        }

    @Test
    fun `leaving the exercise category screen returns to the training being edited`() =
        app {
            given.trainings("Nogi")
            trainingList.openForEditing("Nogi")
            trainingEditor.replaceName("Leg day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.leave()
            trainingEditor.assertEditing("Leg day")
        }
}
