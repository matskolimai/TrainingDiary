package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppListRowScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun shortText() = paparazzi.snapshot { AppListRowPreviewShortText() }

    @Test
    fun longText() = paparazzi.snapshot { AppListRowPreviewLongText() }
}
