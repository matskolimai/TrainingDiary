package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorAction

class TrainingEditorViewModelDriver(
    private val app: HeadlessApp,
) : TrainingEditorDriver {
    override fun typeName(name: String) {
        val viewModel = app.trainingEditor
        viewModel.onAction(TrainingEditorAction.OnNameChange(viewModel.state.value.name + name))
        app.settle()
    }

    override fun tapSave() {
        app.trainingEditor.onAction(TrainingEditorAction.OnSaveClick)
        app.settle()
        assertThat(app.topKey is TrainingEditorKey).isFalse()
    }

    override fun assertEmptyForAdding(): Unit = TODO("ticket 05")

    override fun assertShowingForEditing(name: String): Unit = TODO("ticket 05")

    override fun assertNameFieldFocused(): Unit = TODO("ticket 05")

    override fun replaceName(name: String): Unit = TODO("ticket 05")

    override fun assertCannotSave(): Unit = TODO("ticket 05")

    override fun leave(): Unit = TODO("ticket 05")
}
