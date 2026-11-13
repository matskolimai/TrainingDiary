package pl.skolim.trainingdiary.feature.trainings.data

import pl.skolim.trainingdiary.core.database.ExerciseSetEntity
import pl.skolim.trainingdiary.core.database.TrainingEntity
import pl.skolim.trainingdiary.core.database.TrainingExerciseEntity
import pl.skolim.trainingdiary.core.database.TrainingExerciseWithSets
import pl.skolim.trainingdiary.core.database.TrainingWithExercises
import pl.skolim.trainingdiary.core.domain.ExerciseCatalogue
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.domain.Training
import pl.skolim.trainingdiary.feature.trainings.domain.TrainingExercise

internal fun TrainingWithExercises.toTraining(catalogue: ExerciseCatalogue): Training =
    Training(
        id = training.id,
        name = training.name,
        exercises = exercises.sortedBy { it.trainingExercise.id }.map { it.toTrainingExercise(catalogue) },
    )

private fun TrainingExerciseWithSets.toTrainingExercise(catalogue: ExerciseCatalogue): TrainingExercise =
    TrainingExercise(
        exercise = checkNotNull(catalogue.exercise(trainingExercise.exerciseId)),
        sets = sets.sortedBy { it.id }.map { it.toExerciseSet() },
    )

internal fun Training.toTrainingWithExercises(): TrainingWithExercises =
    TrainingWithExercises(
        training = TrainingEntity(id = id, name = name),
        exercises =
            exercises.map { trainingExercise ->
                TrainingExerciseWithSets(
                    trainingExercise = TrainingExerciseEntity(exerciseId = trainingExercise.exercise.id),
                    sets = trainingExercise.sets.map { it.toExerciseSetEntity() },
                )
            },
    )

private fun ExerciseSetEntity.toExerciseSet(): ExerciseSet = ExerciseSet(reps = reps, weightGrams = weightGrams)

private fun ExerciseSet.toExerciseSetEntity(): ExerciseSetEntity =
    ExerciseSetEntity(reps = reps, weightGrams = weightGrams)
