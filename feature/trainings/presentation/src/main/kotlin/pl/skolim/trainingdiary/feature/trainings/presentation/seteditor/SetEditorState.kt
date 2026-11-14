package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet

data class SetEditorState(
    val sets: List<ExerciseSet> = List(NEW_SET_COUNT) { ExerciseSet(reps = NEW_SET_REPS, weightGrams = 0) },
) {
    val canRemoveSet: Boolean get() = sets.size > 1
    val canAddSet: Boolean get() = sets.size < MAX_SET_COUNT
}

private const val MAX_SET_COUNT = 10

// A new Training exercise starts with 3 Sets of 0 kg × 12.
private const val NEW_SET_COUNT = 3
private const val NEW_SET_REPS = 12
