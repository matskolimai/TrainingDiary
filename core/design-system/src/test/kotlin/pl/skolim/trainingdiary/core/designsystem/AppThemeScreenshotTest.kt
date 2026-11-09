package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppThemeScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun theme() = paparazzi.snapshot { AppThemePreview() }
}
