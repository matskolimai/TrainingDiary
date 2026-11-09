package pl.skolim.trainingdiary.feature.trainings.data

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository

val trainingsDataModule =
    module {
        singleOf(::RoomTrainingRepository) { bind<TrainingRepository>() }
    }
