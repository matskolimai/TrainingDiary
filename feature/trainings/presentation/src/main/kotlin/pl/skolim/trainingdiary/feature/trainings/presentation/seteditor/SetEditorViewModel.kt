package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

class SetEditorViewModel(
    private val key: SetEditorKey,
    catalogue: ExerciseCatalogue,
    private val navigator: SetEditorNavigator,
) : ViewModel() {
    private val exercise = checkNotNull(catalogue.exercise(key.exerciseId))
    private val _state = MutableStateFlow(SetEditorState(isTimed = exercise.isTimed))
    val state: StateFlow<SetEditorState> = _state.asStateFlow()

    fun onAction(action: SetEditorAction) {
        when (action) {
            SetEditorAction.OnAddToTrainingClick -> {
                addToTraining()
            }

            SetEditorAction.OnDecrementSetsClick -> {
                _state.update {
                    if (it.canRemoveSet) {
                        it.copy(
                            sets = it.sets.dropLast(1),
                            focusedSetIndex = it.focusedSetIndex.coerceAtMost(it.sets.lastIndex - 1),
                        )
                    } else {
                        it
                    }
                }
            }

            SetEditorAction.OnIncrementSetsClick -> {
                _state.update { if (it.canAddSet) it.copy(sets = it.sets + it.sets.last()) else it }
            }

            is SetEditorAction.OnValueClick -> {
                _state.update { it.copy(focusedSetIndex = action.setIndex, focusedField = action.field) }
            }

            SetEditorAction.OnIncrementValueClick -> {
                changeValue(1)
            }

            SetEditorAction.OnDecrementValueClick -> {
                changeValue(-1)
            }

            SetEditorAction.OnBackClick -> {
                navigator.back(key)
            }
        }
    }

    private fun changeValue(direction: Int) {
        _state.update { state ->
            state.copy(
                sets =
                    state.sets.mapIndexed { index, set ->
                        if (index == state.focusedSetIndex) set.stepped(state.focusedField, direction) else set
                    },
            )
        }
    }

    private fun addToTraining() {
        val trainingExercise = TrainingExercise(exercise = exercise, sets = state.value.sets)
        navigator.backToTrainingEditor(SetEditorResult.Added(trainingExercise))
    }
}
