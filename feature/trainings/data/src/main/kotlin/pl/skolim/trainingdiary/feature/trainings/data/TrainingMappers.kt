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
        sets = sets.sortedBy { it.id }.map { ExerciseSet(reps = it.reps, weightGrams = it.weightGrams) },
    )

// Children get new ids, so id order is the list's order.
internal fun Training.toTrainingWithExercises(): TrainingWithExercises =
    TrainingWithExercises(
        training = TrainingEntity(id = id, name = name),
        exercises =
            exercises.map { trainingExercise ->
                TrainingExerciseWithSets(
                    trainingExercise = TrainingExerciseEntity(exerciseId = trainingExercise.exercise.id),
                    sets =
                        trainingExercise.sets.map {
                            ExerciseSetEntity(
                                reps = it.reps,
                                weightGrams = it.weightGrams,
                            )
                        },
                )
            },
    )
