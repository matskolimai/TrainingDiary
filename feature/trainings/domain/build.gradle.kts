plugins {
    id("convention.jvm.library")
}

dependencies {
    api(projects.core.domain)
    api(platform(libs.kotlinx.coroutines.bom))
    api(libs.kotlinx.coroutines.core)
}
