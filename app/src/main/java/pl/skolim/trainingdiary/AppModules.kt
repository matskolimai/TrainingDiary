package pl.skolim.trainingdiary

import pl.skolim.trainingdiary.core.database.coreDatabaseModule
import pl.skolim.trainingdiary.feature.trainings.data.trainingsDataModule
import pl.skolim.trainingdiary.feature.trainings.presentation.trainingsPresentationModule

val appModules =
    listOf(
        coreDatabaseModule,
        trainingsDataModule,
        trainingsPresentationModule,
    )
