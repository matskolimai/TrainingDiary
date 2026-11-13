package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

// An Exercise category as its picker row shows it: with the number of Exercises in it.
data class ExerciseCategoryUi(
    val id: Long,
    val name: String,
    val exerciseCount: Int,
)
