package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.SetEditorDriver

class SetEditorDsl(
    private val driver: SetEditorDriver,
) {
    /** Exactly these Sets, in order from Set 1. */
    fun assertSets(vararg sets: PlannedSet) = driver.assertSetsInOrder(sets.toList())

    fun addToTraining() = driver.tapAddToTraining()

    fun addSet() = driver.addSet()

    fun removeSet() = driver.removeSet()

    fun assertCount(
        count: Int,
        canRemove: Boolean,
        canAdd: Boolean,
    ) = driver.assertCount(count, canRemove, canAdd)

    fun leave() = driver.leave()
}
