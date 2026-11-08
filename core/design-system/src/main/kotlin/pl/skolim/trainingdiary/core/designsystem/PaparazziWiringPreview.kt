package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// Proves the Paparazzi wiring; ticket 07 replaces it with the AppTheme preview.
@Preview
@Composable
internal fun PaparazziWiringPreview() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface {
            Text(text = "Dziennik treningów")
        }
    }
}
