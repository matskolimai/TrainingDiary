package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

interface SetEditorNavigator {
    fun back(key: SetEditorKey)

    fun backToTrainingEditor(result: SetEditorResult)
}
