# 01: Toolchain bump

**What to build:** the existing template app builds and launches on the toolchain chosen in the spec, before any
structure changes. This isolates the risk of Kotlin 2.4 on AGP 9.5 (not officially tested together) in one small
change. See the spec's "Toolchain and modules".

**Blocked by:** None (can start immediately)

**Status:** ready-for-agent

- [ ] Kotlin 2.4.21, AGP 9.5.0, Compose BOM 2026.09.00 and KSP 2.3.13 are in the version catalog, with one `kotlin`
      version shared by built-in Kotlin and `kotlin-jvm`
- [ ] JVM target and toolchain are 17
- [ ] `allWarningsAsErrors = true`, with a per-module Gradle-property opt-out for unfixable experimental-API warnings
- [ ] `org.gradle.parallel=true`
- [ ] `kotlin-android` is not applied anywhere
- [ ] `./gradlew check` is green and the app launches unchanged
- [ ] If a version doesn't work, the ticket stops and reports instead of picking another version
