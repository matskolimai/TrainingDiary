package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.acceptance.OnlyOn
import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.PlannedTrainingExercise
import pl.skolim.trainingdiary.acceptance.driver.RepsUnit
import kotlin.time.Duration.Companion.seconds

@Suppress("TooManyFunctions") // One screen owns its acceptance cases and steps on both channels.
interface SetEditorCases : AcceptanceCases {
    @Test
    fun `the set editor opens with only the first weight focused`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.assertFocusedWeight(set = 1)
        }

    @Test
    fun `choosing another value keeps exactly that value focused`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.focusReps(set = 2)
            setEditor.assertFocusedReps(set = 2)
            setEditor.increaseValue()
            setEditor.focusWeight(set = 3)
            setEditor.assertFocusedWeight(set = 3)
            setEditor.focusReps(set = 2)
            setEditor.assertFocusedReps(set = 2)
        }

    @Test
    fun `value changes affect only the focused value of the focused set`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.increaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "1,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.focusReps(set = 2)
            setEditor.assertValueControls(set = 2, canDecrease = true, canIncrease = true)
            setEditor.increaseValue()
            setEditor.increaseValue()
            setEditor.decreaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "1,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 13),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.focusWeight(set = 3)
            setEditor.assertValueControls(set = 3, canDecrease = false, canIncrease = true)
            setEditor.increaseValue(times = 10)
            setEditor.decreaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "1,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 13),
                PlannedSet(weightKg = "13,5", reps = 12),
            )
        }

    @Test
    fun `weight steps by one and a half kilograms shown with a decimal comma`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.increaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "1,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.increaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "3", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @Test
    fun `weight stays within 0 and 300 kg with the unavailable direction disabled`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.assertValueControls(set = 1, canDecrease = false, canIncrease = true)
            setEditor.decreaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.increaseValue(times = 200)
            setEditor.assertValueControls(set = 1, canDecrease = true, canIncrease = false)
            setEditor.increaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "300", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.decreaseValue()
            setEditor.assertValueControls(set = 1, canDecrease = true, canIncrease = true)
            setEditor.assertSets(
                PlannedSet(weightKg = "298,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @OnlyOn(Channel.DEVICE)
    @Test
    fun `holding plus on the weight keeps stepping it until release`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.holdIncreaseValue(1.seconds)
            setEditor.assertWeightAtLeast(set = 1, weightKg = "3")
        }

    @OnlyOn(Channel.DEVICE)
    @Test
    fun `holding plus on the weight stops at the limit and a later tap steps once`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.holdIncreaseValue(30.seconds)
            setEditor.assertValueControls(set = 1, canDecrease = true, canIncrease = false)
            setEditor.decreaseValue()
            setEditor.increaseValue()
            setEditor.decreaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "298,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @Test
    fun `reps stay within one and 999 with the unavailable direction disabled`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.focusReps(set = 1)
            setEditor.decreaseValue(times = 11)
            setEditor.assertValueControls(set = 1, canDecrease = false, canIncrease = true)
            setEditor.decreaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 1),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.increaseValue(times = 998)
            setEditor.assertValueControls(set = 1, canDecrease = true, canIncrease = false)
            setEditor.increaseValue()
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 999),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
            setEditor.decreaseValue()
            setEditor.assertValueControls(set = 1, canDecrease = true, canIncrease = true)
            setEditor.assertSets(
                PlannedSet(weightKg = "0", reps = 998),
                PlannedSet(weightKg = "0", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @Test
    fun `a timed exercise shows its reps in seconds`() =
        app {
            given.newTrainingExercise(category = "Brzuch", exercise = "Plank")
            setEditor.assertRepsUnit(RepsUnit.SECONDS)
        }

    @Test
    fun `a non-timed exercise shows its reps as repetitions`() =
        app {
            given.newTrainingExercise(category = "Brzuch", exercise = "Brzuszki")
            setEditor.assertRepsUnit(RepsUnit.REPETITIONS)
        }

    @Test
    fun `removing the focused set keeps its field focused on the new last set`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.focusReps(set = 3)
            setEditor.removeSet()
            setEditor.assertFocusedReps(set = 2)
            setEditor.removeSet()
            setEditor.assertFocusedReps(set = 1)
            setEditor.removeSet()
            setEditor.assertFocusedReps(set = 1)
            setEditor.addSet()
            setEditor.assertFocusedReps(set = 1)
            setEditor.focusWeight(set = 2)
            setEditor.addSet()
            setEditor.removeSet()
            setEditor.assertFocusedWeight(set = 2)
            setEditor.removeSet()
            setEditor.assertFocusedWeight(set = 1)
        }

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
