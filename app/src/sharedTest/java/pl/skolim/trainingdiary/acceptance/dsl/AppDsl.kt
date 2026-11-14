package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.AppDriver
import pl.skolim.trainingdiary.acceptance.driver.ExerciseCategoryPickerDriver
import pl.skolim.trainingdiary.acceptance.driver.ExercisePickerDriver
import pl.skolim.trainingdiary.acceptance.driver.SetEditorDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListDriver
import kotlin.time.Duration

class AppDsl(
    private val appDriver: AppDriver,
    trainingListDriver: TrainingListDriver,
    trainingEditorDriver: TrainingEditorDriver,
    exerciseCategoryPickerDriver: ExerciseCategoryPickerDriver,
    exercisePickerDriver: ExercisePickerDriver,
    setEditorDriver: SetEditorDriver,
) {
    val trainingList = TrainingListDsl(trainingListDriver)
    val trainingEditor = TrainingEditorDsl(trainingEditorDriver)
    val exerciseCategoryPicker = ExerciseCategoryPickerDsl(exerciseCategoryPickerDriver)
    val exercisePicker = ExercisePickerDsl(exercisePickerDriver)
    val setEditor = SetEditorDsl(setEditorDriver)
    val given = GivenDsl(trainingList, trainingEditor, exerciseCategoryPicker, exercisePicker)

    fun leaveAppFor(duration: Duration) = appDriver.leaveAppFor(duration)
}
