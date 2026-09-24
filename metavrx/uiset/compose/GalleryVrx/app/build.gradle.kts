/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
}

android {
  namespace = "com.example.metavrx.uiset.gallery"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.metavrx.uiset.gallery"
    minSdk = 29
    //noinspection ExpiredTargetSdkVersion
    targetSdk = 32
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    release {
      signingConfig = signingConfigs.getByName("debug")
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions { jvmTarget = "11" }

  buildFeatures { compose = true }
}

dependencies {
  implementation(platform(libs.metavrx.bom))

  // The Meta VR UI Set — the subject of this sample. Its version comes from
  // the MetaVRX BOM.
  implementation(libs.metavrx.uiset.compose.compat)

  // Compose. The UI Set is built against Compose 1.7.8 as a FLOOR, not a match:
  // bytecode compiled against 1.7.8 runs on newer Compose, so a current BOM here
  // is expected and is what a consuming app will realistically use.
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.ui.tooling.preview)
  debugImplementation(libs.androidx.compose.ui.tooling)

  // AndroidX
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.compose)
}
