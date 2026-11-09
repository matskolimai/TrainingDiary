package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingsUiDriver

class TrainingsDsl(
    private val driver: TrainingsUiDriver,
) {
    fun assertNoTrainings() = driver.assertEmptyList()
}
