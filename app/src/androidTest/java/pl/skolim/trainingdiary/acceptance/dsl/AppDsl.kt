package pl.skolim.trainingdiary.acceptance.dsl

import android.content.res.Resources
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorUiDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListUiDriver

class AppDsl(
    compose: ComposeContext,
    resources: Resources,
) {
    val trainingList = TrainingListDsl(TrainingListUiDriver(compose, resources))
    val trainingEditor = TrainingEditorDsl(TrainingEditorUiDriver(compose, resources))
    val given = GivenDsl(trainingList, trainingEditor)
}
