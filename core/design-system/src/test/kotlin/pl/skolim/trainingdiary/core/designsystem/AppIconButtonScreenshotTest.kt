package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppIconButtonScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun iconButton() = paparazzi.snapshot { AppIconButtonPreview() }
}
