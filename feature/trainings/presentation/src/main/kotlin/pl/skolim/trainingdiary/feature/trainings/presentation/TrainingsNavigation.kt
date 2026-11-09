package pl.skolim.trainingdiary.feature.trainings.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListRoot

@Serializable
data object TrainingListKey : NavKey

@Serializable
data class TrainingEditorKey(
    val trainingId: Long? = null, // null means add mode
) : NavKey

// The entries start navigating, and using backStack, in ticket 16.
@Suppress("UnusedParameter")
fun EntryProviderScope<NavKey>.trainingsEntries(backStack: NavBackStack<NavKey>) {
    entry<TrainingListKey> { TrainingListRoot() }
}
