plugins {
    `kotlin-dsl`
}

group = "pl.skolim.trainingdiary.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room3.gradlePlugin)
    compileOnly(libs.ktlint.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "convention.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "convention.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "convention.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "convention.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("androidRoom") {
            id = "convention.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("koin") {
            id = "convention.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("testing") {
            id = "convention.testing"
            implementationClass = "TestingConventionPlugin"
        }
        register("staticAnalysis") {
            id = "convention.static-analysis"
            implementationClass = "StaticAnalysisConventionPlugin"
        }
    }
}
