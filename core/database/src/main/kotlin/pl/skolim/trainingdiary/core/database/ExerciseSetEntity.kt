package pl.skolim.trainingdiary.core.database

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(
    tableName = "exercise_sets",
    foreignKeys = [
        ForeignKey(
            entity = TrainingExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["training_exercise_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("training_exercise_id")],
)
data class ExerciseSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "training_exercise_id") val trainingExerciseId: Long = 0,
    val reps: Int,
    @ColumnInfo(name = "weight_grams") val weightGrams: Int,
)
