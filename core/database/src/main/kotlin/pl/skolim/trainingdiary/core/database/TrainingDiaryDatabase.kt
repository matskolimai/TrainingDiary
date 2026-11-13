package pl.skolim.trainingdiary.core.database

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [TrainingEntity::class, TrainingExerciseEntity::class, ExerciseSetEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class TrainingDiaryDatabase : RoomDatabase() {
    abstract fun trainingDao(): TrainingDao
}
