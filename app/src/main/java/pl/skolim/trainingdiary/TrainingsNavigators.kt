package pl.skolim.trainingdiary

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorResult

class AppTrainingListNavigator(
    private val navigator: AppNavigator,
) : TrainingListNavigator {
    override fun openEditor(trainingId: Long?) = navigator.push(TrainingEditorKey(trainingId))
}

class AppTrainingEditorNavigator(
    private val navigator: AppNavigator,
) : TrainingEditorNavigator {
    override val setEditorResults: Flow<SetEditorResult> = navigator.results.filterIsInstance<SetEditorResult>()

    override fun openExercisePicker() = navigator.push(ExerciseCategoryPickerKey)

    override fun openSetEditor(
        exerciseId: Long,
        index: Int,
        sets: List<ExerciseSet>,
    ) = navigator.push(SetEditorKey(exerciseId, index, sets))

    override fun back(key: TrainingEditorKey) = navigator.pop(key)
}

class AppSetEditorNavigator(
    private val navigator: AppNavigator,
) : SetEditorNavigator {
    override fun back(key: SetEditorKey) = navigator.pop(key)

    override fun backToTrainingEditor(result: SetEditorResult) = navigator.popTo(TrainingEditorKey::class, result)
}
