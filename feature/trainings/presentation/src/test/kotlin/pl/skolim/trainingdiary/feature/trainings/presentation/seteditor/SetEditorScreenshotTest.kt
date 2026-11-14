package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class SetEditorScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun min() = paparazzi.snapshot { SetEditorPreviewMin() }

    @Test
    fun max() = paparazzi.snapshot { SetEditorPreviewMax() }

    @Test
    fun new() = paparazzi.snapshot { SetEditorPreviewNew() }
}
