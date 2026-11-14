package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppCountRowScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun countRow() = paparazzi.snapshot { AppCountRowPreview() }
}
