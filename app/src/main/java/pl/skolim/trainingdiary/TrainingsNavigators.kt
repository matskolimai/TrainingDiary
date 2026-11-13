package pl.skolim.trainingdiary

import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListNavigator

class AppTrainingListNavigator(
    private val navigator: AppNavigator,
) : TrainingListNavigator {
    override fun openEditor(trainingId: Long?) = navigator.push(TrainingEditorKey(trainingId))
}

class AppTrainingEditorNavigator(
    private val navigator: AppNavigator,
) : TrainingEditorNavigator {
    override fun openExercisePicker() = navigator.push(ExerciseCategoryPickerKey)

    override fun back(key: TrainingEditorKey) = navigator.pop(key)
}
