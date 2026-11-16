package pl.skolim.trainingdiary.feature.trainings.presentation.seteditor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.skolim.trainingdiary.core.designsystem.AppIcons
import pl.skolim.trainingdiary.core.designsystem.AppListRow
import pl.skolim.trainingdiary.core.designsystem.AppNumberCircle
import pl.skolim.trainingdiary.core.designsystem.AppRepeatIconButton
import pl.skolim.trainingdiary.core.designsystem.AppValuePill
import pl.skolim.trainingdiary.core.designsystem.Spacing
import pl.skolim.trainingdiary.feature.trainings.domain.ExerciseSet
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import java.math.BigDecimal

@Suppress("LongParameterList") // A Set card shows its number, both values, focus and the focused row's controls.
@Composable
internal fun SetCard(
    number: Int,
    set: ExerciseSet,
    isTimed: Boolean,
    focusedField: SetEditorField?,
    onFocus: (SetEditorField) -> Unit,
    trailing: (@Composable () -> Unit)?,
) {
    AppListRow(
        leading = { AppNumberCircle(number = number) },
        trailing = trailing,
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.small)) {
            AppValuePill(
                text = stringResource(R.string.set_editor_weight, formatWeight(set.weightGrams)),
                focused = focusedField == SetEditorField.WEIGHT,
                onClick = { onFocus(SetEditorField.WEIGHT) },
            )
            AppValuePill(
                text = stringResource(if (isTimed) R.string.set_editor_seconds else R.string.set_editor_reps, set.reps),
                focused = focusedField == SetEditorField.REPS,
                onClick = { onFocus(SetEditorField.REPS) },
            )
        }
    }
}

@Composable
internal fun ValueControls(
    canDecrement: Boolean,
    canIncrement: Boolean,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
) {
    Row {
        AppRepeatIconButton(
            icon = AppIcons.Remove,
            contentDescription = stringResource(R.string.set_editor_cd_decrement_value),
            enabled = canDecrement,
            onClick = onDecrement,
        )
        AppRepeatIconButton(
            icon = AppIcons.Add,
            contentDescription = stringResource(R.string.set_editor_cd_increment_value),
            enabled = canIncrement,
            onClick = onIncrement,
        )
    }
}

// Kilograms with a Polish decimal comma and no „,0”: „0”, „1,5”, „3”, „13,5”.
private fun formatWeight(weightGrams: Int): String =
    BigDecimal(weightGrams)
        .divide(BigDecimal(GRAMS_PER_KG))
        .stripTrailingZeros()
        .toPlainString()
        .replace('.', ',')

private const val GRAMS_PER_KG = 1_000
