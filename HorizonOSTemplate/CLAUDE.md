# Horizon OS Template

A starter Android project template for building 2D panel apps on Meta Quest (Horizon OS). Created by the Meta Horizon Android Studio Plugin's New Project wizard.

## Project Structure

```
HorizonOSTemplate/
  app/
    src/main/
      java/.../horizonostemplate/
        MainActivity.kt          # Single-activity Compose app
      res/
        drawable/ic_meta_logo.xml # Meta logo vector drawable
      AndroidManifest.xml         # Horizon OS manifest with panel layout
    build.gradle.kts              # App module: dependencies, SDK config
  gradle/libs.versions.toml       # Version catalog (Spatial SDK, Compose, etc.)
  build.gradle.kts                # Root build file
  settings.gradle.kts             # Repository and module config
```

## Build & Deploy

**Prerequisites:** JDK 17, Android Studio (Meerkat+), Meta Spatial Simulator or Quest device.

```bash
# Build debug APK
./gradlew assembleDebug

# Install and run on connected device/simulator
./gradlew installDebug

# Clean build
./gradlew clean assembleDebug
```

## Key Dependencies

| Dependency | Purpose |
|---|---|
| `com.meta.spatial:meta-spatial-sdk-uiset` | UISet theme, components (SpatialTheme, SecondaryCard, SpatialSideNavItem) |
| `androidx.activity:activity-compose` | Compose integration with ComponentActivity |
| `androidx.compose.*` | Jetpack Compose UI framework |
| `androidx.compose.material3` | Material 3 icons and components |

Versions are managed in `gradle/libs.versions.toml`. The Spatial SDK version is controlled by the `spatialsdk` version variable.

## UISet Theming

This template uses Meta's UISet design system. Key patterns:

### Dark Theme Setup
```kotlin
SpatialTheme {
  CompositionLocalProvider(
    LocalContentColor provides LocalColorScheme.current.primaryAlphaBackground,
  ) {
    // App content here
  }
}
```
- `SpatialTheme { }` — wraps the app in UISet theming
- `LocalColorScheme.current.primaryAlphaBackground` — light text color for dark backgrounds (0xFFF1F4F7)
- `LocalColorScheme.current.secondaryAlphaBackground` — dimmer secondary text (60% white)
- `LocalColorScheme.current.panel` — gradient brush for dark panel backgrounds (0xFF414141 → 0xFF272727)

### Common UISet Components
- `SpatialSideNavItem` — navigation items with icon, label, selected state
- `SecondaryCard` — content cards with rounded corners and subtle background
- `SpatialTheme.typography.*` — `headline1`, `headline3`, `body1`, etc.
- `SpatialTheme.shapes.*` — `large`, `medium`, etc.

### Pitfalls
- **Always set panel background.** Without `.background(brush = LocalColorScheme.current.panel)`, UISet components render light text on a white/transparent background.
- **Always propagate text color.** Use `CompositionLocalProvider(LocalContentColor provides ...)` at the top level so child components inherit readable text colors.
- **SpatialSideNavItem defaults.** Don't set `collapsed = true` and `dense = true` together — the items render as tiny invisible squares. Use default expanded mode.

## Horizon OS Manifest

The `AndroidManifest.xml` includes Horizon OS-specific configuration:

```xml
<!-- Required: Horizon OS SDK version targeting -->
<horizonos:uses-horizonos-sdk
  horizonos:minSdkVersion="69"
  horizonos:targetSdkVersion="207" />

<!-- Supported Quest devices -->
<meta-data
  android:name="com.oculus.supportedDevices"
  android:value="quest2|questpro|quest3|quest3s" />

<!-- Panel default size (2D app window dimensions) -->
<layout
  android:defaultHeight="640dp"
  android:defaultWidth="1024dp" />
```

- `horizonos:` namespace: `http://schemas.horizonos/sdk`
- `horizonos:minSdkVersion` is the oldest installable Horizon OS release.
- `horizonos:targetSdkVersion` opts into behavior through that Horizon OS
  release and should advance independently of the minimum.
- Horizon OS SDK levels are separate from Android's `minSdk` and `targetSdk`.
- `android:launchMode="singleTask"` — standard for Quest apps
- `android:configChanges` — handles orientation/size changes without activity restart

## Platform Constraints (Horizon OS)

These Android features are NOT available on Horizon OS:
- Google Mobile Services (GMS) — Auth, Location, Ads, Billing
- Android Notification API
- Camera access
- Google Play Billing

Use Android-native alternatives where available (e.g., `LocationManager` instead of GMS Location).

## Going Immersive (Spatial SDK)

To convert this 2D panel app into a fully immersive 3D experience:

1. Add Spatial SDK dependencies to `app/build.gradle.kts`:
   ```kotlin
   implementation("com.meta.spatial:meta-spatial-sdk:${spatialSdkVersion}")
   ```
2. Change `MainActivity` to extend `AppSystemActivity` instead of `ComponentActivity`
3. Add spatial scene configuration and ECS components

See [Meta Spatial SDK documentation](https://developers.meta.com/horizon/develop/spatial-sdk/) for details.
