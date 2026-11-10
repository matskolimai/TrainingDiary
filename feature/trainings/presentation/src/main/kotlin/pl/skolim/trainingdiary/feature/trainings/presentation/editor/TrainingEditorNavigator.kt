package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

interface TrainingEditorNavigator {
    fun back(key: TrainingEditorKey)
}
