package pl.skolim.trainingdiary.acceptance.dsl

import pl.skolim.trainingdiary.acceptance.driver.AppDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListDriver
import kotlin.time.Duration

class AppDsl(
    private val appDriver: AppDriver,
    trainingListDriver: TrainingListDriver,
    trainingEditorDriver: TrainingEditorDriver,
) {
    val trainingList = TrainingListDsl(trainingListDriver)
    val trainingEditor = TrainingEditorDsl(trainingEditorDriver)
    val given = GivenDsl(trainingList, trainingEditor)

    fun leaveAppFor(duration: Duration) = appDriver.leaveAppFor(duration)
}
