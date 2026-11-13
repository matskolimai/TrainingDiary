package pl.skolim.trainingdiary.acceptance.driver

/** The Exercise picker's steps, implemented once per channel. */
interface ExercisePickerDriver {
    fun assertListedInOrder(exercises: List<String>)

    fun leave()
}
