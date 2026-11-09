plugins {
    id("convention.android.feature")
    id("convention.paparazzi")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.trainings.presentation"
}

dependencies {
    implementation(projects.feature.trainings.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.koin.androidx.compose)
}
