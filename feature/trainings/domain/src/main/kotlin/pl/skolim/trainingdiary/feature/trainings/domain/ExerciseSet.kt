package pl.skolim.trainingdiary.feature.trainings.domain

import kotlinx.serialization.Serializable

/** One Set; named so to stay clear of `kotlin.collections.Set`. Serializable so a nav key can carry it. */
@Serializable
data class ExerciseSet(
    val reps: Int,
    val weightGrams: Int,
)
