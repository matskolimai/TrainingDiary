package pl.skolim.trainingdiary

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import pl.skolim.trainingdiary.core.presentation.ObserveAsEvents
import pl.skolim.trainingdiary.feature.trainings.presentation.TrainingListKey
import pl.skolim.trainingdiary.feature.trainings.presentation.trainingsEntries

@Composable
fun AppNavDisplay(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(TrainingListKey)
    ObserveAsEvents(navigator.requests, backStack) { request ->
        when (request) {
            is NavRequest.Push -> backStack.push(request.key)
            is NavRequest.Pop -> backStack.pop(request.key)
        }
    }
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider =
            entryProvider {
                trainingsEntries()
            },
    )
}
