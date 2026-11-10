package pl.skolim.trainingdiary.feature.trainings.presentation.list

sealed interface TrainingListEvent {
    data class TrainingDeleted(
        val name: String,
    ) : TrainingListEvent
}
