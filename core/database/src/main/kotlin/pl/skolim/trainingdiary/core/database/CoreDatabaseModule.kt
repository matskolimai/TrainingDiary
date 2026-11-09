package pl.skolim.trainingdiary.core.database

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import org.koin.dsl.module

val coreDatabaseModule =
    module {
        single {
            Room
                .databaseBuilder<TrainingDiaryDatabase>(get<Context>(), "training-diary.db")
                .setDriver(AndroidSQLiteDriver())
                .build()
        }
        single { get<TrainingDiaryDatabase>().trainingDao() }
    }
