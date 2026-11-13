package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppRepeatIconButtonScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun enabled() = paparazzi.snapshot { AppRepeatIconButtonPreviewEnabled() }

    @Test
    fun disabled() = paparazzi.snapshot { AppRepeatIconButtonPreviewDisabled() }
}
