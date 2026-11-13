package pl.skolim.trainingdiary.feature.exercises.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerRoot
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerRoot

@Serializable
data object ExerciseCategoryPickerKey : NavKey

@Serializable
data class ExercisePickerKey(
    val categoryId: Long,
) : NavKey

fun EntryProviderScope<NavKey>.exercisesEntries() {
    entry<ExerciseCategoryPickerKey> { ExerciseCategoryPickerRoot() }
    entry<ExercisePickerKey> { key -> ExercisePickerRoot(key = key) }
}
