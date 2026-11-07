import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import pl.skolim.trainingdiary.buildlogic.configureAndroid
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.pluginId

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("android-library"))
            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }
            apply(plugin = "convention.testing")
        }
    }
}
