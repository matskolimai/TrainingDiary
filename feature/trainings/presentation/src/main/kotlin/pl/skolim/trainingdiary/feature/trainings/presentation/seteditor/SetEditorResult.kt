package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import pl.skolim.trainingdiary.core.presentation.NavResult
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise

sealed interface SetEditorResult : NavResult {
    data class Added(
        val trainingExercise: TrainingExercise,
    ) : SetEditorResult
}
