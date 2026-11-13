package pl.skolim.trainingdiary.core.database

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "training_exercises",
    foreignKeys = [
        ForeignKey(
            entity = TrainingEntity::class,
            parentColumns = ["id"],
            childColumns = ["training_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("training_id")],
)
data class TrainingExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "training_id") val trainingId: Long = 0,
    @ColumnInfo(name = "exercise_id") val exerciseId: Long,
)
