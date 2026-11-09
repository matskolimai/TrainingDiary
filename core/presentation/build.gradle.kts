plugins {
    id("convention.android.library")
    id("convention.android.compose")
}

android {
    namespace = "pl.skolim.trainingdiary.core.presentation"
}

dependencies {
    implementation(libs.androidx.navigation3.runtime)
}
