package pl.skolim.trainingdiary.core.designsystem

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import org.junit.Rule
import org.junit.Test

class AppTopBarScreenshotTest {
    @get:Rule
    val paparazzi =
        Paparazzi(
            deviceConfig = DeviceConfig.PIXEL_9.copy(nightMode = NightMode.NIGHT, locale = "pl", fontScale = 1f),
        )

    @Test
    fun topBar() = paparazzi.snapshot { AppTopBarPreview() }
}
