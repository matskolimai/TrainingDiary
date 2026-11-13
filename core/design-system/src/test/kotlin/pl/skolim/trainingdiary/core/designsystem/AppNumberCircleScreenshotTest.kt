package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppNumberCircleScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun numberCircle() = paparazzi.snapshot { AppNumberCirclePreview() }
}
