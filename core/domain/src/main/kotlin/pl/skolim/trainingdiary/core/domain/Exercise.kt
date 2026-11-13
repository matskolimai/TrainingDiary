package pl.skolim.trainingdiary.core.domain

// A timed Exercise's reps stand for seconds.
data class Exercise(
    val id: Long,
    val name: String,
    val category: ExerciseCategory,
    val isTimed: Boolean = false,
)
