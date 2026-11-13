package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppCountStepperScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun middle() = paparazzi.snapshot { AppCountStepperPreviewMiddle() }

    @Test
    fun min() = paparazzi.snapshot { AppCountStepperPreviewMin() }

    @Test
    fun max() = paparazzi.snapshot { AppCountStepperPreviewMax() }
}
