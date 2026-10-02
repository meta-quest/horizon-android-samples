# Meta VR Template

A minimal 2D Android application template for mixed reality. It uses standard
Android and Jetpack Compose with Meta UI Set styling.

## Getting Started

1. Open this project in Android Studio with the Meta VR Android Studio Plugin installed
2. Connect a Meta Quest device or use the Meta Spatial Simulator
3. Build and run the app

## What the template configures

This is a Standard Android app: `MainActivity` extends `ComponentActivity`, and
the UI is ordinary Jetpack Compose. The UI Set dependency supplies themed
components; it does not change the application model or the build path.

The manifest contains OS configuration that is independent of the
Android SDK levels in `app/build.gradle.kts`:

- `metavr:minSdkVersion` is the oldest OS release on which the app may be
  installed.
- `metavr:targetSdkVersion` opts the app into OS behavior through that
  release. Update the target when adopting a newer release; do not raise the
  minimum unless the app requires newer APIs.
- The legacy `horizonos:uses-horizonos-sdk` element is declared alongside the
  current one, because the OS and the Developer Dashboard still accept it.
- `com.oculus.supportedDevices` declares the headset families supported by the
  app.
- The activity's `<layout>` sets the initial 2D panel size. Users can resize the
  panel, so layouts must remain responsive.

In `MainActivity.kt`, the UI Set theme supplies colors, shapes, and typography.
The template also applies the panel background and inherited text color
explicitly, because UI Set components assume a dark panel surface.

## Project Structure

- `app/src/main/java/` - Kotlin source files
- `app/src/main/res/` - Android resources
- `app/src/main/AndroidManifest.xml` - App manifest with OS configuration

## Documentation

- [Android app documentation](https://developers.meta.com/horizon/develop/android-apps/)

## Troubleshooting

### Gradle uses the wrong JDK

If the project cannot build or sync because Gradle is using an incompatible JDK, select JDK 17.
For command-line builds, set `JAVA_HOME` to your JDK 17 installation directory before running the
Gradle wrapper again. In Android Studio, set **Gradle JDK** to JDK 17 under **Settings** > **Build,
Execution, Deployment** > **Build Tools** > **Gradle**.
