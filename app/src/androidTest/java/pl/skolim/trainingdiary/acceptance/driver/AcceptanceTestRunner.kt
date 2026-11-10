package pl.skolim.trainingdiary.acceptance.driver

import android.app.Application
import android.app.LocaleManager
import android.content.Context
import android.os.Bundle
import android.os.LocaleList
import androidx.test.runner.AndroidJUnitRunner

/**
 * Runs the acceptance tests against [AcceptanceTestApplication] instead of the app's own `Application`, with the app in
 * Polish: the managed device is `en-US`, and Polish plural forms (2 treningi, 5 treningów) need the Polish locale.
 */
class AcceptanceTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application = super.newApplication(classLoader, AcceptanceTestApplication::class.java.name, context)

    override fun onCreate(arguments: Bundle?) {
        targetContext.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags("pl")
        super.onCreate(arguments)
    }
}
