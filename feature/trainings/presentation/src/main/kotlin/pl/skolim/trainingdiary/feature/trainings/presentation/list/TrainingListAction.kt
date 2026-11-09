package pl.skolim.trainingdiary.feature.trainings.presentation.list

internal sealed interface TrainingListAction {
    data object OnAddClick : TrainingListAction
}
