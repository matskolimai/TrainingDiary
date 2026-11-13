package pl.skolim.trainingdiary.feature.trainings.domain

/** One Set; named so to stay clear of `kotlin.collections.Set`. */
data class ExerciseSet(
    val reps: Int,
    val weightGrams: Int,
)
