import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import pl.skolim.trainingdiary.buildlogic.configureAndroid
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.pluginId
import pl.skolim.trainingdiary.buildlogic.versionInt

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("android-application"))
            extensions.configure<ApplicationExtension> {
                configureAndroid(this)
                defaultConfig.targetSdk = libs.versionInt("android-targetSdk")
            }
            apply(plugin = "convention.testing")
        }
    }
}
