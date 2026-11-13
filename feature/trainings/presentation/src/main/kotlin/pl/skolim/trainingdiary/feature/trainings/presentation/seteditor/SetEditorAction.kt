package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

sealed interface SetEditorAction {
    data object OnAddToTrainingClick : SetEditorAction

    data object OnBackClick : SetEditorAction
}
