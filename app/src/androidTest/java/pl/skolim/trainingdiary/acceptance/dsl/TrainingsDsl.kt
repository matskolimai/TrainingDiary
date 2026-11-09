package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingsUiDriver

class TrainingsDsl(
    private val driver: TrainingsUiDriver,
) {
    fun assertNoTrainings() = driver.assertEmptyList()

    fun startAdding() = driver.openEditorForAdding()

    fun assertNewTrainingWithEmptyName() = driver.assertEmptyEditorForAdding()

    fun enterName(name: String) = driver.typeName(name)

    fun leaveWithoutSaving() = driver.leaveEditor()
}
