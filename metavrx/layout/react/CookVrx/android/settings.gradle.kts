/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

pluginManagement {
  // React Native ships its Gradle plugin as source inside node_modules rather than
  // as a Maven artifact, so it is pulled in as a composite build. It supplies the
  // `com.facebook.react*` plugin ids used by this project and the app module.
  includeBuild("../node_modules/@react-native/gradle-plugin")

  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

plugins { id("com.facebook.react.settings") }

// Runs `npx react-native config` and includes every autolinkable dependency as a
// Gradle module. Both Layout packages are direct app dependencies so each package
// contributes its own native registration and codegen schema.
extensions.configure<com.facebook.react.ReactSettingsExtension> {
  autolinkLibrariesFromCommand()
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "CookVrxRn"

include(":app")
