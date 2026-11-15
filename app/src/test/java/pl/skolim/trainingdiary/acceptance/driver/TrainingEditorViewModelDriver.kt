package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorAction

class TrainingEditorViewModelDriver(
    private val app: HeadlessApp,
) : TrainingEditorDriver {
    override fun typeName(name: String) {
        val viewModel = app.trainingEditor
        act(TrainingEditorAction.OnNameChange(viewModel.state.value.name + name))
    }

    override fun tapAddExercise() {
        act(TrainingEditorAction.OnAddExerciseClick)
        assertThat(app.topKey).isEqualTo(ExerciseCategoryPickerKey)
    }

    override fun tapExercise(position: Int) {
        act(TrainingEditorAction.OnExerciseClick(position - 1))
        assertThat(app.topKey is SetEditorKey).isTrue()
    }

    override fun tapSave() {
        act(TrainingEditorAction.OnSaveClick)
        assertThat(app.topKey is TrainingEditorKey).isFalse()
    }

    override fun tapSaveTwiceQuickly() {
        val viewModel = app.trainingEditor
        viewModel.onAction(TrainingEditorAction.OnSaveClick)
        viewModel.onAction(TrainingEditorAction.OnSaveClick)
        app.settle()
        assertThat(app.topKey is TrainingEditorKey).isFalse()
    }

    override fun assertEmptyForAdding() {
        val state = app.trainingEditor.state.value
        assertThat(state.isEditMode).isFalse()
        assertThat(state.isLoading).isFalse()
        assertThat(state.name).isEmpty()
    }

    override fun assertShowingForEditing(name: String) {
        val state = app.trainingEditor.state.value
        assertThat(state.isEditMode).isTrue()
        assertThat(state.isLoading).isFalse()
        assertThat(state.name).isEqualTo(name)
    }

    override fun assertExercisesInOrder(exercises: List<PlannedTrainingExercise>) {
        val actual =
            app.trainingEditor.state.value.exercises
                .map {
                    PlannedTrainingExercise(
                        name = it.exercise.name,
                        category = it.exercise.category.name,
                        sets = it.sets.size,
                    )
                }
        assertThat(actual).containsExactlyElementsIn(exercises).inOrder()
    }

    override fun assertNameFieldFocused(): Unit =
        throw CannotStageException("assertNameFieldFocused", Channel.VIEW_MODEL)

    override fun replaceName(name: String) = act(TrainingEditorAction.OnNameChange(name))

    override fun assertCannotSave() {
        assertThat(app.trainingEditor.state.value.canSave).isFalse()
    }

    override fun leave() {
        act(TrainingEditorAction.OnBackClick)
        assertThat(app.topKey is TrainingEditorKey).isFalse()
    }

    private fun act(action: TrainingEditorAction) {
        app.trainingEditor.onAction(action)
        app.settle()
    }
}
