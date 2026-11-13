package pl.skolim.trainingdiary

import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerNavigator
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

class AppExerciseCategoryPickerNavigator(
    private val navigator: AppNavigator,
) : ExerciseCategoryPickerNavigator {
    override fun openExercises(categoryId: Long) = navigator.push(ExercisePickerKey(categoryId))

    override fun back(key: ExerciseCategoryPickerKey) = navigator.pop(key)
}

class AppExercisePickerNavigator(
    private val navigator: AppNavigator,
) : ExercisePickerNavigator {
    override fun onExercisePicked(exerciseId: Long) = navigator.push(SetEditorKey(exerciseId))

    override fun back(key: ExercisePickerKey) = navigator.pop(key)
}
