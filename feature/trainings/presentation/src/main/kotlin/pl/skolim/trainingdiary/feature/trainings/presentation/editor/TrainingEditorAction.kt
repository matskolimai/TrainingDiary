package pl.skolim.trainingdiary.feature.trainings.presentation.editor

sealed interface TrainingEditorAction {
    data class OnNameChange(
        val name: String,
    ) : TrainingEditorAction

    data object OnAddExerciseClick : TrainingEditorAction

    data class OnExerciseClick(
        val index: Int,
    ) : TrainingEditorAction

    data class OnExerciseOptionsClick(
        val index: Int,
    ) : TrainingEditorAction

    data object OnExerciseOptionsDismiss : TrainingEditorAction

    data object OnEditExerciseClick : TrainingEditorAction

    data object OnDeleteExerciseClick : TrainingEditorAction

    data object OnSaveClick : TrainingEditorAction

    data object OnBackClick : TrainingEditorAction
}
