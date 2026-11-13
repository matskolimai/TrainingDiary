package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

sealed interface ExerciseCategoryPickerAction {
    data class OnCategoryClick(
        val categoryId: Long,
    ) : ExerciseCategoryPickerAction

    data object OnBackClick : ExerciseCategoryPickerAction
}
