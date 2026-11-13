package pl.skolim.trainingdiary.feature.trainings.domain

// Named ExerciseSet to avoid kotlin.collections.Set; the glossary term is Set.
data class ExerciseSet(
    val reps: Int,
    val weightGrams: Int,
)
