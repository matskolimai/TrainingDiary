package pl.skolim.trainingdiary

import android.content.Context
import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.ParameterTypeInjection
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

class KoinModulesTest {
    // verifyAll checks each module alone, so one module including the rest lets a binding use another module's.
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `koin graph is complete`() {
        module { includes(appModules) }.verify(
            extraTypes = listOf(Context::class),
            injections =
                injectedParameters(
                    ParameterTypeInjection(
                        targetType = internalClass(TrainingEditorKey::class.java, "editor.TrainingEditorViewModel"),
                        injectedTypes = listOf(TrainingEditorKey::class),
                    ),
                ),
        )
    }
}

// ViewModels are internal to their feature, so :app finds one by name in its key's package; a rename fails the test.
private fun internalClass(
    nextTo: Class<*>,
    relativeName: String,
) = Class.forName("${nextTo.packageName}.$relativeName").kotlin
