package pl.skolim.trainingdiary.feature.trainings.presentation

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorViewModel
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListViewModel
import pl.skolim.trainingdiary.feature.trainings.presentation.seteditor.SetEditorViewModel

val trainingsPresentationModule =
    module {
        viewModelOf(::TrainingListViewModel)
        viewModelOf(::TrainingEditorViewModel)
        viewModelOf(::SetEditorViewModel)
    }
