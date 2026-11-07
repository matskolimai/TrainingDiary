# 02: build-logic convention plugins

**What to build:** module build scripts become a few lines, because shared configuration lives in an included
build-logic build. `:app` is the first user. See the spec's "Toolchain and modules".

**Blocked by:** 01 (Toolchain bump)

**Status:** ready-for-agent

- [ ] Included build with plugins `convention.android.application`, `.android.library`, `.android.feature`,
      `.jvm.library`, `.android.compose`, `.android.room`, `.koin` and `.testing`
- [ ] `.android.feature` applies kotlinx-serialization and `navigation3-runtime`; `.android.room` applies
      `androidx.room3` + KSP and sets the schema directory; `.koin` adds only `platform(koin-bom)` + `koin-core`;
      `.testing` sets up JUnit 6 (mannodermaus 2.0.2 for Android) + Truth and declares `junit-platform-launcher`
- [ ] Base plugins apply `.testing` (and, from ticket 03, `.static-analysis`)
- [ ] AGP 9 rules: untyped `CommonExtension`, `compilerOptions`, no `kotlin-android`
- [ ] The compiler settings from ticket 01 move into the plugins
- [ ] Every coordinate and version comes from the version catalog; BOMs where they exist
- [ ] Type-safe project accessors are on
- [ ] `:app` uses `convention.android.application` + `convention.android.compose`
- [ ] `./gradlew check` is green and the app launches unchanged
