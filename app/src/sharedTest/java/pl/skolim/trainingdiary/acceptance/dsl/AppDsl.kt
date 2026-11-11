package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListDriver

class AppDsl(
    trainingListDriver: TrainingListDriver,
    trainingEditorDriver: TrainingEditorDriver,
) {
    val trainingList = TrainingListDsl(trainingListDriver)
    val trainingEditor = TrainingEditorDsl(trainingEditorDriver)
    val given = GivenDsl(trainingList, trainingEditor)
}
