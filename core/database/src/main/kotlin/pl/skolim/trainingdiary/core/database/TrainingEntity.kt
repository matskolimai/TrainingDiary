package pl.skolim.trainingdiary.core.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)
