package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppSheetItemScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun sheetItem() = paparazzi.snapshot { AppSheetItemPreview() }
}
