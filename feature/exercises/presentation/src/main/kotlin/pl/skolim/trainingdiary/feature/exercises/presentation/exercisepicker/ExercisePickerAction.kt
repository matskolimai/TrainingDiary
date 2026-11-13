package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

sealed interface ExercisePickerAction {
    data class OnExerciseClick(
        val exerciseId: Long,
    ) : ExercisePickerAction

    data object OnBackClick : ExercisePickerAction
}
