package pl.skolim.trainingdiary.core.database

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Query("SELECT * FROM trainings ORDER BY id")
    fun observeAll(): Flow<List<TrainingEntity>>

    @Query("SELECT * FROM trainings WHERE id = :id")
    suspend fun getById(id: Long): TrainingEntity?

    /** An entity with id 0 gets a new id; an entity with an existing id keeps it, which restores a deleted row. */
    @Insert
    suspend fun insert(training: TrainingEntity)

    @Update
    suspend fun update(training: TrainingEntity)

    @Query("DELETE FROM trainings WHERE id = :id")
    suspend fun deleteById(id: Long)
}
