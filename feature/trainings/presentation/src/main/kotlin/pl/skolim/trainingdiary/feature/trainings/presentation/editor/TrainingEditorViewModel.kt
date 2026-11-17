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
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorResult

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

    // Collected for the ViewModel's whole life, so a result lands while the Set editor still covers this Screen.
    init {
        viewModelScope.launch {
            navigator.setEditorResults.collect { result ->
                when (result) {
                    is SetEditorResult.Added -> {
                        _state.update { it.copy(exercises = it.exercises + result.trainingExercise) }
                    }

                    is SetEditorResult.Replaced -> {
                        _state.update { state ->
                            state.copy(
                                exercises =
                                    state.exercises.mapIndexed { index, trainingExercise ->
                                        if (index == result.index) result.trainingExercise else trainingExercise
                                    },
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: TrainingEditorAction) {
        when (action) {
            is TrainingEditorAction.OnNameChange -> _state.update { it.copy(name = action.name) }
            TrainingEditorAction.OnAddExerciseClick -> navigator.openExercisePicker()
            is TrainingEditorAction.OnExerciseClick -> openSetEditor(action.index)
            is TrainingEditorAction.OnExerciseOptionsClick -> openOptionsSheet(action.index)
            TrainingEditorAction.OnExerciseOptionsDismiss -> dismissOptionsSheet()
            TrainingEditorAction.OnEditExerciseClick -> editExercise()
            TrainingEditorAction.OnDeleteExerciseClick -> deleteExercise()
            TrainingEditorAction.OnSaveClick -> save()
            TrainingEditorAction.OnBackClick -> navigator.back(key)
        }
    }

    private fun openOptionsSheet(index: Int) {
        _state.update { it.copy(optionsSheetIndex = index) }
    }

    private fun dismissOptionsSheet() {
        _state.update { it.copy(optionsSheetIndex = null) }
    }

    // Each sheet action reads the open sheet's index and closes it, so a second tap before the sheet leaves is ignored.
    private fun editExercise() {
        val index = _state.value.optionsSheetIndex ?: return
        dismissOptionsSheet()
        openSetEditor(index)
    }

    // No undo: the removal is stored only by „Zapisz”, so leaving without saving brings it back.
    private fun deleteExercise() {
        val index = _state.value.optionsSheetIndex ?: return
        _state.update { state ->
            state.copy(
                exercises = state.exercises.filterIndexed { i, _ -> i != index },
                optionsSheetIndex = null,
            )
        }
    }

    private fun openSetEditor(index: Int) {
        val trainingExercise = _state.value.exercises[index]
        navigator.openSetEditor(exerciseId = trainingExercise.exercise.id, index = index, sets = trainingExercise.sets)
    }

    private suspend fun load() {
        if (hasLoaded || trainingId == null) return
        hasLoaded = true
        val training = checkNotNull(repository.getTraining(trainingId))
        _state.update { it.copy(name = training.name, exercises = training.exercises, isLoading = false) }
    }

    // isSaving stays set after saving: the editor closes, and a second tap must not save again.
    private fun save() {
        val state = _state.value
        if (!state.canSave) return
        _state.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            if (trainingId == null) {
                repository.addTraining(state.name, exercises = state.exercises)
            } else {
                repository.updateTraining(Training(id = trainingId, name = state.name, exercises = state.exercises))
            }
            navigator.back(key)
        }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L
