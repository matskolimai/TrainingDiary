package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppTopBarWithBackScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun topBarWithBack() = paparazzi.snapshot { AppTopBarWithBackPreview() }

    @Test
    fun topBarWithBackAndSubtitle() = paparazzi.snapshot { AppTopBarWithBackSubtitlePreview() }
}
