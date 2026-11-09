package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** A centre-aligned top bar showing [title]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
    )
}

@Preview
@Composable
internal fun AppTopBarPreview() {
    AppPreview {
        AppTopBar(title = "Title")
    }
}
