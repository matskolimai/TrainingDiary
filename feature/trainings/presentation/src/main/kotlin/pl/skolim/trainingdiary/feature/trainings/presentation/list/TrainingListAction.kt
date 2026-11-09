package pl.skolim.trainingdiary.feature.trainings.presentation.list

sealed interface TrainingListAction {
    data object OnAddClick : TrainingListAction
}
