package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository

class TrainingListViewModel(
    private val repository: TrainingRepository,
) : ViewModel() {
    private val optionsSheetTrainingId = MutableStateFlow<Long?>(null)

    val state: StateFlow<TrainingListState> =
        combine(repository.observeTrainings(), optionsSheetTrainingId) { trainings, sheetTrainingId ->
            TrainingListState(trainings = trainings, isLoading = false, optionsSheetTrainingId = sheetTrainingId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), TrainingListState())

    private val _events = Channel<TrainingListEvent>()
    val events: Flow<TrainingListEvent> = _events.receiveAsFlow()

    // Kept so that undo can put the Training back with its id; a newer delete replaces it.
    private var deletedTraining: Training? = null

    fun onAction(action: TrainingListAction) {
        when (action) {
            is TrainingListAction.OnOptionsClick -> optionsSheetTrainingId.value = action.trainingId
            TrainingListAction.OnOptionsDismiss -> optionsSheetTrainingId.value = null
            is TrainingListAction.OnEditClick -> optionsSheetTrainingId.value = null
            is TrainingListAction.OnDeleteClick -> delete(action.trainingId)
            TrainingListAction.OnUndoClick -> undo()
            TrainingListAction.OnAddClick -> Unit
        }
    }

    private fun delete(trainingId: Long) {
        optionsSheetTrainingId.value = null
        viewModelScope.launch {
            val training = repository.getTraining(trainingId) ?: return@launch
            repository.deleteTraining(trainingId)
            deletedTraining = training
            _events.send(TrainingListEvent.TrainingDeleted(training.name))
        }
    }

    private fun undo() {
        val training = deletedTraining ?: return
        deletedTraining = null
        viewModelScope.launch { repository.restoreTraining(training) }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L
