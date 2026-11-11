package pl.skolim.trainingdiary.acceptance

import pl.skolim.trainingdiary.acceptance.dsl.AppDsl

/**
 * What every Screen's cases need: a way to run steps against the whole app. Each Screen's cases extend it, and each
 * channel's base test class implements [app], so one line wires a Screen's cases to a channel.
 */
interface AcceptanceCases {
    fun app(steps: AppDsl.() -> Unit)
}
