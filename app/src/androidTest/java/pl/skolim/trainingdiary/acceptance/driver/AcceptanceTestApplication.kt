package pl.skolim.trainingdiary.acceptance.driver

import android.app.Application

/** Starts nothing itself, so each test can set up the app's graph with its own overrides. */
class AcceptanceTestApplication : Application()
