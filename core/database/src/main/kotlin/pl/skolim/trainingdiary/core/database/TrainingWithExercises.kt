package pl.skolim.trainingdiary.core.database

import androidx.room3.Embedded
import androidx.room3.Relation

// Room does not order @Relation children; readers sort them by id, which is the order they were added in.
data class TrainingWithExercises(
    @Embedded val training: TrainingEntity,
    @Relation(entity = TrainingExerciseEntity::class, parentColumns = ["id"], entityColumns = ["training_id"])
    val exercises: List<TrainingExerciseWithSets>,
)

data class TrainingExerciseWithSets(
    @Embedded val trainingExercise: TrainingExerciseEntity,
    @Relation(parentColumns = ["id"], entityColumns = ["training_exercise_id"])
    val sets: List<ExerciseSetEntity>,
)
