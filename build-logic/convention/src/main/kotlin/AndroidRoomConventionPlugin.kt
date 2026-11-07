import androidx.room3.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

/** Applied next to `convention.android.library`. */
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("ksp"))
            apply(plugin = libs.pluginId("room3"))
            extensions.configure<RoomExtension> {
                schemaDirectory(layout.projectDirectory.dir("schemas"))
            }
            dependencies {
                "implementation"(libs.library("androidx-room3-runtime"))
                "ksp"(libs.library("androidx-room3-compiler"))
            }
        }
    }
}
