package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.driver.PlannedSet

interface SetEditorCases : AcceptanceCases {
    @Test
    fun `picking an exercise plans three sets of 12 reps at 0 kg`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @Test
    fun `leaving the set editor returns to the exercise list`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.pick("Uginanie młotkowe")
            setEditor.leave()
            exercisePicker.assertListed(
                "Uginanie ramion z hantlami",
                "Uginanie młotkowe",
                "Uginanie skoncentrowane",
                "Podciąganie podchwytem",
            )
        }
}
