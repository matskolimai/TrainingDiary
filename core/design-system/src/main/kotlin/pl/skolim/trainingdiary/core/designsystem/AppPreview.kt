package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * Wraps every preview: the app theme over a `background` surface, in inspection mode. Android Studio's preview sets
 * inspection mode itself but Paparazzi doesn't, and [AppBottomSheet] needs it to draw in place.
 */
@Composable
fun AppPreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        AppTheme {
            Surface(modifier = modifier, color = MaterialTheme.colorScheme.background, content = content)
        }
    }
}
