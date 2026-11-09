package pl.skolim.trainingdiary.core.screenshottesting

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode

/** The Paparazzi rule every screenshot test uses: a Pixel 9 in the app's dark theme, Polish locale, font scale 1. */
fun appPaparazzi(): Paparazzi =
    Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_9.copy(nightMode = NightMode.NIGHT, locale = "pl", fontScale = 1f),
    )
