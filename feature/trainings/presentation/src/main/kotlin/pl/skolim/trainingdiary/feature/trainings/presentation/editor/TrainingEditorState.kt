package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
import pl.skolim.trainingdiary.feature.trainings.domain.isValidTrainingName

data class TrainingEditorState(
    val name: String = "",
    val exercises: List<TrainingExercise> = emptyList(),
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false, // only while edit mode loads the Training
    val isSaving: Boolean = false,
    val optionsSheetIndex: Int? = null, // the Training exercise whose options sheet is open
) {
    val canSave: Boolean get() = !isSaving && isValidTrainingName(name)
}
