package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

/** A small fully rounded badge showing [count]; [Sizes.countBadge] high and at least as wide. */
@Composable
fun AppCountBadge(
    count: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Box(
            modifier =
                Modifier
                    .height(Sizes.countBadge)
                    .widthIn(min = Sizes.countBadge)
                    .padding(horizontal = Spacing.small),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = count.toString(), fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview
@Composable
internal fun AppCountBadgePreview() {
    AppPreview {
        AppCountBadge(count = 8, modifier = Modifier.padding(Spacing.large))
    }
}
