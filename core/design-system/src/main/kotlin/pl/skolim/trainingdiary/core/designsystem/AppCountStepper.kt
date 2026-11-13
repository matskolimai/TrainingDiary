package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/** A „− [count] +” control; [decrementEnabled] and [incrementEnabled] turn off − and +, such as at a limit. */
@Suppress("LongParameterList") // A value, two callbacks and two enabled flags, as M3's own controls take.
@Composable
fun AppCountStepper(
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    decrementEnabled: Boolean,
    incrementEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppRepeatIconButton(
            icon = AppIcons.Remove,
            contentDescription = stringResource(R.string.design_system_cd_decrement),
            enabled = decrementEnabled,
            onClick = onDecrement,
        )
        Text(
            text = count.toString(),
            modifier = Modifier.widthIn(min = Sizes.countMinWidth),
            style = MaterialTheme.typography.titleLarge.copy(fontFeatureSettings = "tnum"),
            textAlign = TextAlign.Center,
        )
        AppRepeatIconButton(
            icon = AppIcons.Add,
            contentDescription = stringResource(R.string.design_system_cd_increment),
            enabled = incrementEnabled,
            onClick = onIncrement,
        )
    }
}

@Preview
@Composable
internal fun AppCountStepperPreviewMiddle() {
    AppCountStepperPreview(count = 3, decrementEnabled = true, incrementEnabled = true)
}

@Preview
@Composable
internal fun AppCountStepperPreviewMin() {
    AppCountStepperPreview(count = 1, decrementEnabled = false, incrementEnabled = true)
}

@Preview
@Composable
internal fun AppCountStepperPreviewMax() {
    AppCountStepperPreview(count = 10, decrementEnabled = true, incrementEnabled = false)
}

@Composable
private fun AppCountStepperPreview(
    count: Int,
    decrementEnabled: Boolean,
    incrementEnabled: Boolean,
) {
    AppPreview {
        AppCountStepper(
            count = count,
            onDecrement = {},
            onIncrement = {},
            decrementEnabled = decrementEnabled,
            incrementEnabled = incrementEnabled,
            modifier = Modifier.padding(Spacing.large),
        )
    }
}
