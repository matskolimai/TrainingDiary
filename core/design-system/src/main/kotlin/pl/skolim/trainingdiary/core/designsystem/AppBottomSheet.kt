@file:OptIn(ExperimentalMaterial3Api::class)

package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview

/**
 * A modal bottom sheet with a drag handle and no title, showing [content] (usually [AppSheetItem]s).
 * [onDismiss] is called when the user swipes it down, taps the scrim or presses back.
 *
 * In a preview it draws the expanded sheet over a scrim in place: Paparazzi cannot capture the separate window
 * [ModalBottomSheet] opens.
 */
@Composable
fun AppBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (LocalInspectionMode.current) {
        AppBottomSheetInPlace(modifier = modifier, content = content)
    } else {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            modifier = modifier,
            shape = sheetShape,
            containerColor = sheetColor,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            content = content,
        )
    }
}

/** 28dp top corners. */
private val sheetShape: Shape
    @Composable get() = BottomSheetDefaults.ExpandedShape

private val sheetColor: Color
    @Composable @ReadOnlyComposable
    get() = MaterialTheme.colorScheme.surfaceContainerLow

// The expanded sheet over the scrim, sharing the real sheet's shape, colour and drag handle.
@Composable
private fun AppBottomSheetInPlace(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize().background(BottomSheetDefaults.ScrimColor),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Surface(modifier = Modifier.fillMaxWidth(), shape = sheetShape, color = sheetColor) {
            Column {
                BottomSheetDefaults.DragHandle(modifier = Modifier.align(Alignment.CenterHorizontally))
                content()
            }
        }
    }
}

@Preview
@Composable
internal fun AppBottomSheetPreview() {
    AppPreview {
        AppBottomSheet(onDismiss = {}) {
            AppSheetItem(icon = AppIcons.Edit, text = "Edit", onClick = {})
            AppSheetItem(icon = AppIcons.Delete, text = "Delete", onClick = {})
        }
    }
}
