import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

/**
 * JUnit 6 + Truth for local tests. Applied by the base plugins; on Android the mannodermaus plugin runs local tests on
 * the JUnit Platform.
 */
class TestingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            listOf("android-application", "android-library").forEach { android ->
                pluginManager.withPlugin(libs.pluginId(android)) {
                    apply(plugin = libs.pluginId("android-junit"))
                }
            }
            pluginManager.withPlugin(libs.pluginId("kotlin-jvm")) {
                tasks.withType<Test>().configureEach { useJUnitPlatform() }
            }
            dependencies {
                "testImplementation"(platform(libs.library("junit-bom")))
                "testImplementation"(libs.library("junit-jupiter"))
                "testImplementation"(libs.library("truth"))
                "testRuntimeOnly"(libs.library("junit-platform-launcher"))
            }
        }
    }
}
