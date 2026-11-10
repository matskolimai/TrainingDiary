package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import pl.skolim.trainingdiary.feature.trainings.domain.isValidTrainingName

data class TrainingEditorState(
    val name: String = "",
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false, // only while edit mode loads the Training
    val isSaving: Boolean = false,
) {
    val canSave: Boolean get() = !isSaving && isValidTrainingName(name)
}
