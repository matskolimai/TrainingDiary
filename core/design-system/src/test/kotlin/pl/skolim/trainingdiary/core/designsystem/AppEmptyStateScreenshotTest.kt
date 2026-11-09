package pl.skolim.trainingdiary.core.designsystem

import org.junit.Rule
import org.junit.Test
import pl.skolim.trainingdiary.core.screenshottesting.appPaparazzi

class AppEmptyStateScreenshotTest {
    @get:Rule
    val paparazzi = appPaparazzi()

    @Test
    fun emptyState() = paparazzi.snapshot { AppEmptyStatePreview() }
}
