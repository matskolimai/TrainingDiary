package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.feature.exercises.presentation.R
import pl.skolim.trainingdiary.core.designsystem.R as DesignSystemR

class ExerciseCategoryPickerUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) : ExerciseCategoryPickerDriver {
    // A row is one tap target holding its category's name and the badge with the Exercise count.
    override fun assertListedInOrder(categories: List<Pair<String, Int>>) {
        compose.waitUntilExactlyOneExists(title())
        val tops =
            categories.map { (name, count) ->
                val row = categoryRow(name) and hasText(count.toString())
                compose.waitUntilExactlyOneExists(row)
                compose
                    .onNode(row)
                    .fetchSemanticsNode()
                    .boundsInRoot.top
            }
        assertThat(tops).isInStrictOrder()
    }

    override fun tapCategory(name: String) {
        compose.waitUntilExactlyOneExists(categoryRow(name))
        compose.onNode(categoryRow(name)).performClick()
        compose.waitUntilDoesNotExist(categoryRow(name))
    }

    override fun leave() {
        compose.waitUntilExactlyOneExists(title())
        compose
            .onNodeWithContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
            .performClick()
        compose.waitUntilDoesNotExist(title())
    }

    private fun categoryRow(name: String): SemanticsMatcher = hasClickAction() and hasText(name)

    // The Training editor's „Dodaj ćwiczenie” button has the same text; the title is the one that isn't clickable.
    private fun title(): SemanticsMatcher =
        hasText(resources.getString(R.string.exercise_category_picker_title)) and !hasClickAction()
}
