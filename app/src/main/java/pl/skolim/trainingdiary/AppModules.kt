package pl.skolim.trainingdiary

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.core.database.coreDatabaseModule
import pl.skolim.trainingdiary.feature.trainings.data.trainingsDataModule
import pl.skolim.trainingdiary.feature.trainings.presentation.editor.TrainingEditorNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.list.TrainingListNavigator
import pl.skolim.trainingdiary.feature.trainings.presentation.trainingsPresentationModule

// One App<Screen>Navigator per screen navigator, all sharing the single AppNavigator.
val appNavigationModule =
    module {
        singleOf(::AppNavigator)
        singleOf(::AppTrainingListNavigator) { bind<TrainingListNavigator>() }
        singleOf(::AppTrainingEditorNavigator) { bind<TrainingEditorNavigator>() }
    }

val appModules =
    listOf(
        appNavigationModule,
        coreDatabaseModule,
        trainingsDataModule,
        trainingsPresentationModule,
    )
