package pl.skolim.trainingdiary.core.designsystem

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
 * The row itself isn't clickable; interactions belong to the slots. [minHeight] is one of the `Sizes.listRow*`
 * heights.
 */
@Composable
fun AppListRow(
    trailing: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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
                    .heightIn(min = minHeight)
                    .padding(start = Spacing.large, end = Spacing.extraSmall),
            horizontalArrangement = Arrangement.spacedBy(Spacing.large),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
            trailing()
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
