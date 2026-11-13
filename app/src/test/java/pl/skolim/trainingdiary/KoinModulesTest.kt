package pl.skolim.trainingdiary

import android.content.Context
import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.definition
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerViewModel
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorViewModel

class KoinModulesTest {
    // verifyAll checks each module alone, so one module including the rest lets a binding use another module's.
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `koin graph is complete`() {
        module { includes(appModules) }.verify(
            extraTypes = listOf(Context::class),
            injections =
                injectedParameters(
                    definition<TrainingEditorViewModel>(TrainingEditorKey::class),
                    definition<ExercisePickerViewModel>(ExercisePickerKey::class),
                ),
        )
    }
}
