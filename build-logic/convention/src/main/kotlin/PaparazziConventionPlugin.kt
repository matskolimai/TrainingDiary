import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

/**
 * Paparazzi screenshot tests, applied next to `convention.android.library` only on modules that have them. Paparazzi
 * is a JUnit 4 rule, so its tests run on the JUnit Platform through the vintage engine beside JUnit 6. `check` verifies
 * the committed reference images; `./gradlew recordPaparazziDebug` re-records them. Tests get the shared
 * `appPaparazzi()` rule from `:core:screenshot-testing`.
 */
class PaparazziConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("paparazzi"))
            dependencies {
                "testImplementation"(libs.library("junit4"))
                "testImplementation"(project(":core:screenshot-testing"))
                "testRuntimeOnly"(libs.library("junit-vintage-engine"))
            }
            tasks.named("check") { dependsOn("verifyPaparazziDebug") }
        }
    }
}
