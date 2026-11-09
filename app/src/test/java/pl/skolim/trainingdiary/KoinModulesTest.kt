package pl.skolim.trainingdiary

import android.content.Context
import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.test.verify.verify

class KoinModulesTest {
    // verifyAll checks each module alone, so one module including the rest lets a binding use another module's.
    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `koin graph is complete`() {
        module { includes(appModules) }.verify(extraTypes = listOf(Context::class))
    }
}
