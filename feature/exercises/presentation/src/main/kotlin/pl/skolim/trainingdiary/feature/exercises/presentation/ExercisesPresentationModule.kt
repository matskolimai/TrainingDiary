package pl.skolim.trainingdiary.feature.exercises.presentation

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.feature.exercises.presentation.categorypicker.ExerciseCategoryPickerViewModel
import pl.skolim.trainingdiary.feature.exercises.presentation.exercisepicker.ExercisePickerViewModel

val exercisesPresentationModule =
    module {
        viewModelOf(::ExerciseCategoryPickerViewModel)
        viewModelOf(::ExercisePickerViewModel)
    }
