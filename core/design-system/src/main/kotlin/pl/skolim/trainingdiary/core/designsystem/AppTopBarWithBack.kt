package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * A small top bar showing [title], with a back arrow that calls [onBackClick]. A non-null [subtitle] is shown
 * under the title.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBarWithBack(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    TopAppBar(
        title = {
            Column {
                Text(text = title)
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            AppIconButton(
                icon = AppIcons.Back,
                contentDescription = stringResource(R.string.design_system_cd_back),
                onClick = onBackClick,
            )
        },
    )
}

@Preview
@Composable
internal fun AppTopBarWithBackPreview() {
    AppPreview {
        AppTopBarWithBack(title = "Title", onBackClick = {})
    }
}

@Preview
@Composable
internal fun AppTopBarWithBackSubtitlePreview() {
    AppPreview {
        AppTopBarWithBack(title = "Title", onBackClick = {}, subtitle = "Subtitle")
    }
}
