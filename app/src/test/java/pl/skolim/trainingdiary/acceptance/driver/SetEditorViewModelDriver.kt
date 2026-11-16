package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorAction
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorField
import kotlin.time.Duration

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

    override fun addSet() {
        app.setEditor.onAction(SetEditorAction.OnIncrementSetsClick)
        app.settle()
    }

    override fun removeSet() {
        app.setEditor.onAction(SetEditorAction.OnDecrementSetsClick)
        app.settle()
    }

    override fun assertCount(
        count: Int,
        canRemove: Boolean,
        canAdd: Boolean,
    ) {
        assertThat(app.setEditor.state.value.sets).hasSize(count)
        assertThat(app.setEditor.state.value.canRemoveSet).isEqualTo(canRemove)
        assertThat(app.setEditor.state.value.canAddSet).isEqualTo(canAdd)
    }

    override fun focusWeight(set: Int) = focus(set, SetEditorField.WEIGHT)

    override fun focusReps(set: Int) = focus(set, SetEditorField.REPS)

    private fun focus(
        set: Int,
        field: SetEditorField,
    ) {
        app.setEditor.onAction(SetEditorAction.OnValueClick(set - 1, field))
        app.settle()
    }

    override fun assertFocusedReps(set: Int) = assertFocused(set, SetEditorField.REPS)

    override fun assertFocusedWeight(set: Int) = assertFocused(set, SetEditorField.WEIGHT)

    private fun assertFocused(
        set: Int,
        field: SetEditorField,
    ) {
        assertThat(app.setEditor.state.value.focusedSetIndex).isEqualTo(set - 1)
        assertThat(app.setEditor.state.value.focusedField).isEqualTo(field)
    }

    override fun increaseValue(times: Int) {
        repeat(times) { app.setEditor.onAction(SetEditorAction.OnIncrementValueClick) }
        app.settle()
    }

    override fun decreaseValue(times: Int) {
        repeat(times) { app.setEditor.onAction(SetEditorAction.OnDecrementValueClick) }
        app.settle()
    }

    override fun holdIncreaseValue(duration: Duration): Unit =
        throw CannotStageException("holdIncreaseValue", Channel.VIEW_MODEL)

    override fun assertWeightAtLeast(
        set: Int,
        weightKg: String,
    ) {
        val weightGrams =
            app.setEditor.state.value.sets[set - 1]
                .weightGrams
        assertThat(weightGrams).isAtLeast(grams(weightKg))
    }

    override fun assertValueControls(
        set: Int,
        canDecrease: Boolean,
        canIncrease: Boolean,
    ) {
        assertThat(app.setEditor.state.value.focusedSetIndex).isEqualTo(set - 1)
        assertThat(app.setEditor.state.value.canDecrementValue).isEqualTo(canDecrease)
        assertThat(app.setEditor.state.value.canIncrementValue).isEqualTo(canIncrease)
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
