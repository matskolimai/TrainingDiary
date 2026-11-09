package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.AnnotatedString
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import pl.skolim.trainingdiary.core.designsystem.R as DesignSystemR

class TrainingsUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) {
    fun assertEmptyList() {
        compose.waitUntilExactlyOneExists(hasText(resources.getString(R.string.training_list_empty_title)))
        compose.onNodeWithText(resources.getString(R.string.training_list_empty_body)).assertIsDisplayed()
    }

    fun openEditorForAdding() {
        compose.onNodeWithContentDescription(resources.getString(R.string.training_list_cd_add)).performClick()
        compose.waitUntilExactlyOneExists(nameField())
    }

    fun assertEmptyEditorForAdding() {
        compose.onNodeWithText(resources.getString(R.string.training_editor_title_add)).assertIsDisplayed()
        // The field is focused by an effect after the first frame, so wait for it rather than assert at once.
        compose.waitUntilExactlyOneExists(nameField() and hasNoInput() and isFocused())
    }

    fun typeName(name: String) {
        compose.onNode(nameField()).performTextInput(name)
    }

    fun leaveEditor() {
        compose
            .onNodeWithContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
            .performClick()
        compose.waitUntilDoesNotExist(nameField())
    }

    private fun nameField(): SemanticsMatcher =
        hasSetTextAction() and hasText(resources.getString(R.string.training_editor_name_label))

    private fun hasNoInput(): SemanticsMatcher =
        SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString(""))
}
