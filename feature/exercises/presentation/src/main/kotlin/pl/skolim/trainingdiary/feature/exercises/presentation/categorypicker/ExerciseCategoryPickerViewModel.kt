package pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey

class ExerciseCategoryPickerViewModel(
    catalogue: ExerciseCatalogue,
    private val navigator: ExerciseCategoryPickerNavigator,
) : ViewModel() {
    val state: StateFlow<ExerciseCategoryPickerState> =
        MutableStateFlow(
            ExerciseCategoryPickerState(
                categories =
                    catalogue.categories().map { category ->
                        ExerciseCategoryUi(
                            id = category.id,
                            name = category.name,
                            exerciseCount = catalogue.exercises(category.id).size,
                        )
                    },
            ),
        ).asStateFlow()

    fun onAction(action: ExerciseCategoryPickerAction) {
        when (action) {
            ExerciseCategoryPickerAction.OnBackClick -> navigator.back(ExerciseCategoryPickerKey)
        }
    }
}
