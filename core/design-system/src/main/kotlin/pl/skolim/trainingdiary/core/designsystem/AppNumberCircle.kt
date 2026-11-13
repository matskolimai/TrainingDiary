package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** A [Sizes.numberCircle] circle showing [number], such as a position in a list. */
@Composable
fun AppNumberCircle(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Box(modifier = Modifier.size(Sizes.numberCircle), contentAlignment = Alignment.Center) {
            Text(text = number.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
internal fun AppNumberCirclePreview() {
    AppPreview {
        AppNumberCircle(number = 3, modifier = Modifier.padding(Spacing.large))
    }
}
