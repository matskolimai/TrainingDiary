plugins {
    id("convention.android.application")
    id("convention.android.compose")
    id("convention.koin")
}

android {
    namespace = "pl.skolim.trainingdiary"

    defaultConfig {
        applicationId = "pl.skolim.trainingdiary"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "pl.skolim.trainingdiary.acceptance.driver.AcceptanceTestRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(projects.feature.trainings.domain)
    implementation(projects.feature.trainings.data)
    implementation(projects.feature.trainings.presentation)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
    implementation(projects.core.database)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.koin.android)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.koin.test)
    testImplementation(libs.androidx.room3.runtime)
    testImplementation(libs.androidx.sqlite.bundled.jvm)
    testImplementation(libs.kotlinx.coroutines.test)

    // The in-memory database the acceptance tests swap in.
    androidTestImplementation(libs.androidx.room3.runtime)
}
