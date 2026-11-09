package pl.skolim.trainingdiary.acceptance.driver

import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithText
import de.mannodermaus.junit5.compose.ComposeContext
import pl.skolim.trainingdiary.feature.trainings.presentation.R

class TrainingsUiDriver(
    private val compose: ComposeContext,
    private val resources: Resources,
) {
    fun assertEmptyList() {
        compose.waitUntilExactlyOneExists(hasText(resources.getString(R.string.training_list_empty_title)))
        compose.onNodeWithText(resources.getString(R.string.training_list_empty_body)).assertIsDisplayed()
    }
}
