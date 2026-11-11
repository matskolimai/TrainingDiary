package pl.skolim.trainingdiary.acceptance.driver

import kotlin.time.Duration

class AppViewModelDriver(
    private val app: HeadlessApp,
) : AppDriver {
    override fun leaveAppFor(duration: Duration) = app.leaveFor(duration)
}
