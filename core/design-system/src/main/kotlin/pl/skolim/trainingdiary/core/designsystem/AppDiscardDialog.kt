package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * Asks whether to discard unsaved changes. „Odrzuć” calls [onDiscard]; „Anuluj”, a tap outside and back call
 * [onDismiss].
 */
@Composable
fun AppDiscardDialog(
    onDiscard: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDiscard) {
                Text(text = stringResource(R.string.design_system_discard_dialog_discard))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.design_system_discard_dialog_cancel))
            }
        },
        title = { Text(text = stringResource(R.string.design_system_discard_dialog_title)) },
    )
}

@Preview
@Composable
internal fun AppDiscardDialogPreview() {
    AppPreview {
        AppDiscardDialog(onDiscard = {}, onDismiss = {})
    }
}
