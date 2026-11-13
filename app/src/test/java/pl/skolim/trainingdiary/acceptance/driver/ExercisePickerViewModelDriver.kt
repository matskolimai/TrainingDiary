package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerAction
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

class ExercisePickerViewModelDriver(
    private val app: HeadlessApp,
) : ExercisePickerDriver {
    override fun assertListedInOrder(exercises: List<String>) {
        val listed = app.exercisePicker.state.value.exercises
        assertThat(listed.map { it.name }).containsExactlyElementsIn(exercises).inOrder()
    }

    override fun tapExercise(name: String) {
        val exercise =
            app.exercisePicker.state.value.exercises
                .single { it.name == name }
        app.exercisePicker.onAction(ExercisePickerAction.OnExerciseClick(exercise.id))
        app.settle()
        assertThat(app.topKey).isEqualTo(SetEditorKey(exercise.id))
    }

    override fun leave() {
        app.exercisePicker.onAction(ExercisePickerAction.OnBackClick)
        app.settle()
        assertThat(app.topKey is ExercisePickerKey).isFalse()
    }
}
