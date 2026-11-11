package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorDriver

class TrainingEditorDsl(
    private val driver: TrainingEditorDriver,
) {
    fun assertEmptyNewTraining() = driver.assertEmptyForAdding()

    fun assertNameReadyToType() = driver.assertNameFieldFocused()

    fun assertEditing(name: String) = driver.assertShowingForEditing(name)

    fun enterName(name: String) = driver.typeName(name)

    fun replaceName(name: String) = driver.replaceName(name)

    fun save() = driver.tapSave()

    fun assertSaveUnavailable() = driver.assertCannotSave()

    fun leaveWithoutSaving() = driver.leave()
}
