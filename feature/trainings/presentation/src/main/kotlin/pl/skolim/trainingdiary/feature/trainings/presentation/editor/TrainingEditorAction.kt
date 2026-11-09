package pl.skolim.trainingdiary.feature.trainings.presentation.editor

internal sealed interface TrainingEditorAction {
    data class OnNameChange(
        val name: String,
    ) : TrainingEditorAction

    data object OnBackClick : TrainingEditorAction
}
