package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

sealed interface SetEditorAction {
    data object OnAddToTrainingClick : SetEditorAction

    data object OnDecrementSetsClick : SetEditorAction

    data object OnIncrementSetsClick : SetEditorAction

    data object OnBackClick : SetEditorAction
}
