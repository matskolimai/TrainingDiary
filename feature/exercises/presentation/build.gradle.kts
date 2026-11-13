plugins {
    id("convention.android.feature")
    id("convention.paparazzi")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.exercises.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designSystem)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.koin.androidx.compose)
}
