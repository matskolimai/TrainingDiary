package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import kotlinx.coroutines.flow.Flow
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorResult

interface TrainingEditorNavigator {
    val setEditorResults: Flow<SetEditorResult>

    fun openExercisePicker()

    fun openSetEditor(
        exerciseId: Long,
        index: Int,
        sets: List<ExerciseSet>,
    )

    fun back(key: TrainingEditorKey)
}
