package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppFabScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun fab() = paparazzi.snapshot { AppFabPreview() }
}
