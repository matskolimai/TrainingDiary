package pl.skolim.trainingdiary.feature.trainings.domain

data class Training(
    val id: Long,
    val name: String,
    val exercises: List<TrainingExercise>,
)
