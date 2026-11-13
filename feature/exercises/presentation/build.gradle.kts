plugins {
    id("convention.android.feature")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.exercises.presentation"
}

dependencies {
    implementation(projects.core.domain)
}
