package pl.skolim.trainingdiary.core.domain

// Lists are in display order. Synchronous while the catalogue is constants.
interface ExerciseCatalogue {
    fun categories(): List<ExerciseCategory>

    fun exercises(categoryId: Long): List<Exercise>

    // Fails for an id outside the catalogue.
    fun exercise(id: Long): Exercise
}
