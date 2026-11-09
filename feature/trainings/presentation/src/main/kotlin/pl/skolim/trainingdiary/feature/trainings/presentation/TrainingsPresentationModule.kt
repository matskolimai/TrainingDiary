package pl.skolim.trainingdiary.feature.trainings.presentation

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListViewModel

val trainingsPresentationModule =
    module {
        viewModelOf(::TrainingListViewModel)
    }
