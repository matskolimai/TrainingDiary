package pl.skolim.trainingdiary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TrainingDiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TrainingDiaryApp)
            modules(appModules)
        }
    }
}
