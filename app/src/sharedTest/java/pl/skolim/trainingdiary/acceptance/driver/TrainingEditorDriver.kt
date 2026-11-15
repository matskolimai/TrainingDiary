package pl.skolim.trainingdiary.acceptance.driver

/** The Training editor's steps, implemented once per channel. */
@Suppress("TooManyFunctions") // One screen owns its acceptance cases and steps on both channels.
interface TrainingEditorDriver {
    fun assertEmptyForAdding()

    fun assertShowingForEditing(name: String)

    fun assertNameFieldFocused()

    fun assertExercisesInOrder(exercises: List<PlannedTrainingExercise>)

    fun typeName(name: String)

    fun replaceName(name: String)

    fun tapAddExercise()

    fun tapExercise(position: Int)

    fun tapSave()

    fun tapSaveTwiceQuickly()

    fun assertCannotSave()

    fun leave()
}

/** One Training exercise as its card reads: the Exercise name, its category and the number of Sets. */
data class PlannedTrainingExercise(
    val name: String,
    val category: String,
    val sets: Int,
)
