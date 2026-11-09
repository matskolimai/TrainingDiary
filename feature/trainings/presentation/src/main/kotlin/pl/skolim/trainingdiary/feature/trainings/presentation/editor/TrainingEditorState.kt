package pl.skolim.trainingdiary.feature.trainings.presentation.editor

data class TrainingEditorState(
    val name: String = "",
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false, // only while edit mode loads the Training
    val isSaving: Boolean = false,
)
