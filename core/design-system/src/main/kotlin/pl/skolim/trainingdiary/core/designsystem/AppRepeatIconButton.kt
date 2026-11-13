package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

/** A tonal 40dp icon button showing [icon], such as − or + beside a value. Disabled when not [enabled]. */
@Composable
fun AppRepeatIconButton(
    icon: ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Preview
@Composable
internal fun AppRepeatIconButtonPreviewEnabled() {
    AppPreview {
        AppRepeatIconButton(
            icon = AppIcons.Add,
            contentDescription = "Add",
            enabled = true,
            onClick = {},
            modifier = Modifier.padding(Spacing.large),
        )
    }
}

@Preview
@Composable
internal fun AppRepeatIconButtonPreviewDisabled() {
    AppPreview {
        AppRepeatIconButton(
            icon = AppIcons.Remove,
            contentDescription = "Remove",
            enabled = false,
            onClick = {},
            modifier = Modifier.padding(Spacing.large),
        )
    }
}
