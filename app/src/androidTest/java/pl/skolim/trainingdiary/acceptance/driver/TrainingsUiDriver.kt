package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.AnnotatedString
import com.google.common.truth.Truth.assertThat
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

    fun addTraining(name: String) {
        openEditorForAdding()
        typeName(name)
        tapSave()
    }

    // Names may repeat (duplicates are allowed), so rows are matched by position, not by unique text.
    fun assertListedInOrder(names: List<String>) {
        compose.waitUntilNodeCount(moreOptions(), names.size)
        val listed =
            names
                .distinct()
                .flatMap { name ->
                    compose.onAllNodesWithText(name).fetchSemanticsNodes().map { it.boundsInRoot.top to name }
                }.sortedBy { (top, _) -> top }
                .map { (_, name) -> name }
        assertThat(listed).containsExactlyElementsIn(names).inOrder()
    }

    fun assertCount(count: Int) {
        compose.waitUntilExactlyOneExists(hasText(countLabel(count)))
    }

    // The count is hidden on the empty list: once the empty state shows, the label for zero Trainings must not.
    fun assertNoCount() {
        assertEmptyList()
        compose.onNodeWithText(countLabel(0)).assertDoesNotExist()
    }

    fun openEditorForAdding() {
        compose.onNodeWithContentDescription(resources.getString(R.string.training_list_cd_add)).performClick()
        compose.waitUntilExactlyOneExists(nameField())
    }

    fun assertEmptyEditorForAdding() {
        compose.onNodeWithText(resources.getString(R.string.training_editor_title_add)).assertIsDisplayed()
        compose.onNode(nameField() and hasNoInput()).assertIsDisplayed()
    }

    fun openEditorForEditing(name: String) {
        chooseOption(name, R.string.training_list_edit)
        compose.waitUntilExactlyOneExists(nameField())
    }

    fun deleteTraining(name: String) {
        chooseOption(name, R.string.training_list_delete)
    }

    fun assertUndoDeleteOffered(name: String) {
        compose.waitUntilExactlyOneExists(hasText(resources.getString(R.string.training_list_deleted, name)))
        compose.onNode(undoAction()).assertIsDisplayed()
    }

    fun undoDelete() {
        compose.waitUntilExactlyOneExists(undoAction())
        compose.onNode(undoAction()).performClick()
    }

    fun assertEditorForEditing(name: String) {
        compose.onNodeWithText(resources.getString(R.string.training_editor_title_edit)).assertIsDisplayed()
        compose.onNode(nameField() and hasInput(name)).assertIsDisplayed()
    }

    fun assertNameFieldFocused() {
        // The field is focused by an effect after the first frame, so wait for it rather than assert at once.
        compose.waitUntilExactlyOneExists(nameField() and isFocused())
    }

    fun typeName(name: String) {
        compose.onNode(nameField()).performTextInput(name)
    }

    fun replaceName(name: String) {
        compose.onNode(nameField()).performTextReplacement(name)
    }

    fun tapSave() {
        compose.onNode(saveButton()).performClick()
        compose.waitUntilDoesNotExist(nameField())
    }

    fun assertCannotSave() {
        compose.onNode(saveButton()).assertIsNotEnabled()
    }

    fun leaveEditor() {
        compose
            .onNodeWithContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
            .performClick()
        compose.waitUntilDoesNotExist(nameField())
    }

    private fun chooseOption(
        name: String,
        @StringRes option: Int,
    ) {
        compose.onNode(moreOptions() and hasAnySibling(hasText(name))).performClick()
        compose.onNode(hasClickAction() and hasText(resources.getString(option))).performClick()
    }

    private fun countLabel(count: Int): String =
        resources.getQuantityString(R.plurals.training_list_count, count, count)

    private fun moreOptions(): SemanticsMatcher =
        hasContentDescription(resources.getString(R.string.training_list_cd_more))

    private fun undoAction(): SemanticsMatcher =
        hasClickAction() and hasText(resources.getString(R.string.training_list_undo))

    private fun nameField(): SemanticsMatcher =
        hasSetTextAction() and hasText(resources.getString(R.string.training_editor_name_label))

    private fun saveButton(): SemanticsMatcher =
        hasClickAction() and hasText(resources.getString(R.string.training_editor_save))

    private fun hasNoInput(): SemanticsMatcher = hasInput("")

    private fun hasInput(text: String): SemanticsMatcher =
        SemanticsMatcher.expectValue(SemanticsProperties.EditableText, AnnotatedString(text))
}
