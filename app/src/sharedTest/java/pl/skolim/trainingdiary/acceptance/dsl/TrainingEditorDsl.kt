package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.PlannedTrainingExercise
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorDriver

@Suppress("TooManyFunctions") // One screen owns its acceptance cases and steps on both channels.
class TrainingEditorDsl(
    private val driver: TrainingEditorDriver,
) {
    fun assertEmptyNewTraining() = driver.assertEmptyForAdding()

    fun assertNameReadyToType() = driver.assertNameFieldFocused()

    fun assertEditing(name: String) = driver.assertShowingForEditing(name)

    /** Exactly these Training exercises, in order. */
    fun assertExercises(vararg exercises: PlannedTrainingExercise) = driver.assertExercisesInOrder(exercises.toList())

    fun enterName(name: String) = driver.typeName(name)

    fun replaceName(name: String) = driver.replaceName(name)

    fun addExercise() = driver.tapAddExercise()

    /** Opens the Training exercise at [position], counted from 1, in the Set editor. */
    fun editExercise(position: Int) = driver.tapExercise(position)

    fun save() = driver.tapSave()

    fun saveTwiceQuickly() = driver.tapSaveTwiceQuickly()

    fun assertSaveUnavailable() = driver.assertCannotSave()

    fun leaveWithoutSaving() = driver.leave()
}
