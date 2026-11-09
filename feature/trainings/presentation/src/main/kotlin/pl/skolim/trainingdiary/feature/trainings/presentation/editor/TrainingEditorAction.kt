package pl.skolim.trainingdiary.feature.trainings.presentation.editor

sealed interface TrainingEditorAction {
    data class OnNameChange(
        val name: String,
    ) : TrainingEditorAction

    data object OnBackClick : TrainingEditorAction
}
