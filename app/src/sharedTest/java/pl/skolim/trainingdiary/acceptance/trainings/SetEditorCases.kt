package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.PlannedTrainingExercise

interface SetEditorCases : AcceptanceCases {
    @Test
    fun `a training exercise cannot have fewer than one set`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            repeat(2) { setEditor.removeSet() }
            setEditor.assertCount(count = 1, canRemove = false, canAdd = true)
            setEditor.removeSet()
            setEditor.assertSets(PlannedSet(weightKg = "0", reps = 12))
            setEditor.addSet()
            setEditor.assertCount(count = 2, canRemove = true, canAdd = true)
        }

    @Test
    fun `a training exercise cannot have more than ten sets`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            repeat(7) { setEditor.addSet() }
            setEditor.assertCount(count = 10, canRemove = true, canAdd = false)
            setEditor.addSet()
            setEditor.assertCount(count = 10, canRemove = true, canAdd = false)
            setEditor.removeSet()
            setEditor.assertCount(count = 9, canRemove = true, canAdd = true)
        }

    @Test
    fun `removing a set keeps the preceding sets and returns the smaller count to the training`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.removeSet()
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.addToTraining()
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 2),
            )
        }

    @Test
    fun `adding a set copies the last set and returns the changed count to the training`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.addSet()
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.addToTraining()
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 4),
            )
        }

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

    @Test
    fun `adding to the training returns to the new training screen with the exercise and its three sets`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
            )
        }

    @Test
    fun `adding the same exercise twice lists it twice in added order`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.pick("Uginanie młotkowe")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.pick("Uginanie młotkowe")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Uginanie młotkowe", category = "Biceps", sets = 3),
                PlannedTrainingExercise(name = "Uginanie młotkowe", category = "Biceps", sets = 3),
                PlannedTrainingExercise(name = "Wykroki z hantlami", category = "Nogi", sets = 3),
            )
        }
}
