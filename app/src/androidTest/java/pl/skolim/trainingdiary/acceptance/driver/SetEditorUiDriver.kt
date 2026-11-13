package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.feature.trainings.presentation.R
import pl.skolim.trainingdiary.core.designsystem.R as DesignSystemR

class SetEditorUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) : SetEditorDriver {
    override fun assertSetsInOrder(sets: List<PlannedSet>) {
        compose.waitUntilExactlyOneExists(title())
        val tops =
            sets.mapIndexed { index, set ->
                val card =
                    setCard(number = index + 1) and
                        hasAnyChild(pill(resources.getString(R.string.set_editor_weight, set.weightKg))) and
                        hasAnyChild(pill(resources.getString(R.string.set_editor_reps, set.reps)))
                compose.waitUntilExactlyOneExists(card)
                compose
                    .onNode(card)
                    .fetchSemanticsNode()
                    .boundsInRoot.top
            }
        assertThat(tops).isInStrictOrder()
        compose.onNode(setCard(number = sets.size + 1)).assertDoesNotExist()
    }

    override fun tapAddToTraining() {
        compose.waitUntilExactlyOneExists(addToTrainingButton())
        compose.onNode(addToTrainingButton()).performClick()
        compose.waitUntilDoesNotExist(addToTrainingButton())
    }

    // The Exercise picker under this screen has the same title and its own back arrow, so leaving waits for this
    // screen's back arrow, told apart by its node, to go.
    override fun leave() {
        val back = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_back))
        compose.waitUntilExactlyOneExists(back)
        val node = compose.onNode(back).fetchSemanticsNode().id
        compose.onNode(back).performClick()
        compose.waitUntil { compose.onAllNodes(back).fetchSemanticsNodes().none { it.id == node } }
    }

    // A Set card's children are its number circle, holding the number, and its pills.
    private fun setCard(number: Int): SemanticsMatcher =
        hasAnyChild(hasAnyChild(hasText(number.toString()))) and hasAnyChild(hasClickAction())

    private fun pill(text: String): SemanticsMatcher = hasClickAction() and hasText(text)

    private fun addToTrainingButton(): SemanticsMatcher =
        hasClickAction() and hasText(resources.getString(R.string.set_editor_add_to_training))

    private fun title(): SemanticsMatcher =
        hasText(resources.getString(R.string.set_editor_title)) and !hasClickAction()
}
