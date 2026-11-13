package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

sealed interface ExerciseCategoryPickerAction {
    data object OnBackClick : ExerciseCategoryPickerAction
}
