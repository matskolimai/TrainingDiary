package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet

data class SetEditorState(
    val focusedSetIndex: Int = 0,
    val focusedField: SetEditorField = SetEditorField.WEIGHT,
    val sets: List<ExerciseSet> = List(NEW_SET_COUNT) { ExerciseSet(reps = NEW_SET_REPS, weightGrams = 0) },
) {
    val canDecrementValue: Boolean get() = canStep(-1)
    val canIncrementValue: Boolean get() = canStep(1)
    val canRemoveSet: Boolean get() = sets.size > 1
    val canAddSet: Boolean get() = sets.size < MAX_SET_COUNT

    private fun canStep(direction: Int): Boolean {
        val set = sets[focusedSetIndex]
        return set.stepped(focusedField, direction) != set
    }
}

/** This Set with [field] one step in [direction] (1 or -1), held within its limits. */
internal fun ExerciseSet.stepped(
    field: SetEditorField,
    direction: Int,
): ExerciseSet =
    when (field) {
        SetEditorField.WEIGHT -> {
            copy(
                weightGrams =
                    (weightGrams + direction * WEIGHT_STEP_GRAMS).coerceIn(MIN_WEIGHT_GRAMS, MAX_WEIGHT_GRAMS),
            )
        }

        SetEditorField.REPS -> {
            copy(reps = (reps + direction).coerceIn(MIN_REPS, MAX_REPS))
        }
    }

private const val MAX_SET_COUNT = 10

// A new Training exercise starts with 3 Sets of 0 kg × 12.
private const val NEW_SET_COUNT = 3
private const val NEW_SET_REPS = 12

private const val MIN_REPS = 1
private const val MAX_REPS = 999

private const val WEIGHT_STEP_GRAMS = 1_500
private const val MIN_WEIGHT_GRAMS = 0
private const val MAX_WEIGHT_GRAMS = 300_000
