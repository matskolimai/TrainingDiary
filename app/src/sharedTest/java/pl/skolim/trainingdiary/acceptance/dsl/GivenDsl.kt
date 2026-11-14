package pl.skolim.trainingdiary.acceptance.dsl

class GivenDsl(
    private val trainingList: TrainingListDsl,
    private val trainingEditor: TrainingEditorDsl,
    private val exerciseCategoryPicker: ExerciseCategoryPickerDsl,
    private val exercisePicker: ExercisePickerDsl,
) {
    /** Opens the Set editor for a new Training exercise in an unsaved Training. */
    fun newTrainingExercise(
        category: String,
        exercise: String,
    ) {
        trainingList.openNewTraining()
        trainingEditor.addExercise()
        exerciseCategoryPicker.pick(category)
        exercisePicker.pick(exercise)
    }

    fun trainings(vararg names: String) =
        names.forEach { name ->
            trainingList.openNewTraining()
            trainingEditor.enterName(name)
            trainingEditor.save()
        }
}
