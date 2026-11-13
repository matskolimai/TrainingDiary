package pl.skolim.trainingdiary.acceptance.driver

/** The Exercise category picker's steps, implemented once per channel. */
interface ExerciseCategoryPickerDriver {
    fun assertListedInOrder(categories: List<Pair<String, Int>>)

    fun leave()
}
