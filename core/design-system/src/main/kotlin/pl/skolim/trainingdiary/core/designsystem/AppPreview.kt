package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/** Wraps every preview: the app theme over a `background` surface. */
@Composable
fun AppPreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AppTheme {
        Surface(modifier = modifier, color = MaterialTheme.colorScheme.background, content = content)
    }
}
