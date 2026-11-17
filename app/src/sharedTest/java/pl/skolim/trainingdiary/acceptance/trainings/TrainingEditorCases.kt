package pl.skolim.trainingdiary.acceptance.trainings

import org.junit.jupiter.api.Test
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.acceptance.OnlyOn
import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.PlannedTrainingExercise
import kotlin.time.Duration.Companion.seconds

// One cases interface per Screen keeps both channels on the same acceptance contract.
@Suppress("TooManyFunctions")
interface TrainingEditorCases : AcceptanceCases {
    @Test
    fun `leaving a new training with exercises without saving stores nothing`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.leaveWithoutSaving()
            trainingList.assertEmpty()
        }

    @Test
    fun `leaving an edited training without saving keeps its stored name exercises and sets`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.save()
            trainingList.openForEditing("Push day")
            trainingEditor.replaceName("Leg day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.leaveWithoutSaving()
            trainingList.assertListed("Push day")
            trainingList.openForEditing("Push day")
            trainingEditor.assertEditing("Push day")
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
            )
        }

    @Test
    fun `saving an edited training keeps existing and added exercises with their sets`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.save()
            trainingList.openForEditing("Push day")
            trainingEditor.replaceName("Leg day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.save()
            trainingList.assertListed("Leg day")
            trainingList.openForEditing("Leg day")
            trainingEditor.assertEditing("Leg day")
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
                PlannedTrainingExercise(name = "Wykroki z hantlami", category = "Nogi", sets = 3),
            )
        }

    @Test
    fun `saving a new training keeps its exercises and sets in added order when reopened`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.save()
            trainingList.assertListed("Push day")
            trainingList.openForEditing("Push day")
            trainingEditor.assertEditing("Push day")
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
                PlannedTrainingExercise(name = "Wykroki z hantlami", category = "Nogi", sets = 3),
            )
        }

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

    @Test
    fun `editing a training exercise from its options opens the set editor with its sets`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.removeSet()
            setEditor.increaseValue()
            setEditor.addToTraining()
            trainingEditor.editExerciseFromOptions(position = 1)
            setEditor.assertEditing("Pullover z hantlem")
            setEditor.assertSets(
                PlannedSet(weightKg = "1,5", reps = 12),
                PlannedSet(weightKg = "0", reps = 12),
            )
        }

    @Test
    fun `deleting a training exercise removes only it and keeps the order of the rest`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.pick("Uginanie młotkowe")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.deleteExercise(position = 2)
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Uginanie młotkowe", category = "Biceps", sets = 3),
                PlannedTrainingExercise(name = "Wykroki z hantlami", category = "Nogi", sets = 3),
            )
        }

    @Test
    fun `saving after deleting a training exercise stores the removal`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.enterName("Push day")
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Nogi")
            exercisePicker.pick("Wykroki z hantlami")
            setEditor.addToTraining()
            trainingEditor.save()
            trainingList.openForEditing("Push day")
            trainingEditor.deleteExercise(position = 1)
            trainingEditor.save()
            trainingList.openForEditing("Push day")
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Wykroki z hantlami", category = "Nogi", sets = 3),
            )
        }

    @Test
    fun `closing the options of a training exercise keeps it unchanged`() =
        app {
            given.newTrainingExercise(category = "Plecy", exercise = "Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.closeExerciseOptions(position = 1)
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
            )
        }

    @OnlyOn(Channel.VIEW_MODEL)
    @Test
    fun `deleting a training exercise twice quickly removes only it`() =
        app {
            trainingList.openNewTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Biceps")
            exercisePicker.pick("Uginanie młotkowe")
            setEditor.addToTraining()
            trainingEditor.addExercise()
            exerciseCategoryPicker.pick("Plecy")
            exercisePicker.pick("Pullover z hantlem")
            setEditor.addToTraining()
            trainingEditor.deleteExerciseTwiceQuickly(position = 1)
            trainingEditor.assertExercises(
                PlannedTrainingExercise(name = "Pullover z hantlem", category = "Plecy", sets = 3),
            )
        }
}
