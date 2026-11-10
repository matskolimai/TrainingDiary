package pl.skolim.trainingdiary.feature.trainings.presentation.list

sealed interface TrainingListAction {
    data object OnAddClick : TrainingListAction

    data class OnOptionsClick(
        val trainingId: Long,
    ) : TrainingListAction

    data object OnOptionsDismiss : TrainingListAction

    data class OnEditClick(
        val trainingId: Long,
    ) : TrainingListAction

    data class OnDeleteClick(
        val trainingId: Long,
    ) : TrainingListAction

    data object OnUndoClick : TrainingListAction
}
