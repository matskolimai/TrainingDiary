package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppOutlinedButtonScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun withIcon() = paparazzi.snapshot { AppOutlinedButtonPreview() }
}
