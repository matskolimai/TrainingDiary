package pl.skolim.trainingdiary.feature.trainings.presentation.editor

sealed interface TrainingEditorEvent {
    data object TrainingSaved : TrainingEditorEvent

    data object TrainingMissing : TrainingEditorEvent
}
