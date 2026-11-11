package pl.skolim.trainingdiary.acceptance.driver

import pl.skolim.trainingdiary.acceptance.Channel
import kotlin.time.Duration

class AppUiDriver : AppDriver {
    override fun leaveAppFor(duration: Duration): Unit = throw CannotStageException("leaveAppFor", Channel.DEVICE)
}
