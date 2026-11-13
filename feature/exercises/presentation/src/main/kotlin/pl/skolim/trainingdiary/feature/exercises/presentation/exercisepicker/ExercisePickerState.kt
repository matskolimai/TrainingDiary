package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

import pl.skolim.trainingdiary.core.domain.Exercise

data class ExercisePickerState(
    val exercises: List<Exercise> = emptyList(),
)
