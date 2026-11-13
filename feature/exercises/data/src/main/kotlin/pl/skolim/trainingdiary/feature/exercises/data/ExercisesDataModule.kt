package pl.skolim.trainingdiary.feature.exercises.data

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue

val exercisesDataModule =
    module {
        singleOf(::HardcodedExerciseCatalogue) { bind<ExerciseCatalogue>() }
    }
