package pl.skolim.trainingdiary.feature.trainings.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository

internal class TrainingListViewModel(
    repository: TrainingRepository,
) : ViewModel() {
    val state: StateFlow<TrainingListState> =
        repository
            .observeTrainings()
            .map { trainings -> TrainingListState(trainings = trainings, isLoading = false) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), TrainingListState())

    fun onAction(action: TrainingListAction) {
        when (action) {
            TrainingListAction.OnAddClick -> Unit
        }
    }
}

private const val STOP_TIMEOUT_MILLIS = 5_000L
