package pl.skolim.trainingdiary.feature.trainings.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.core.presentation.pop
import pl.skolim.trainingdiary.core.presentation.push
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorRoot
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListRoot

@Serializable
data object TrainingListKey : NavKey

@Serializable
data class TrainingEditorKey(
    val trainingId: Long? = null, // null means add mode
) : NavKey

fun EntryProviderScope<NavKey>.trainingsEntries(backStack: NavBackStack<NavKey>) {
    entry<TrainingListKey> {
        TrainingListRoot(
            onAddTraining = { backStack.push(TrainingEditorKey()) },
            onEditTraining = { trainingId -> backStack.push(TrainingEditorKey(trainingId)) },
        )
    }
    entry<TrainingEditorKey> { key ->
        TrainingEditorRoot(key = key, onBack = { backStack.pop(key) })
    }
}
