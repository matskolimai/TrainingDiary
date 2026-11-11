package pl.skolim.trainingdiary.acceptance.driver

/** The Training list's steps, implemented once per channel. */
interface TrainingListDriver {
    fun assertEmptyList()

    fun assertListedInOrder(names: List<String>)

    fun openNewTraining()

    fun openTrainingForEditing(name: String)

    fun deleteTraining(name: String)

    fun assertUndoDeleteOffered(name: String)

    fun undoDelete()
}
