package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingsUiDriver

class TrainingsDsl(
    private val driver: TrainingsUiDriver,
) {
    fun givenTrainings(vararg names: String) = names.forEach(driver::addTraining)

    fun assertTrainingsListed(vararg names: String) = driver.assertListedInOrder(names.toList())

    fun assertNoTrainings() = driver.assertEmptyList()

    fun openNewTrainingScreen() = driver.openEditorForAdding()

    fun assertNewTrainingScreenIsEmpty() = driver.assertEmptyEditorForAdding()

    fun assertNameReadyToType() = driver.assertNameFieldFocused()

    fun enterName(name: String) = driver.typeName(name)

    fun save() = driver.tapSave()

    fun assertSaveUnavailable() = driver.assertCannotSave()

    fun leaveWithoutSaving() = driver.leaveEditor()
}
