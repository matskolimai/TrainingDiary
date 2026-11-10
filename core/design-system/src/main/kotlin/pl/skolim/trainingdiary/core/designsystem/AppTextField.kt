package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

/**
 * An outlined single-line text field labelled [label]; the keyboard's Done action only hides the keyboard.
 *
 * Width comes from [modifier]; to focus the field (for example on first composition), pass
 * `Modifier.focusRequester(...)`.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
    )
}

@Preview
@Composable
internal fun AppTextFieldPreviewEmpty() {
    AppPreview {
        AppTextField(
            value = "",
            onValueChange = {},
            label = "Label",
            modifier = Modifier.fillMaxWidth().padding(Spacing.large),
        )
    }
}

@Preview
@Composable
internal fun AppTextFieldPreviewFilled() {
    AppPreview {
        AppTextField(
            value = "Value",
            onValueChange = {},
            label = "Label",
            modifier = Modifier.fillMaxWidth().padding(Spacing.large),
        )
    }
}
