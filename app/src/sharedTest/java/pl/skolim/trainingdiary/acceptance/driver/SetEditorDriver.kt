package pl.skolim.trainingdiary.acceptance.driver

/** The Set editor's steps, implemented once per channel. */
interface SetEditorDriver {
    fun assertSetsInOrder(sets: List<PlannedSet>)

    fun tapAddToTraining()

    fun leave()
}

/** One Set as the user reads it: the weight in kilograms with a Polish decimal comma („1,5”) and the reps. */
data class PlannedSet(
    val weightKg: String,
    val reps: Int,
)
