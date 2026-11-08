package pl.skolim.trainingdiary.feature.trainings.data

import pl.skolim.trainingdiary.core.database.TrainingEntity
import pl.skolim.trainingdiary.feature.trainings.domain.Training

internal fun TrainingEntity.toTraining(): Training = Training(id = id, name = name)

internal fun Training.toTrainingEntity(): TrainingEntity = TrainingEntity(id = id, name = name)
