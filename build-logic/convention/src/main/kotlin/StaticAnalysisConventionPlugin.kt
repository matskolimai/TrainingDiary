import dev.detekt.gradle.extensions.DetektExtension
import dev.detekt.gradle.extensions.FailOnSeverity
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import pl.skolim.trainingdiary.buildlogic.libs
import pl.skolim.trainingdiary.buildlogic.library
import pl.skolim.trainingdiary.buildlogic.pluginId
import pl.skolim.trainingdiary.buildlogic.version

/**
 * ktlint (rules from the root `.editorconfig`) and Detekt with compose-rules (Compose adaptations in the root
 * `config/detekt/detekt.yml`). Applied by the base plugins; `check` runs `ktlintCheck` and the type-resolving
 * `detektMain` / `detektTest`.
 */
class StaticAnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = libs.pluginId("ktlint"))
            extensions.configure<KtlintExtension> {
                version.set(libs.version("ktlint"))
            }

            apply(plugin = libs.pluginId("detekt"))
            extensions.configure<DetektExtension> {
                buildUponDefaultConfig.set(true)
                config.setFrom(rootProject.layout.projectDirectory.file("config/detekt/detekt.yml"))
                // Detekt 2 replaced `maxIssues` with a severity threshold; failing on Info means any finding fails.
                failOnSeverity.set(FailOnSeverity.Info)
            }
            dependencies {
                "detektPlugins"(libs.library("compose-rules-detekt"))
            }
            // The plain `detekt` task skips type-resolving rules; the gate runs the variants that resolve types instead.
            tasks.named("check") {
                setDependsOn(dependsOn.filterNot { it.taskName() == "detekt" })
                dependsOn("detektMain", "detektTest")
            }
        }
    }
}

private fun Any.taskName(): String? = when (this) {
    is TaskProvider<*> -> name
    is Task -> name
    is String -> this
    else -> null
}
