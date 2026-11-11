package pl.skolim.trainingdiary.acceptance

import org.junit.jupiter.api.extension.ExtendWith
import pl.skolim.trainingdiary.acceptance.driver.ChannelCondition
import pl.skolim.trainingdiary.acceptance.dsl.AppDsl

/**
 * What every Screen's cases need: a way to run steps against the whole app. Each Screen's cases extend it, and each
 * channel's base test class implements [channel] and [app], so one line wires a Screen's cases to a channel.
 */
@ExtendWith(ChannelCondition::class)
interface AcceptanceCases {
    /** The channel these cases run on; a case tagged [OnlyOn] another channel is skipped. */
    val channel: Channel

    fun app(steps: AppDsl.() -> Unit)
}
