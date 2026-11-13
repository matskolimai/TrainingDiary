// Named for the feature: the Exercise picker's key joins this one in the next screen's ticket.
@file:Suppress("MatchingDeclarationName")

package pl.skolim.trainingdiary.feature.exercises.presentation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerRoot

@Serializable
data object ExerciseCategoryPickerKey : NavKey

fun EntryProviderScope<NavKey>.exercisesEntries() {
    entry<ExerciseCategoryPickerKey> { ExerciseCategoryPickerRoot() }
}
