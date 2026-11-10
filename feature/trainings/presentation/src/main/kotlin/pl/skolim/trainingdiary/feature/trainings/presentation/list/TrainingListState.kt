package pl.skolim.trainingdiary.feature.trainings.presentation.list

import pl.skolim.trainingdiary.feature.trainings.domain.Training

data class TrainingListState(
    val trainings: List<Training> = emptyList(),
    val isLoading: Boolean = true, // until the first emission, so the empty state never flashes
    val optionsSheetTrainingId: Long? = null, // the Training whose options sheet is open
)
