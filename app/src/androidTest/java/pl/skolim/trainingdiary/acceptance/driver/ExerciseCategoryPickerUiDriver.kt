package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
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
    // A row is its category's name next to a badge holding the Exercise count.
    override fun assertListedInOrder(categories: List<Pair<String, Int>>) {
        compose.waitUntilExactlyOneExists(subtitle())
        val tops =
            categories.map { (name, count) ->
                val row = hasText(name) and hasAnySibling(hasAnyChild(hasText(count.toString())))
                compose.waitUntilExactlyOneExists(row)
                compose
                    .onNode(row)
                    .fetchSemanticsNode()
                    .boundsInRoot.top
            }
        assertThat(tops).isInStrictOrder()
    }

    override fun leave() {
        compose.waitUntilExactlyOneExists(subtitle())
        compose
            .onNodeWithContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
            .performClick()
        compose.waitUntilDoesNotExist(subtitle())
    }

    private fun subtitle(): SemanticsMatcher = hasText(resources.getString(R.string.exercise_category_picker_subtitle))
}
