package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

/**
 * A tonal 40dp icon button showing [icon], such as − or + beside a value. Disabled when not [enabled]. Holding it
 * repeats [onClick], first after [FirstRepeatDelay] and then every [RepeatInterval], until release or until it is
 * disabled, such as at a limit.
 */
@Composable
fun AppRepeatIconButton(
    icon: ImageVector,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val currentOnClick by rememberUpdatedState(onClick)
    // Set once a hold has repeated, so letting go does not step once more. Cleared by the recomposition after the
    // release, so a later quick tap, whose press and release land in one frame, still steps.
    var repeated by remember { mutableStateOf(false) }
    LaunchedEffect(pressed && enabled) {
        if (!pressed || !enabled) {
            repeated = false
        } else {
            delay(FirstRepeatDelay)
            while (true) {
                repeated = true
                currentOnClick()
                delay(RepeatInterval)
            }
        }
    }
    FilledTonalIconButton(
        onClick = { if (!repeated) onClick() },
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}

private val FirstRepeatDelay = 400.milliseconds
private val RepeatInterval = 90.milliseconds

@Preview
@Composable
internal fun AppRepeatIconButtonPreviewEnabled() {
    AppPreview {
        AppRepeatIconButton(
            icon = AppIcons.Add,
            contentDescription = "Add",
            enabled = true,
            onClick = {},
            modifier = Modifier.padding(Spacing.large),
        )
    }
}

@Preview
@Composable
internal fun AppRepeatIconButtonPreviewDisabled() {
    AppPreview {
        AppRepeatIconButton(
            icon = AppIcons.Remove,
            contentDescription = "Remove",
            enabled = false,
            onClick = {},
            modifier = Modifier.padding(Spacing.large),
        )
    }
}
