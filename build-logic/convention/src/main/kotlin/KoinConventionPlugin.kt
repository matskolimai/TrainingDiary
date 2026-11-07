import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library

/** Only the BOM and `koin-core`; other Koin artifacts are declared by the module that needs them. */
class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "implementation"(platform(libs.library("koin-bom")))
                "implementation"(libs.library("koin-core"))
            }
        }
    }
}
