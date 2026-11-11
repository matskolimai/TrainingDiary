package pl.skolim.trainingdiary.acceptance.driver

import com.google.common.truth.Truth.assertThat
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListAction
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListEvent

class TrainingListViewModelDriver(
    private val app: HeadlessApp,
) : TrainingListDriver {
    override fun assertEmptyList() {
        assertThat(app.trainingList.state.value.isLoading).isFalse()
        assertThat(app.trainingList.state.value.trainings).isEmpty()
    }

    override fun assertListedInOrder(names: List<String>) {
        assertThat(app.trainingList.state.value.isLoading).isFalse()
        assertThat(
            app.trainingList.state.value.trainings
                .map { it.name },
        ).containsExactlyElementsIn(names).inOrder()
    }

    override fun openNewTraining() {
        act(TrainingListAction.OnAddClick)
        assertThat(app.topKey).isEqualTo(TrainingEditorKey())
    }

    override fun openTrainingForEditing(name: String) {
        val id = trainingId(name)
        act(TrainingListAction.OnOptionsClick(id))
        act(TrainingListAction.OnEditClick(id))
        assertThat(app.topKey).isEqualTo(TrainingEditorKey(id))
    }

    override fun deleteTraining(name: String) {
        val id = trainingId(name)
        act(TrainingListAction.OnOptionsClick(id))
        act(TrainingListAction.OnDeleteClick(id))
    }

    override fun assertUndoDeleteOffered(name: String) {
        assertThat(app.lastTrainingListEvent).isEqualTo(TrainingListEvent.TrainingDeleted(name))
    }

    override fun undoDelete() = act(TrainingListAction.OnUndoClick)

    private fun trainingId(name: String): Long =
        app.trainingList.state.value.trainings
            .single { it.name == name }
            .id

    private fun act(action: TrainingListAction) {
        app.trainingList.onAction(action)
        app.settle()
    }
}
