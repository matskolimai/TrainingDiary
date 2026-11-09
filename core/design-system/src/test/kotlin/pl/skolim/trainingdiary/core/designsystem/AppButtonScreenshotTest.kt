package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppButtonScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun enabled() = paparazzi.snapshot { AppButtonPreviewEnabled() }

    @Test
    fun disabled() = paparazzi.snapshot { AppButtonPreviewDisabled() }
}
