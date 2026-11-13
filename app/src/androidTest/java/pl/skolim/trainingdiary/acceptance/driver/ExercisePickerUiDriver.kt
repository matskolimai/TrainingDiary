package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.core.designsystem.R as DesignSystemR

class ExercisePickerUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) : ExercisePickerDriver {
    override fun assertListedInOrder(exercises: List<String>) {
        val tops =
            exercises.map { name ->
                compose.waitUntilExactlyOneExists(hasText(name))
                compose
                    .onNode(hasText(name))
                    .fetchSemanticsNode()
                    .boundsInRoot.top
            }
        assertThat(tops).isInStrictOrder()
    }

    override fun tapExercise(name: String) {
        compose.waitUntilExactlyOneExists(exerciseRow(name))
        compose.onNode(exerciseRow(name)).performClick()
        compose.waitUntilDoesNotExist(exerciseRow(name))
    }

    // The Exercise category picker under this screen has the same title and its own back arrow, so leaving waits
    // for this screen's back arrow, told apart by its node, to go.
    override fun leave() {
        val back = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
        compose.waitUntilExactlyOneExists(back)
        val node = compose.onNode(back).fetchSemanticsNode().id
        compose.onNode(back).performClick()
        compose.waitUntil { compose.onAllNodes(back).fetchSemanticsNodes().none { it.id == node } }
    }

    private fun exerciseRow(name: String): SemanticsMatcher = hasClickAction() and hasText(name)
}
