package pl.skolim.trainingdiary.acceptance.driver

import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.acceptance.OnlyOn

/**
 * Thrown by a driver for a step its [channel] cannot stage, so that no case passes there without checking anything.
 * The fix is to tag the case with [OnlyOn] the channel that can stage the step.
 */
class CannotStageException(
    step: String,
    channel: Channel,
) : UnsupportedOperationException(
        "The step '$step' cannot be staged on the $channel channel. " +
            "Tag the case with @OnlyOn for a channel that can stage it.",
    )
