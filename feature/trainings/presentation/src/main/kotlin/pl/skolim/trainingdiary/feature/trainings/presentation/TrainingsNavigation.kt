package pl.skolim.trainingdiary.feature.trainings.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorRoot
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListRoot
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorRoot
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.newTrainingExerciseSets

@Serializable
data object TrainingListKey : NavKey

@Serializable
data class TrainingEditorKey(
    val trainingId: Long? = null, // null means add mode
) : NavKey

@Serializable
data class SetEditorKey(
    val exerciseId: Long,
    val index: Int? = null, // null means add mode; otherwise the edited Training exercise's position
    val sets: List<ExerciseSet> = newTrainingExerciseSets,
) : NavKey

fun EntryProviderScope<NavKey>.trainingsEntries() {
    entry<TrainingListKey> { TrainingListRoot() }
    entry<TrainingEditorKey> { key -> TrainingEditorRoot(key = key) }
    entry<SetEditorKey> { key -> SetEditorRoot(key = key) }
}
