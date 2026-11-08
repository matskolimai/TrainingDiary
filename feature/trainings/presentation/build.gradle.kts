plugins {
    id("convention.android.feature")
}

android {
    namespace = "pl.skolim.trainingdiary.feature.trainings.presentation"
}

dependencies {
    implementation(projects.feature.trainings.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
}
