package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository

class TrainingListViewModel(
    repository: TrainingRepository,
) : ViewModel() {
    private val optionsSheetTrainingId = MutableStateFlow<Long?>(null)

    val state: StateFlow<TrainingListState> =
        combine(repository.observeTrainings(), optionsSheetTrainingId) { trainings, sheetTrainingId ->
            TrainingListState(trainings = trainings, isLoading = false, optionsSheetTrainingId = sheetTrainingId)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), TrainingListState())

    fun onAction(action: TrainingListAction) {
        when (action) {
            is TrainingListAction.OnOptionsClick -> optionsSheetTrainingId.value = action.trainingId
            TrainingListAction.OnOptionsDismiss -> optionsSheetTrainingId.value = null
            is TrainingListAction.OnEditClick -> optionsSheetTrainingId.value = null
            is TrainingListAction.OnDeleteClick -> optionsSheetTrainingId.value = null
            TrainingListAction.OnAddClick -> Unit
        }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L
