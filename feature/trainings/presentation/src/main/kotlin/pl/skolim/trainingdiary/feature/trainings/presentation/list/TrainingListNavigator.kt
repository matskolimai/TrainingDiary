package pl.skolim.trainingdiary.feature.trainings.presentation.list

interface TrainingListNavigator {
    fun openEditor(trainingId: Long? = null) // null means add mode
}
