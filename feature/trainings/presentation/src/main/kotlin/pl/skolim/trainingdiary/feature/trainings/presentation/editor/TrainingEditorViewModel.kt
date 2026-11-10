package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

class TrainingEditorViewModel(
    key: TrainingEditorKey,
    private val repository: TrainingRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingEditorState(isEditMode = key.trainingId != null))
    val state: StateFlow<TrainingEditorState> = _state.asStateFlow()

    private val _events = Channel<TrainingEditorEvent>()
    val events: Flow<TrainingEditorEvent> = _events.receiveAsFlow()

    fun onAction(action: TrainingEditorAction) {
        when (action) {
            is TrainingEditorAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            TrainingEditorAction.OnSaveClick -> save()
            TrainingEditorAction.OnBackClick -> Unit
        }
    }

    // isSaving stays set after saving: the editor closes, and a second tap must not save again.
    private fun save() {
        val state = _state.value
        if (!state.canSave) return
        _state.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            repository.addTraining(state.name)
            _events.send(TrainingEditorEvent.TrainingSaved)
        }
    }
}
