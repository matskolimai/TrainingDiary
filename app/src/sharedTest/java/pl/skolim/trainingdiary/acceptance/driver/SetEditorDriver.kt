package pl.skolim.trainingdiary.acceptance.driver

/** The Set editor's steps, implemented once per channel. */
@Suppress("TooManyFunctions") // One screen owns its acceptance cases and steps on both channels.
interface SetEditorDriver {
    fun assertSetsInOrder(sets: List<PlannedSet>)

    fun tapAddToTraining()

    fun addSet()

    fun removeSet()

    fun assertCount(
        count: Int,
        canRemove: Boolean,
        canAdd: Boolean,
    )

    fun focusWeight(set: Int)

    fun focusReps(set: Int)

    fun assertFocusedReps(set: Int)

    fun assertFocusedWeight(set: Int)

    fun increaseValue(times: Int)

    fun decreaseValue(times: Int)

    fun assertValueControls(
        set: Int,
        canDecrease: Boolean,
        canIncrease: Boolean,
    )

    fun assertRepsUnit(unit: RepsUnit)

    fun leave()
}

/** One Set as the user reads it: the weight in kilograms with a Polish decimal comma („1,5”) and the reps. */
data class PlannedSet(
    val weightKg: String,
    val reps: Int,
)

/** What a Set's reps stand for: repetitions („12 powt.”) or, for timed Exercises, seconds („12 s”). */
enum class RepsUnit { REPETITIONS, SECONDS }
