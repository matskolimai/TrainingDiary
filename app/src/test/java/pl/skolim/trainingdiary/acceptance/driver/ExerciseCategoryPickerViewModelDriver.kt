package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerAction

class ExerciseCategoryPickerViewModelDriver(
    private val app: HeadlessApp,
) : ExerciseCategoryPickerDriver {
    override fun assertListedInOrder(categories: List<Pair<String, Int>>) {
        val listed = app.exerciseCategoryPicker.state.value.categories
        assertThat(listed.map { it.name to it.exerciseCount }).containsExactlyElementsIn(categories).inOrder()
    }

    override fun leave() {
        app.exerciseCategoryPicker.onAction(ExerciseCategoryPickerAction.OnBackClick)
        app.settle()
        assertThat(app.topKey).isNotEqualTo(ExerciseCategoryPickerKey)
    }
}
