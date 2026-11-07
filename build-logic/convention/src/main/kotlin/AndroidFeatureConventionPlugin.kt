import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "convention.android.library")
            apply(plugin = "convention.android.compose")
            apply(plugin = "convention.koin")
            apply(plugin = libs.pluginId("kotlin-serialization"))
            dependencies {
                "implementation"(platform(libs.library("kotlinx-serialization-bom")))
                "implementation"(libs.library("kotlinx-serialization-core"))
                "implementation"(libs.library("androidx-navigation3-runtime"))
            }
        }
    }
}
