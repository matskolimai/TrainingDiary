package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.skolim.trainingdiary.feature.trainings.presentation.SetEditorKey

class SetEditorViewModel(
    private val key: SetEditorKey,
    private val navigator: SetEditorNavigator,
) : ViewModel() {
    val state: StateFlow<SetEditorState> = MutableStateFlow(SetEditorState()).asStateFlow()

    fun onAction(action: SetEditorAction) {
        when (action) {
            SetEditorAction.OnBackClick -> navigator.back(key)
        }
    }
}
