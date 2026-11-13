package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.ExercisePickerDriver

class ExercisePickerDsl(
    private val driver: ExercisePickerDriver,
) {
    /** Exercise names in display order. */
    fun assertListed(vararg exercises: String) = driver.assertListedInOrder(exercises.toList())

    fun leave() = driver.leave()
}
