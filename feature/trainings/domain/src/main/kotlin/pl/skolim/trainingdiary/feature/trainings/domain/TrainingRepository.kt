package pl.skolim.trainingdiary.feature.trainings.domain

import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun observeTrainings(): Flow<List<Training>>

    suspend fun getTraining(id: Long): Training?

    suspend fun addTraining(name: String)

    suspend fun updateTraining(training: Training)

    suspend fun deleteTraining(id: Long)

    suspend fun restoreTraining(training: Training)
}
