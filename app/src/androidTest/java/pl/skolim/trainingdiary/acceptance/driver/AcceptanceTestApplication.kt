package pl.skolim.trainingdiary.acceptance.driver

import android.app.Application

// Starts no Koin itself; InMemoryAppExtension starts the app's graph with test overrides before each test.
class AcceptanceTestApplication : Application()
