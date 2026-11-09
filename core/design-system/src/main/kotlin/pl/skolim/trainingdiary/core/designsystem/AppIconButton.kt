package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

/** A standard 48dp icon button showing [icon]. */
@Composable
fun AppIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

@Preview
@Composable
internal fun AppIconButtonPreview() {
    AppPreview {
        AppIconButton(
            icon = AppIcons.MoreVert,
            contentDescription = "More",
            onClick = {},
        )
    }
}
