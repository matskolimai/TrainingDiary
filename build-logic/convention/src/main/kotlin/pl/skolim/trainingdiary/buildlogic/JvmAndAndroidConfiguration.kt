package pl.skolim.trainingdiary.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

private const val JVM_VERSION = 21

// Opt out per module by setting `trainingdiary.allowCompilerWarnings=true` in the module's gradle.properties,
// only for unfixable warnings from an experimental API.
private const val ALLOW_COMPILER_WARNINGS = "trainingdiary.allowCompilerWarnings"

internal fun Project.configureAndroid(android: CommonExtension) {
    android.apply {
        compileSdk { version = release(libs.versionInt("android-compileSdk")) }
        defaultConfig.minSdk = libs.versionInt("android-minSdk")
        compileOptions.apply {
            sourceCompatibility = JavaVersion.toVersion(JVM_VERSION)
            targetCompatibility = JavaVersion.toVersion(JVM_VERSION)
        }
    }
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(JVM_VERSION)
        compilerOptions { configure(this@configureAndroid) }
    }
}

internal fun Project.configureKotlinJvm() {
    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(JVM_VERSION)
        compilerOptions { configure(this@configureKotlinJvm) }
    }
}

private fun KotlinJvmCompilerOptions.configure(project: Project) {
    jvmTarget.set(JvmTarget.fromTarget(JVM_VERSION.toString()))
    allWarningsAsErrors.set(project.findProperty(ALLOW_COMPILER_WARNINGS)?.toString()?.toBoolean() != true)
}
