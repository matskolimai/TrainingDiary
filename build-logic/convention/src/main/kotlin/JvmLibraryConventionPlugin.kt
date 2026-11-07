import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import pl.skolim.trainingdiary.buildlogic.configureKotlinJvm
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.pluginId

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("kotlin-jvm"))
            configureKotlinJvm()
            apply(plugin = "convention.testing")
        }
    }
}
