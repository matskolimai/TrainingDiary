package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.AnnotatedString
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.acceptance.Channel
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import pl.skolim.trainingdiary.core.designsystem.R as DesignSystemR

// Steps that read or type on this screen wait for the name field first, since the step that opened the screen
// waited only for the previous screen to leave.
class TrainingEditorUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) : TrainingEditorDriver {
    override fun assertEmptyForAdding() {
        compose.waitUntilExactlyOneExists(nameField())
        compose.onNodeWithText(resources.getString(R.string.training_editor_title_add)).assertIsDisplayed()
        compose.onNode(nameField() and hasNoInput()).assertIsDisplayed()
    }

    override fun assertShowingForEditing(name: String) {
        compose.waitUntilExactlyOneExists(nameField())
        compose.onNodeWithText(resources.getString(R.string.training_editor_title_edit)).assertIsDisplayed()
        compose.onNode(nameField() and hasInput(name)).assertIsDisplayed()
    }

    override fun assertNameFieldFocused() {
        // The field is focused by an effect after the first frame, so wait for it rather than assert at once.
        compose.waitUntilExactlyOneExists(nameField() and isFocused())
    }

    override fun typeName(name: String) {
        compose.waitUntilExactlyOneExists(nameField())
        compose.onNode(nameField()).performTextInput(name)
    }

    override fun replaceName(name: String) {
        compose.waitUntilExactlyOneExists(nameField())
        compose.onNode(nameField()).performTextReplacement(name)
    }

    override fun tapSave() {
        compose.onNode(saveButton()).performClick()
        compose.waitUntilDoesNotExist(nameField())
    }

    override fun tapSaveTwiceQuickly(): Unit = throw CannotStageException("tapSaveTwiceQuickly", Channel.DEVICE)

    override fun assertCannotSave() {
        compose.waitUntilExactlyOneExists(nameField())
        compose.onNode(saveButton()).assertIsNotEnabled()
    }

    override fun leave() {
        compose
            .onNodeWithContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
            .performClick()
        compose.waitUntilDoesNotExist(nameField())
    }

    private fun nameField(): SemanticsMatcher =
        hasSetTextAction() and hasText(resources.getString(R.string.training_editor_name_label))

    private fun saveButton(): SemanticsMatcher =
        hasClickAction() and hasText(resources.getString(R.string.training_editor_save))

    private fun hasNoInput(): SemanticsMatcher = hasInput("")

    private fun hasInput(text: String): SemanticsMatcher =
        SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString(text))
}
