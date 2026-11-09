package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppTextFieldScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun empty() = paparazzi.snapshot { AppTextFieldPreviewEmpty() }

    @Test
    fun filled() = paparazzi.snapshot { AppTextFieldPreviewFilled() }
}
