plugins {
    id("convention.android.library")
    id("convention.koin")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.exercises.data"
}

dependencies {
    implementation(projects.core.domain)
}
