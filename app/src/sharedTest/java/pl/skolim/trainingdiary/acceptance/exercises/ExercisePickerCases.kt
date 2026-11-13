package pl.skolim.trainingdiary.acceptance.exercises

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases

interface ExercisePickerCases : AcceptanceCases {
    @Test
    fun `picking an exercise category lists its exercises in catalogue order`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.assertListed(
                "Podciąganie na drążku",
                "Wiosłowanie hantlem w opadzie",
                "Wiosłowanie hantlami oburącz",
                "Martwy ciąg z hantlami",
                "Pullover z hantlem",
            )
        }

    @Test
    fun `leaving the exercise list returns to the exercise categories`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.leave()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.assertListed(
                "Uginanie ramion z hantlami",
                "Uginanie młotkowe",
                "Uginanie skoncentrowane",
                "Podciąganie podchwytem",
            )
        }
}
