package pl.skolim.trainingdiary.core.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Transaction
    @Query("SELECT * FROM trainings ORDER BY id")
    fun observeAll(): Flow<List<TrainingWithExercises>>

    @Transaction
    @Query("SELECT * FROM trainings WHERE id = :id")
    suspend fun getById(id: Long): TrainingWithExercises?

    /**
     * Inserts a Training with its children, filling in the children's foreign keys. A row with id 0 gets a new id; a
     * row with an existing id keeps it, which is how a deleted Training is restored.
     */
    @Transaction
    suspend fun insert(training: TrainingWithExercises) {
        val trainingId = insertTraining(training.training)
        insertChildren(trainingId, training.exercises)
    }

    /** Updates the Training's name and replaces its children. */
    @Transaction
    suspend fun replace(training: TrainingWithExercises) {
        updateTraining(training.training)
        deleteTrainingExercises(training.training.id)
        insertChildren(training.training.id, training.exercises)
    }

    @Query("DELETE FROM trainings WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Insert
    suspend fun insertTraining(training: TrainingEntity): Long

    @Insert
    suspend fun insertTrainingExercise(trainingExercise: TrainingExerciseEntity): Long

    @Insert
    suspend fun insertExerciseSets(sets: List<ExerciseSetEntity>)

    @Update
    suspend fun updateTraining(training: TrainingEntity)

    @Query("DELETE FROM training_exercises WHERE training_id = :trainingId")
    suspend fun deleteTrainingExercises(trainingId: Long)

    private suspend fun insertChildren(
        trainingId: Long,
        exercises: List<TrainingExerciseWithSets>,
    ) {
        exercises.forEach { exercise ->
            val trainingExerciseId =
                insertTrainingExercise(exercise.trainingExercise.copy(trainingId = trainingId))
            insertExerciseSets(exercise.sets.map { it.copy(trainingExerciseId = trainingExerciseId) })
        }
    }
}
