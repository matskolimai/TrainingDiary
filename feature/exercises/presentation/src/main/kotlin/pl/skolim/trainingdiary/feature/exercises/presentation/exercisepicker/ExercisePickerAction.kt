package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

sealed interface ExercisePickerAction {
    data object OnBackClick : ExercisePickerAction
}
