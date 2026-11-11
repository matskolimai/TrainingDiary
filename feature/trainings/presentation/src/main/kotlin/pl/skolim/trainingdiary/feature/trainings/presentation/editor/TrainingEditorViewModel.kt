package pl.skolim.trainingdiary.feature.trainings.presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingEditorKey

class TrainingEditorViewModel(
    private val key: TrainingEditorKey,
    private val repository: TrainingRepository,
    private val navigator: TrainingEditorNavigator,
) : ViewModel() {
    private val trainingId = key.trainingId

    private val _state =
        MutableStateFlow(TrainingEditorState(isEditMode = trainingId != null, isLoading = trainingId != null))

    // onStart runs again when collection restarts after the timeout; the guard keeps it from overwriting typed input.
    private var hasLoaded = false

    val state: StateFlow<TrainingEditorState> =
        _state
            .onStart { load() }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), _state.value)

    fun onAction(action: TrainingEditorAction) {
        when (action) {
            is TrainingEditorAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            TrainingEditorAction.OnSaveClick -> save()
            TrainingEditorAction.OnBackClick -> navigator.back(key)
        }
    }

    private suspend fun load() {
        if (hasLoaded || trainingId == null) return
        hasLoaded = true
        val training = checkNotNull(repository.getTraining(trainingId))
        _state.update { it.copy(name = training.name, isLoading = false) }
    }

    // isSaving stays set after saving: the editor closes, and a second tap must not save again.
    private fun save() {
        val state = _state.value
        if (!state.canSave) return
        _state.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            if (trainingId == null) {
                repository.addTraining(state.name)
            } else {
                repository.updateTraining(Training(id = trainingId, name = state.name))
            }
            navigator.back(key)
        }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L
