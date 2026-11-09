package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** A 56dp-high, fully rounded filled button labelled [text]. Its width comes from [modifier]. */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(Sizes.buttonHeight),
        enabled = enabled,
        shape = CircleShape,
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
internal fun AppButtonPreviewEnabled() {
    AppPreview {
        AppButton(
            text = "Save",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(Spacing.large),
        )
    }
}

@Preview
@Composable
internal fun AppButtonPreviewDisabled() {
    AppPreview {
        AppButton(
            text = "Save",
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(Spacing.large),
            enabled = false,
        )
    }
}
