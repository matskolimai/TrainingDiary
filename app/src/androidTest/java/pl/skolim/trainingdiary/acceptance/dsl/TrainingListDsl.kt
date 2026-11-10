package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingListUiDriver

class TrainingListDsl(
    private val driver: TrainingListUiDriver,
) {
    fun assertListed(vararg names: String) = driver.assertListedInOrder(names.toList())

    fun assertEmpty() = driver.assertEmptyList()

    fun openNewTraining() = driver.openNewTraining()

    fun openForEditing(name: String) = driver.openTrainingForEditing(name)

    fun delete(name: String) = driver.deleteTraining(name)

    fun assertUndoOffered(name: String) = driver.assertUndoDeleteOffered(name)

    fun undoDelete() = driver.undoDelete()
}
