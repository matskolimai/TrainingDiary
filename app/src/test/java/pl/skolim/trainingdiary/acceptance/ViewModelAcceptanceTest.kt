package pl.skolim.trainingdiary.acceptance

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.dsl.onClose
import pl.skolim.trainingdiary.acceptance.driver.AppViewModelDriver
import pl.skolim.trainingdiary.acceptance.driver.ExerciseCategoryPickerViewModelDriver
import pl.skolim.trainingdiary.acceptance.driver.ExercisePickerViewModelDriver
import pl.skolim.trainingdiary.acceptance.driver.HeadlessApp
import pl.skolim.trainingdiary.acceptance.driver.SetEditorViewModelDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingEditorViewModelDriver
import pl.skolim.trainingdiary.acceptance.driver.TrainingListViewModelDriver
import pl.skolim.trainingdiary.acceptance.dsl.AppDsl
import pl.skolim.trainingdiary.appModules
import pl.skolim.trainingdiary.core.database.TrainingDiaryDatabase

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("AbstractClassCanBeConcreteClass") // Only extended by each Screen's one-line test class.
abstract class ViewModelAcceptanceTest : AcceptanceCases {
    override val channel = Channel.VIEW_MODEL

    override fun app(steps: AppDsl.() -> Unit) {
        val dispatcher = StandardTestDispatcher()
        runTest(dispatcher) {
            Dispatchers.setMain(dispatcher)
            try {
                val databaseModule =
                    module {
                        single {
                            Room
                                .inMemoryDatabaseBuilder<TrainingDiaryDatabase>()
                                .setDriver(BundledSQLiteDriver())
                                .setQueryCoroutineContext(dispatcher)
                                .build()
                        } onClose { it?.close() }
                    }
                val koin = startKoin { modules(appModules + databaseModule) }.koin
                HeadlessApp(koin, this).use { app ->
                    app.settle()
                    AppDsl(
                        appDriver = AppViewModelDriver(app),
                        trainingListDriver = TrainingListViewModelDriver(app),
                        trainingEditorDriver = TrainingEditorViewModelDriver(app),
                        exerciseCategoryPickerDriver = ExerciseCategoryPickerViewModelDriver(app),
                        exercisePickerDriver = ExercisePickerViewModelDriver(app),
                        setEditorDriver = SetEditorViewModelDriver(app),
                    ).steps()
                }
            } finally {
                stopKoin()
                Dispatchers.resetMain()
            }
        }
    }
}
