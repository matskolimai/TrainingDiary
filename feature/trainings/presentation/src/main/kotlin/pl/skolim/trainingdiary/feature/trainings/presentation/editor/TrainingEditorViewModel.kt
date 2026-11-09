package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

// The repository starts saving in ticket 18 and loading in ticket 21.
@Suppress("UnusedPrivateProperty")
internal class TrainingEditorViewModel(
    key: TrainingEditorKey,
    private val repository: TrainingRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingEditorState(isEditMode = key.trainingId != null))
    val state: StateFlow<TrainingEditorState> = _state.asStateFlow()

    fun onAction(action: TrainingEditorAction) {
        when (action) {
            is TrainingEditorAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            TrainingEditorAction.OnBackClick -> Unit
        }
    }
}
