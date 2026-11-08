plugins {
    id("convention.android.library")
    id("convention.android.compose")
    id("convention.paparazzi")
}

android {
    namespace = "pl.skolim.trainingdiary.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
