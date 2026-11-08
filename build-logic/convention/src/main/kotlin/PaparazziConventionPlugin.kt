import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId

// Paparazzi 2 is compiled for Java 21; only the test JVM runs on it, bytecode stays at the project's target.
private const val PAPARAZZI_TEST_JVM_VERSION = 21

/**
 * Paparazzi screenshot tests, applied next to `convention.android.library` only on modules that have them. Paparazzi
 * is a JUnit 4 rule, so its tests run on the JUnit Platform through the vintage engine beside JUnit 6. `check` verifies
 * the committed reference images; `./gradlew recordPaparazziDebug` re-records them.
 */
class PaparazziConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("paparazzi"))
            dependencies {
                "testImplementation"(libs.library("junit4"))
                "testRuntimeOnly"(libs.library("junit-vintage-engine"))
            }
            val toolchains = extensions.getByType<JavaToolchainService>()
            tasks.withType<Test>().configureEach {
                javaLauncher.set(
                    toolchains.launcherFor {
                        languageVersion.set(JavaLanguageVersion.of(PAPARAZZI_TEST_JVM_VERSION))
                    },
                )
            }
            tasks.named("check") { dependsOn("verifyPaparazziDebug") }
        }
    }
}
