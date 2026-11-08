package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val AppColorScheme = darkColorScheme()
private val AppTypography = Typography()
private val AppShapes = Shapes()

/** The app's only theme: dark, with Material 3 default colours, typography and shapes. */
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content,
    )
}

@Preview
@Composable
internal fun AppThemePreview() {
    AppPreview {
        Column(
            modifier = Modifier.padding(Spacing.large),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium),
        ) {
            Text(text = "Title large", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Title small in primary",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            ThemeSwatch("surfaceContainer", MaterialTheme.colorScheme.surfaceContainer)
            ThemeSwatch("surfaceContainerLow", MaterialTheme.colorScheme.surfaceContainerLow)
            ThemeSwatch("primaryContainer", MaterialTheme.colorScheme.primaryContainer)
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.large)) {
                listOf(
                    AppIcons.Add,
                    AppIcons.MoreVert,
                    AppIcons.Edit,
                    AppIcons.Delete,
                    AppIcons.Back,
                    AppIcons.FitnessCenter,
                ).forEach { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeSwatch(
    name: String,
    color: Color,
) {
    Surface(color = color, shape = MaterialTheme.shapes.large) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(Spacing.large),
        )
    }
}
