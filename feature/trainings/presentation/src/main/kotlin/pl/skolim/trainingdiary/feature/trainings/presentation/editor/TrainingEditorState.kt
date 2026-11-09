package pl.skolim.trainingdiary.feature.trainings.presentation.editor

internal data class TrainingEditorState(
    val name: String = "",
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false, // only while edit mode loads the Training
    val isSaving: Boolean = false,
)
