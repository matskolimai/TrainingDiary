package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.feature.trainings.presentation.R

class TrainingListUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) {
    fun assertEmptyList() {
        compose.waitUntilExactlyOneExists(hasText(resources.getString(R.string.training_list_empty_title)))
        compose.onNodeWithText(resources.getString(R.string.training_list_empty_body)).assertIsDisplayed()
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

    fun openNewTraining() {
        compose.waitUntilExactlyOneExists(addAction())
        compose.onNode(addAction()).performClick()
        compose.waitUntilDoesNotExist(addAction())
    }

    fun openTrainingForEditing(name: String) {
        chooseOption(name, R.string.training_list_edit)
        compose.waitUntilDoesNotExist(addAction())
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

    private fun chooseOption(
        name: String,
        @StringRes option: Int,
    ) {
        val rowOptions = moreOptions() and hasAnySibling(hasText(name))
        compose.waitUntilExactlyOneExists(rowOptions)
        compose.onNode(rowOptions).performClick()
        compose.onNode(hasClickAction() and hasText(resources.getString(option))).performClick()
    }

    private fun addAction(): SemanticsMatcher =
        hasContentDescription(resources.getString(R.string.training_list_cd_add))

    private fun moreOptions(): SemanticsMatcher =
        hasContentDescription(resources.getString(R.string.training_list_cd_more))

    private fun undoAction(): SemanticsMatcher =
        hasClickAction() and hasText(resources.getString(R.string.training_list_undo))
}
