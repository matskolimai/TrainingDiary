package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppCountBadgeScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun countBadge() = paparazzi.snapshot { AppCountBadgePreview() }
}
