package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.PlannedSet
import pl.skolim.trainingdiary.acceptance.driver.RepsUnit
import pl.skolim.trainingdiary.acceptance.driver.SetEditorDriver
import kotlin.time.Duration

@Suppress("TooManyFunctions") // One screen owns its acceptance cases and steps on both channels.
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

    fun focusWeight(set: Int) = driver.focusWeight(set)

    fun focusReps(set: Int) = driver.focusReps(set)

    fun assertFocusedReps(set: Int) = driver.assertFocusedReps(set)

    fun assertFocusedWeight(set: Int) = driver.assertFocusedWeight(set)

    fun increaseValue(times: Int = 1) = driver.increaseValue(times)

    fun decreaseValue(times: Int = 1) = driver.decreaseValue(times)

    /** Presses the focused value's + and lets go after [duration]. */
    fun holdIncreaseValue(duration: Duration) = driver.holdIncreaseValue(duration)

    /** Set [set]'s weight is at least [weightKg], written as in [PlannedSet]. */
    fun assertWeightAtLeast(
        set: Int,
        weightKg: String,
    ) = driver.assertWeightAtLeast(set, weightKg)

    fun assertValueControls(
        set: Int,
        canDecrease: Boolean,
        canIncrease: Boolean,
    ) = driver.assertValueControls(set, canDecrease, canIncrease)

    /** Every Set's reps read in [unit]. */
    fun assertRepsUnit(unit: RepsUnit) = driver.assertRepsUnit(unit)

    fun leave() = driver.leave()
}
