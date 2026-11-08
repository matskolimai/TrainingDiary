package pl.skolim.trainingdiary.feature.trainings.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.skolim.trainingdiary.core.database.TrainingDao
import pl.skolim.trainingdiary.core.database.TrainingEntity
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository

internal class RoomTrainingRepository(
    private val dao: TrainingDao,
) : TrainingRepository {
    override fun observeTrainings(): Flow<List<Training>> =
        dao.observeAll().map { entities -> entities.map { it.toTraining() } }

    override suspend fun getTraining(id: Long): Training? = dao.getById(id)?.toTraining()

    override suspend fun addTraining(name: String) {
        dao.insert(TrainingEntity(name = name.toValidTrainingName()))
    }

    override suspend fun updateTraining(training: Training) {
        dao.update(training.copy(name = training.name.toValidTrainingName()).toTrainingEntity())
    }

    override suspend fun deleteTraining(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun restoreTraining(training: Training) {
        dao.insert(training.toTrainingEntity())
    }
}

// Temporary safety net until ticket 18 adds isValidTrainingName to the domain.
private fun String.toValidTrainingName(): String = trim().also { require(it.isNotBlank()) { "Blank training name" } }
