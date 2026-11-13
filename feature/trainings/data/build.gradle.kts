plugins {
    id("convention.android.library")
    id("convention.koin")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.trainings.data"
}

dependencies {
    implementation(projects.feature.trainings.domain)
    implementation(projects.core.database)
    implementation(projects.core.domain)
}
