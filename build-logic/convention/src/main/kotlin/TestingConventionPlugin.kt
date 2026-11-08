import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

/**
 * JUnit 6 + Truth for local tests. Applied by the base plugins; on Android the mannodermaus plugin runs local tests on
 * the JUnit Platform.
 *
 * On the application module it also sets up the acceptance tests: JUnit 6 on device (mannodermaus adds its runner,
 * core and Compose artifacts once it sees `junit-jupiter-api` and a Compose dependency in `androidTest`) and a
 * headless Gradle Managed Device, run with `./gradlew pixel9DebugAndroidTest`.
 */
class TestingConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            listOf("android-application", "android-library").forEach { android ->
                pluginManager.withPlugin(libs.pluginId(android)) {
                    apply(plugin = libs.pluginId("android-junit"))
                }
            }
            pluginManager.withPlugin(libs.pluginId("android-application")) {
                configureAcceptanceTests()
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

private fun Project.configureAcceptanceTests() {
    extensions.configure<ApplicationExtension> {
        // The developer's phone, at the highest API level with an `aosp-atd` image.
        testOptions.managedDevices.allDevices.register<ManagedVirtualDevice>("pixel9") {
            device = "Pixel 9"
            sdkVersion = 37
            systemImageSource = "aosp-atd"
        }
    }
    dependencies {
        "androidTestImplementation"(platform(libs.library("junit-bom")))
        "androidTestImplementation"(libs.library("junit-jupiter-api"))
        "androidTestImplementation"(libs.library("androidx-test-runner"))
        "androidTestImplementation"(libs.library("truth"))
        "androidTestImplementation"(platform(libs.library("androidx-compose-bom")))
        "androidTestImplementation"(libs.library("androidx-compose-ui-test"))
        // Compose UI test idles through Espresso; the 3.6.1 it pulls in crashes on API 37 (no InputManager.getInstance).
        "androidTestRuntimeOnly"(libs.library("androidx-espresso-core"))
    }
}
