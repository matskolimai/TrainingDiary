package pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.exercises.presentation.ExercisePickerKey

class ExercisePickerViewModel(
    private val key: ExercisePickerKey,
    catalogue: ExerciseCatalogue,
    private val navigator: ExercisePickerNavigator,
) : ViewModel() {
    val state: StateFlow<ExercisePickerState> =
        MutableStateFlow(ExercisePickerState(exercises = catalogue.exercises(key.categoryId))).asStateFlow()

    fun onAction(action: ExercisePickerAction) {
        when (action) {
            ExercisePickerAction.OnBackClick -> navigator.back(key)
        }
    }
}
