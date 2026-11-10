package pl.skolim.trainingdiary.feature.trainings.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorRoot
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListRoot

@Serializable
data object TrainingListKey : NavKey

@Serializable
data class TrainingEditorKey(
    val trainingId: Long? = null, // null means add mode
) : NavKey

fun EntryProviderScope<NavKey>.trainingsEntries() {
    entry<TrainingListKey> { TrainingListRoot() }
    entry<TrainingEditorKey> { key -> TrainingEditorRoot(key = key) }
}
