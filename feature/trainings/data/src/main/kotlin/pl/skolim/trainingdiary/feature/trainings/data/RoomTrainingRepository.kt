package pl.skolim.trainingdiary.feature.trainings.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.skolim.trainingdiary.core.database.TrainingDao
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingRepository
import pl.skolim.trainingdiary.feature.trainings.domain.isValidTrainingName

internal class RoomTrainingRepository(
    private val dao: TrainingDao,
    private val catalogue: ExerciseCatalogue,
) : TrainingRepository {
    override fun observeTrainings(): Flow<List<Training>> =
        dao.observeAll().map { trainings -> trainings.map { it.toTraining(catalogue) } }

    override suspend fun getTraining(id: Long): Training? = dao.getById(id)?.toTraining(catalogue)

    override suspend fun addTraining(
        name: String,
        exercises: List<TrainingExercise>,
    ) {
        val training = Training(id = 0, name = name.toValidTrainingName(), exercises = exercises)
        dao.insert(training.toTrainingWithExercises())
    }

    override suspend fun updateTraining(training: Training) {
        dao.replace(training.copy(name = training.name.toValidTrainingName()).toTrainingWithExercises())
    }

    override suspend fun deleteTraining(id: Long) {
        dao.deleteById(id)
    }

    override suspend fun restoreTraining(training: Training) {
        dao.insert(training.toTrainingWithExercises())
    }
}

private fun String.toValidTrainingName(): String =
    trim().also { require(isValidTrainingName(it)) { "Blank training name" } }
