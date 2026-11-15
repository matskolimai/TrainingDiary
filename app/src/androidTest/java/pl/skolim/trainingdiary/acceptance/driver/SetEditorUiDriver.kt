package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
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

    override fun addSet() {
        val increment = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_increment))
        compose.waitUntilExactlyOneExists(increment)
        compose.onNode(increment).performClick()
        compose.waitForIdle()
    }

    override fun removeSet() {
        val decrement = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_decrement))
        compose.waitUntilExactlyOneExists(decrement)
        compose.onNode(decrement).performClick()
        compose.waitForIdle()
    }

    override fun assertCount(
        count: Int,
        canRemove: Boolean,
        canAdd: Boolean,
    ) {
        val decrement = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_decrement))
        val increment = hasContentDescription(resources.getString(DesignSystemR.string.design_system_cd_increment))
        compose.waitUntilExactlyOneExists(hasText(count.toString()) and hasAnySibling(decrement))
        if (canRemove) compose.onNode(decrement).assertIsEnabled() else compose.onNode(decrement).assertIsNotEnabled()
        if (canAdd) compose.onNode(increment).assertIsEnabled() else compose.onNode(increment).assertIsNotEnabled()
    }

    override fun focusWeight(set: Int) = focus(set, weightPill())

    override fun focusReps(set: Int) = focus(set, repsPill())

    private fun focus(
        set: Int,
        value: SemanticsMatcher,
    ) {
        compose.waitUntilExactlyOneExists(title())
        compose.onNode(value and hasParent(setCard(set))).performClick()
        compose.waitForIdle()
    }

    override fun assertFocusedReps(set: Int) = assertFocused(set, repsPill())

    override fun assertFocusedWeight(set: Int) = assertFocused(set, weightPill())

    private fun assertFocused(
        set: Int,
        value: SemanticsMatcher,
    ) {
        compose.waitUntilExactlyOneExists(title())
        compose.onNode(isSelected()).assertExists()
        compose.onNode(isSelected() and hasParent(setCard(set)) and value).assertExists()
    }

    private fun weightPill(): SemanticsMatcher =
        valuePill("weight value from resources") { text ->
            val weight = text.takeWhile { it.isDigit() || it == ',' }
            weight.isNotEmpty() && text == resources.getString(R.string.set_editor_weight, weight)
        }

    private fun repsPill(): SemanticsMatcher = repsPill(RepsUnit.REPETITIONS) or repsPill(RepsUnit.SECONDS)

    private fun repsPill(unit: RepsUnit): SemanticsMatcher {
        val format =
            when (unit) {
                RepsUnit.REPETITIONS -> R.string.set_editor_reps
                RepsUnit.SECONDS -> R.string.set_editor_seconds
            }
        return valuePill("reps value in $unit from resources") { text ->
            val reps = text.takeWhile(Char::isDigit).toIntOrNull()
            reps != null && text == resources.getString(format, reps)
        }
    }

    private fun valuePill(
        description: String,
        matches: (String) -> Boolean,
    ): SemanticsMatcher =
        hasClickAction() and
            SemanticsMatcher(description) { node ->
                node.config
                    .getOrNull(SemanticsProperties.Text)
                    .orEmpty()
                    .any { matches(it.text) }
            }

    override fun increaseValue(times: Int) = changeValue(R.string.set_editor_cd_increment_value, times)

    override fun decreaseValue(times: Int) = changeValue(R.string.set_editor_cd_decrement_value, times)

    private fun changeValue(
        description: Int,
        times: Int,
    ) {
        val button = hasContentDescription(resources.getString(description))
        compose.waitUntilExactlyOneExists(button)
        if (times == 1) {
            compose.onNode(button).performClick()
        } else {
            // Batch repeated presses before redraw; every press uses the real button callback.
            compose.onNode(button).assertIsEnabled().performSemanticsAction(SemanticsActions.OnClick) { press ->
                repeat(times) { press() }
            }
        }
        compose.waitForIdle()
    }

    override fun assertValueControls(
        set: Int,
        canDecrease: Boolean,
        canIncrease: Boolean,
    ) {
        val decrement = hasContentDescription(resources.getString(R.string.set_editor_cd_decrement_value))
        val increment = hasContentDescription(resources.getString(R.string.set_editor_cd_increment_value))
        compose.waitUntilExactlyOneExists(decrement)
        compose.waitUntilExactlyOneExists(increment)
        val decrease = compose.onNode(decrement and hasParent(setCard(set)))
        val increase = compose.onNode(increment and hasParent(setCard(set)))
        if (canDecrease) decrease.assertIsEnabled() else decrease.assertIsNotEnabled()
        if (canIncrease) increase.assertIsEnabled() else increase.assertIsNotEnabled()
    }

    override fun tapAddToTraining() {
        compose.waitUntilExactlyOneExists(addToTrainingButton())
        compose.onNode(addToTrainingButton()).performClick()
        compose.waitUntilDoesNotExist(addToTrainingButton())
    }

    override fun assertRepsUnit(unit: RepsUnit) {
        val other = RepsUnit.entries.single { it != unit }
        compose.waitUntilAtLeastOneExists(repsPill(unit))
        compose.onAllNodes(repsPill(other)).assertCountEquals(0)
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
