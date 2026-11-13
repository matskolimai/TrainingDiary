package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

class SetEditorViewModel(
    private val key: SetEditorKey,
    private val catalogue: ExerciseCatalogue,
    private val navigator: SetEditorNavigator,
) : ViewModel() {
    val state: StateFlow<SetEditorState> = MutableStateFlow(SetEditorState()).asStateFlow()

    fun onAction(action: SetEditorAction) {
        when (action) {
            SetEditorAction.OnAddToTrainingClick -> addToTraining()
            SetEditorAction.OnBackClick -> navigator.back(key)
        }
    }

    private fun addToTraining() {
        val exercise = checkNotNull(catalogue.exercise(key.exerciseId))
        val trainingExercise = TrainingExercise(exercise = exercise, sets = state.value.sets)
        navigator.backToTrainingEditor(SetEditorResult.Added(trainingExercise))
    }
}
