package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppBottomSheetScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun bottomSheet() = paparazzi.snapshot { AppBottomSheetPreview() }
}
