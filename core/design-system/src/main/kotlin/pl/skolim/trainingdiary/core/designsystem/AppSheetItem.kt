package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

/** A full-width 56dp action in an [AppBottomSheet]: a decorative leading [icon] and [text]. */
@Composable
fun AppSheetItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().heightIn(min = Sizes.listItemHeight),
        shape = RectangleShape,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
        contentPadding = PaddingValues(horizontal = Spacing.large),
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(Spacing.large))
        Text(text = text, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
internal fun AppSheetItemPreview() {
    AppPreview {
        AppSheetItem(icon = AppIcons.Edit, text = "Edit", onClick = {})
    }
}
