package pl.skolim.trainingdiary.core.designsystem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

/** A 40dp-high, fully rounded outlined button: a leading [icon] and [text]. Its width comes from [modifier]. */
@Composable
fun AppOutlinedButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(Sizes.compactButtonHeight),
        shape = CircleShape,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize))
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }
}

@Preview
@Composable
internal fun AppOutlinedButtonPreview() {
    AppPreview {
        AppOutlinedButton(
            text = "Add",
            icon = AppIcons.Add,
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(Spacing.large),
        )
    }
}
