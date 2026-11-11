package pl.skolim.trainingdiary.acceptance.driver

import org.junit.jupiter.api.extension.ConditionEvaluationResult
import org.junit.jupiter.api.extension.ExecutionCondition
import org.junit.jupiter.api.extension.ExtensionContext
import pl.skolim.trainingdiary.acceptance.AcceptanceCases
import pl.skolim.trainingdiary.acceptance.OnlyOn

/** Skips a case tagged [OnlyOn] on any channel other than the one it names. Registered on [AcceptanceCases]. */
class ChannelCondition : ExecutionCondition {
    override fun evaluateExecutionCondition(context: ExtensionContext): ConditionEvaluationResult {
        val onlyOn =
            context.testMethod.orElse(null)?.getAnnotation(OnlyOn::class.java)
                ?: return ConditionEvaluationResult.enabled("not tagged for one channel")
        // JUnit creates the test instance before it evaluates a test method's conditions.
        val runsOn = (context.requiredTestInstance as AcceptanceCases).channel
        return if (runsOn == onlyOn.channel) {
            ConditionEvaluationResult.enabled("tagged for $runsOn, running on $runsOn")
        } else {
            ConditionEvaluationResult.disabled("tagged for ${onlyOn.channel}, running on $runsOn")
        }
    }
}
