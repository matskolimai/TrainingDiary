package pl.skolim.trainingdiary.feature.trainings.presentation.list

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class TrainingListScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun loading() = paparazzi.snapshot { TrainingListPreviewLoading() }

    @Test
    fun empty() = paparazzi.snapshot { TrainingListPreviewEmpty() }

    @Test
    fun withTrainings() = paparazzi.snapshot { TrainingListPreviewWithTrainings() }

    @Test
    fun optionsSheet() = paparazzi.snapshot { TrainingListPreviewOptionsSheet() }
}
