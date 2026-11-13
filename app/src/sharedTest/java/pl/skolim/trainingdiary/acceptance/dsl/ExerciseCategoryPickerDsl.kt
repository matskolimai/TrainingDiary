package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.ExerciseCategoryPickerDriver

class ExerciseCategoryPickerDsl(
    private val driver: ExerciseCategoryPickerDriver,
) {
    /** Each Exercise category's name paired with its Exercise count, in display order. */
    fun assertListed(vararg categories: Pair<String, Int>) = driver.assertListedInOrder(categories.toList())

    fun leave() = driver.leave()
}
