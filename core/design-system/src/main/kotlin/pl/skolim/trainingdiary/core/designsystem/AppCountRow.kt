package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** A labelled count stepper on a low-emphasis rounded surface. */
@Suppress("LongParameterList") // Label plus the count stepper's value, callbacks and enabled flags.
@Composable
fun AppCountRow(
    label: String,
    count: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    decrementEnabled: Boolean,
    incrementEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.large, vertical = Spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            AppCountStepper(
                count = count,
                onDecrement = onDecrement,
                onIncrement = onIncrement,
                decrementEnabled = decrementEnabled,
                incrementEnabled = incrementEnabled,
            )
        }
    }
}

@Preview
@Composable
internal fun AppCountRowPreview() {
    AppPreview {
        AppCountRow(
            label = "Count",
            count = 3,
            onDecrement = {},
            onIncrement = {},
            decrementEnabled = true,
            incrementEnabled = true,
            modifier = Modifier.padding(Spacing.large),
        )
    }
}
