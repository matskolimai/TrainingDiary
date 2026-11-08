package pl.skolim.trainingdiary.acceptance.trainings

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.isRoot
import com.google.common.truth.Truth.assertThat
import de.mannodermaus.junit5.compose.createAndroidComposeExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import pl.skolim.trainingdiary.MainActivity
import pl.skolim.trainingdiary.acceptance.driver.AcceptanceTestApplication

// Throwaway harness check; ticket 12 replaces it with the first acceptance case.
@OptIn(ExperimentalTestApi::class)
class AppLaunchSmokeTest {
    @JvmField
    @RegisterExtension
    val compose = createAndroidComposeExtension<MainActivity>()

    @Test
    fun `the app launches under the acceptance test application`() =
        compose.use {
            waitUntilExactlyOneExists(isRoot())
            assertThat(compose.activity.application).isInstanceOf(AcceptanceTestApplication::class.java)
        }
}
