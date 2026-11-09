package pl.skolim.trainingdiary.acceptance.driver

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.dsl.onClose
import pl.skolim.trainingdiary.appModules
import pl.skolim.trainingdiary.core.database.TrainingDiaryDatabase

// Starts the app's Koin graph with a fresh in-memory database before each test and stops it after.
// Register it with `@ExtendWith` on the test class: class-level extensions run before the Compose extension's fields,
// so Koin is up before the activity launches and stops only after the activity has closed.
class InMemoryAppExtension :
    BeforeEachCallback,
    AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(appModules + inMemoryDatabaseModule)
        }
    }

    override fun afterEach(context: ExtensionContext) {
        stopKoin()
    }
}

private val inMemoryDatabaseModule =
    module {
        single {
            Room
                .inMemoryDatabaseBuilder<TrainingDiaryDatabase>(get<Context>())
                .setDriver(AndroidSQLiteDriver())
                .build()
        } onClose { it?.close() }
    }
