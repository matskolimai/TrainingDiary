package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingsUiDriver

class TrainingsDsl(
    private val driver: TrainingsUiDriver,
) {
    fun givenTrainings(vararg names: String) = names.forEach(driver::addTraining)

    fun assertTrainingsListed(vararg names: String) = driver.assertListedInOrder(names.toList())

    fun assertNoTrainings() = driver.assertEmptyList()

    fun assertTrainingCount(count: Int) = driver.assertCount(count)

    fun assertNoTrainingCount() = driver.assertNoCount()

    fun openNewTrainingScreen() = driver.openEditorForAdding()

    fun assertNewTrainingScreenIsEmpty() = driver.assertEmptyEditorForAdding()

    fun assertNameReadyToType() = driver.assertNameFieldFocused()

    fun openEditTrainingScreen(name: String) = driver.openEditorForEditing(name)

    fun assertEditTrainingScreenShows(name: String) = driver.assertEditorForEditing(name)

    fun enterName(name: String) = driver.typeName(name)

    fun replaceName(name: String) = driver.replaceName(name)

    fun save() = driver.tapSave()

    fun assertSaveUnavailable() = driver.assertCannotSave()

    fun leaveWithoutSaving() = driver.leaveEditor()

    fun delete(name: String) = driver.deleteTraining(name)

    fun assertUndoOffered(name: String) = driver.assertUndoDeleteOffered(name)

    fun undoDelete() = driver.undoDelete()
}
