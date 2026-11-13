package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppDiscardDialogScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun discardDialog() = paparazzi.snapshot { AppDiscardDialogPreview() }
}
