package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.SetEditorDriver

class SetEditorDsl(
    private val driver: SetEditorDriver,
) {
    /** Exactly these Sets, in order from Set 1. */
    fun assertSets(vararg sets: PlannedSet) = driver.assertSetsInOrder(sets.toList())

    fun addToTraining() = driver.tapAddToTraining()

    fun leave() = driver.leave()
}
