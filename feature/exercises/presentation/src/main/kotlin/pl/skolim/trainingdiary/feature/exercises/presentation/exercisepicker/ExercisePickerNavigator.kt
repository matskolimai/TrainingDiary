package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey

interface ExercisePickerNavigator {
    fun back(key: ExercisePickerKey)
}
