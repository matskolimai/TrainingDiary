// Named for the feature: the Exercise picker's navigator joins this one in the next screen's ticket.
@file:Suppress("MatchingDeclarationName", "ktlint:standard:filename")

package pl.skolim.trainingdiary

import pl.skolim.trainingdiary.feature.exercises.presentation.ExerciseCategoryPickerKey
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerNavigator

class AppExerciseCategoryPickerNavigator(
    private val navigator: AppNavigator,
) : ExerciseCategoryPickerNavigator {
    override fun back(key: ExerciseCategoryPickerKey) = navigator.pop(key)
}
