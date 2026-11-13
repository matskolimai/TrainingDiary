package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorAction

class SetEditorViewModelDriver(
    private val app: HeadlessApp,
) : SetEditorDriver {
    override fun assertSetsInOrder(sets: List<PlannedSet>) {
        val expected = sets.map { ExerciseSet(reps = it.reps, weightGrams = grams(it.weightKg)) }
        assertThat(app.setEditor.state.value.sets).containsExactlyElementsIn(expected).inOrder()
    }

    override fun tapAddToTraining() {
        app.setEditor.onAction(SetEditorAction.OnAddToTrainingClick)
        app.settle()
        assertThat(app.topKey is TrainingEditorKey).isTrue()
    }

    override fun leave() {
        app.setEditor.onAction(SetEditorAction.OnBackClick)
        app.settle()
        assertThat(app.topKey is SetEditorKey).isFalse()
    }

    private fun grams(kg: String): Int =
        kg
            .replace(',', '.')
            .toBigDecimal()
            .movePointRight(3)
            .intValueExact()
}
