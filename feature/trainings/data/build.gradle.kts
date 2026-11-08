plugins {
    id("convention.android.library")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.trainings.data"
}

dependencies {
    implementation(projects.feature.trainings.domain)
    implementation(projects.core.database)
}
