# Horizon OS Template

A minimal 2D Android application template for Horizon OS on Meta Quest devices.
It uses standard Android and Jetpack Compose with Meta UISet styling, without
the immersive Spatial SDK application model.

## Getting Started

1. Open this project in Android Studio with the Meta Horizon OS plugin installed
2. Connect a Meta Quest device or use an emulator
3. Build and run the app

## What the template configures

This is a Standard Android app: `MainActivity` extends `ComponentActivity`, and
the UI is ordinary Jetpack Compose. The Meta Spatial SDK UISet dependency adds
Horizon-styled components and theming; it does not make the app immersive or
change its build path.

The manifest contains Horizon OS configuration that is independent of the
Android SDK levels in `app/build.gradle.kts`:

- `horizonos:minSdkVersion="69"` is the oldest Horizon OS release on which the
  app may be installed.
- `horizonos:targetSdkVersion="207"` opts the app into platform behavior through
  Horizon OS v207. Update this target when adopting a newer Horizon OS release;
  do not raise the minimum unless the app requires newer APIs.
- `com.oculus.supportedDevices` declares the headset families supported by the
  app.
- The activity's `<layout>` sets the initial 2D panel size. Users can resize the
  panel, so layouts must remain responsive.

In `MainActivity.kt`, `SpatialTheme` supplies UISet colors, shapes, and
typography. The template also applies the panel background and inherited text
color explicitly because UISet components assume a dark panel surface.

## Project Structure

- `app/src/main/java/` - Kotlin source files
- `app/src/main/res/` - Android resources
- `app/src/main/AndroidManifest.xml` - App manifest with Horizon OS configuration

## Documentation

- [Horizon OS Documentation](https://developers.meta.com/horizon/develop/android-apps/)
