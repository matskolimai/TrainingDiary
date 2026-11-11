package pl.skolim.trainingdiary.acceptance.driver

import kotlin.time.Duration

interface AppDriver {
    fun leaveAppFor(duration: Duration)
}
