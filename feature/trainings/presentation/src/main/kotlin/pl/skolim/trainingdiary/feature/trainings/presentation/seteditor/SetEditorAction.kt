package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

sealed interface SetEditorAction {
    data object OnAddToTrainingClick : SetEditorAction

    data object OnDecrementSetsClick : SetEditorAction

    data object OnIncrementSetsClick : SetEditorAction

    data class OnValueClick(
        val setIndex: Int,
        val field: SetEditorField,
    ) : SetEditorAction

    data object OnIncrementValueClick : SetEditorAction

    data object OnDecrementValueClick : SetEditorAction

    data object OnBackClick : SetEditorAction
}
