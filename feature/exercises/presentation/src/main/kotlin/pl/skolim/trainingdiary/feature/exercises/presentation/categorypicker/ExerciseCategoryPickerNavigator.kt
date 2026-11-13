package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey

interface ExerciseCategoryPickerNavigator {
    fun openExercises(categoryId: Long)

    fun back(key: ExerciseCategoryPickerKey)
}
