package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppValuePillScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun focused() = paparazzi.snapshot { AppValuePillPreviewFocused() }

    @Test
    fun unfocused() = paparazzi.snapshot { AppValuePillPreviewUnfocused() }
}
