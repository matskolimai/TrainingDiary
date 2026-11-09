package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/** The screen frame: [topBar] at the top, [fab] in the bottom-end corner and snackbars from [snackbarHostState]. */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues) -> Unit,
) {
    AppScaffold(
        snackbar = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
        topBar = topBar,
        fab = fab,
        content = content,
    )
}

/** [AppScaffold] with the snackbar as a slot, so the preview can show a snackbar without a running host. */
@Composable
private fun AppScaffold(
    snackbar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    fab: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        snackbarHost = snackbar,
        floatingActionButton = fab,
        content = content,
    )
}

@Preview
@Composable
internal fun AppScaffoldPreview() {
    AppPreview {
        AppScaffold(
            snackbar = { Snackbar(modifier = Modifier.padding(Spacing.medium)) { Text(text = "Snackbar") } },
            topBar = { AppTopBar(title = "Title") },
            fab = { AppFab(icon = AppIcons.Add, contentDescription = "Add", onClick = {}) },
        ) { contentPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Content")
            }
        }
    }
}
