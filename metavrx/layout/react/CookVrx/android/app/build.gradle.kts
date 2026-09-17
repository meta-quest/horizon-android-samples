/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  // React Native's own Gradle plugin, resolved from the composite build wired in
  // settings.gradle.kts. It owns everything a React Native app needs that cannot be
  // expressed as a plain dependency: New Architecture codegen (the TurboModule
  // registry that `TurboModuleRegistry.getEnforcing` reads at startup), the
  // `react-android` / `hermes-android` versions taken from node_modules, the
  // `IS_NEW_ARCHITECTURE_ENABLED` / `IS_HERMES_ENABLED` BuildConfig fields read by
  // `MainApplication`, JS bundling for release builds, and autolinking.
  id("com.facebook.react")
}

react {
  // Paths default to the Gradle root's parent — this sample's root, which holds
  // package.json, index.js, and node_modules — so only autolinking needs wiring.
  autolinkLibrariesWithApp()
}

android {
  namespace = "com.meta.spatial.samples.cookvrxrn"
  compileSdk = 36
  // Match the NDK used by the reproducible release build.
  ndkVersion = "27.1.12297006"

  defaultConfig {
    applicationId = "com.meta.spatial.samples.cookvrxrn"
    // 29, not 24: the Layout React Native SDK's autolinked android/ project declares minSdk 29,
    // and the manifest merger refuses a consumer below its libraries. Every Horizon OS device is
    // well above 29 anyway.
    minSdk = 29
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    // Quest is arm64-only; skip x86/armeabi-v7a builds.
    ndk { abiFilters += listOf("arm64-v8a") }

    // Infra's legacy ccache cannot compile or link React Native's generated C++.
    externalNativeBuild { cmake { arguments += "-DCCACHE_FOUND=OFF" } }
  }

  buildTypes {
    debug {
      // Default debug build — Metro bundler attaches at runtime.
    }
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions { jvmTarget = "17" }

  sourceSets["main"].java.srcDirs("src/main/kotlin")
}

dependencies {
  implementation(platform(libs.metavrx.bom))

  // Versionless on purpose: the React Native Gradle plugin pins both to the versions the
  // installed `react-native` package declares, keeping the native runtime and the
  // JS Metro serves in lockstep. `hermes-android` carries `libhermesvm.so`, which
  // `react-android` does not.
  implementation("com.facebook.react:react-android")

  // React Native defaults Hermes on, so default to on here too when the property is absent:
  // `BuildConfig.IS_HERMES_ENABLED` would otherwise say Hermes while the engine was never packaged.
  // `hermes-android` publishes under `com.facebook.hermes`, not `com.facebook.react` — the old
  // coordinate still resolves, but only through a deprecation substitution the plugin warns about.
  if (project.providers.gradleProperty("hermesEnabled").orNull?.toBoolean() ?: true) {
    implementation("com.facebook.hermes:hermes-android")
  }

  // The window artifact provides the native module and view manager and pulls
  // in the matching base artifact. Its version comes from the MetaVRX BOM.
  implementation(libs.metavrx.layout.window.react.compat)
}
