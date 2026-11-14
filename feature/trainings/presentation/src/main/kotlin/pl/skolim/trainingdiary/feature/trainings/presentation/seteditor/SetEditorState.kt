package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet

data class SetEditorState(
    val focusedSetIndex: Int = 0,
    val focusedField: SetEditorField = SetEditorField.WEIGHT,
    val sets: List<ExerciseSet> = List(NEW_SET_COUNT) { ExerciseSet(reps = NEW_SET_REPS, weightGrams = 0) },
) {
    val canDecrementValue: Boolean
        get() = focusedField == SetEditorField.WEIGHT || sets[focusedSetIndex].reps > MIN_REPS
    val canIncrementValue: Boolean
        get() = focusedField == SetEditorField.WEIGHT || sets[focusedSetIndex].reps < MAX_REPS
    val canRemoveSet: Boolean get() = sets.size > 1
    val canAddSet: Boolean get() = sets.size < MAX_SET_COUNT
}

private const val MAX_SET_COUNT = 10

// A new Training exercise starts with 3 Sets of 0 kg × 12.
private const val NEW_SET_COUNT = 3
private const val NEW_SET_REPS = 12

internal const val MIN_REPS = 1
internal const val MAX_REPS = 999
