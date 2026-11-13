package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerAction

class ExercisePickerViewModelDriver(
    private val app: HeadlessApp,
) : ExercisePickerDriver {
    override fun assertListedInOrder(exercises: List<String>) {
        val listed = app.exercisePicker.state.value.exercises
        assertThat(listed.map { it.name }).containsExactlyElementsIn(exercises).inOrder()
    }

    override fun leave() {
        app.exercisePicker.onAction(ExercisePickerAction.OnBackClick)
        app.settle()
        assertThat(app.topKey is ExercisePickerKey).isFalse()
    }
}
