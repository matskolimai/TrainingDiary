package pl.skolim.trainingdiary.acceptance.dsl

class GivenDsl(
    private val trainingList: TrainingListDsl,
    private val trainingEditor: TrainingEditorDsl,
) {
    fun trainings(vararg names: String) =
        names.forEach { name ->
            trainingList.openNewTraining()
            trainingEditor.enterName(name)
            trainingEditor.save()
        }
}
