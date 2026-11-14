package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview

/**
 * A 40dp-high tappable pill showing [text], such as „0 kg”. When [focused] it is filled with `secondaryContainer`
 * and outlined in `primary`; otherwise it has no fill and no outline.
 */
@Composable
fun AppValuePill(
    text: String,
    focused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(Sizes.compactButtonHeight).semantics { selected = focused },
        shape = MaterialTheme.shapes.medium,
        colors =
            if (focused) {
                ButtonDefaults.outlinedButtonColors(
                    containerColor = colors.secondaryContainer,
                    contentColor = colors.onSecondaryContainer,
                )
            } else {
                ButtonDefaults.outlinedButtonColors(contentColor = colors.onSurface)
            },
        border = if (focused) BorderStroke(Sizes.focusBorderWidth, colors.primary) else null,
        contentPadding = PaddingValues(horizontal = Spacing.large),
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
internal fun AppValuePillPreviewFocused() {
    AppPreview {
        AppValuePill(text = "60 kg", focused = true, onClick = {}, modifier = Modifier.padding(Spacing.large))
    }
}

@Preview
@Composable
internal fun AppValuePillPreviewUnfocused() {
    AppPreview {
        AppValuePill(text = "12 powt.", focused = false, onClick = {}, modifier = Modifier.padding(Spacing.large))
    }
}
