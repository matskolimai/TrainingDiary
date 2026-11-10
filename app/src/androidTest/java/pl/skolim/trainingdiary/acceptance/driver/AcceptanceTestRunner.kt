package pl.skolim.trainingdiary.acceptance.driver

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

/** Runs the acceptance tests against [AcceptanceTestApplication] instead of the app's own `Application`. */
class AcceptanceTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application = super.newApplication(classLoader, AcceptanceTestApplication::class.java.name, context)
}
