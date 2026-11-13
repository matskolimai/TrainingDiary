package pl.skolim.trainingdiary.feature.trainings.domain

import pl.skolim.trainingdiary.core.domain.Exercise

data class TrainingExercise(
    val exercise: Exercise,
    val sets: List<ExerciseSet>,
)
