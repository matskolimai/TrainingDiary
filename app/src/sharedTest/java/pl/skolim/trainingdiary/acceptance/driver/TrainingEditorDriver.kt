package pl.skolim.trainingdiary.acceptance.driver

/** The Training editor's steps, implemented once per channel. */
interface TrainingEditorDriver {
    fun assertEmptyForAdding()

    fun assertShowingForEditing(name: String)

    fun assertNameFieldFocused()

    fun typeName(name: String)

    fun replaceName(name: String)

    fun tapAddExercise()

    fun tapSave()

    fun tapSaveTwiceQuickly()

    fun assertCannotSave()

    fun leave()
}
