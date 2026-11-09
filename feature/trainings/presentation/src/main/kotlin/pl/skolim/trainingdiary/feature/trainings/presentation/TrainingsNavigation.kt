package pl.skolim.trainingdiary.feature.trainings.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object TrainingListKey : NavKey

@Serializable
data class TrainingEditorKey(
    val trainingId: Long? = null, // null means add mode
) : NavKey

// The entries start navigating, and using backStack, in ticket 16.
@Suppress("UnusedParameter")
fun EntryProviderScope<NavKey>.trainingsEntries(backStack: NavBackStack<NavKey>) {
    // An empty start entry until ticket 12 adds the list; NavDisplay throws on a key without an entry.
    entry<TrainingListKey> { }
}
