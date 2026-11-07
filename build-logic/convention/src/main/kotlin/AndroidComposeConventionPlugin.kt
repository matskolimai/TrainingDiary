import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

/** Applied next to `convention.android.application` or `convention.android.library`. */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("kotlin-compose"))
            extensions.getByType<CommonExtension>().buildFeatures.compose = true
            dependencies {
                val bom = platform(libs.library("androidx-compose-bom"))
                "implementation"(bom)
                "implementation"(libs.library("androidx-compose-ui-tooling-preview"))
                "debugImplementation"(libs.library("androidx-compose-ui-tooling"))
                "androidTestImplementation"(bom)
            }
        }
    }
}
