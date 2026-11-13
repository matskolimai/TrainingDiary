package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

/**
 * A separated, rounded list row: [content] fills the space and wraps, [trailing] sits at the end.
 * With [onClick] the whole row is the tap target. [minHeight] is one of the `Sizes.listRow*` heights.
 */
@Composable
fun AppListRow(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    minHeight: Dp = Sizes.listRowMinHeight,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Row(
            modifier =
                Modifier
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .heightIn(min = minHeight)
                    // Trailing content pads its own end (an icon button through its touch target); text alone takes
                    // the full margin.
                    .padding(start = Spacing.large, end = if (trailing != null) Spacing.extraSmall else Spacing.large),
            horizontalArrangement = Arrangement.spacedBy(Spacing.large),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            trailing?.invoke()
        }
    }
}

@Composable
private fun AppListRowPreviewContent(text: String) {
    AppPreview {
        AppListRow(
            trailing = { AppIconButton(icon = AppIcons.MoreVert, contentDescription = "More", onClick = {}) },
            modifier = Modifier.padding(Spacing.large),
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
internal fun AppListRowPreviewShortText() {
    AppListRowPreviewContent(text = "Item")
}

@Preview
@Composable
internal fun AppListRowPreviewLongText() {
    AppListRowPreviewContent(
        text = "A much longer item name that does not fit on a single line and wraps onto the next ones",
    )
}

@Preview
@Composable
internal fun AppListRowPreviewWithoutTrailing() {
    AppPreview {
        AppListRow(
            modifier = Modifier.padding(Spacing.large),
            onClick = {},
            minHeight = Sizes.listRowCompactMinHeight,
        ) {
            Text(text = "A much longer item name that does not fit on a single line and wraps onto the next ones")
        }
    }
}
