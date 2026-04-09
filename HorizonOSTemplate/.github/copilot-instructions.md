# Horizon OS Template — Copilot Instructions

A starter Android project for building 2D panel apps on Meta Quest (Horizon OS).

## Project Structure

```
HorizonOSTemplate/
  app/
    src/main/
      java/.../horizonostemplate/
        MainActivity.kt          # Single-activity Compose app (all UI in one file)
      res/
        drawable/ic_meta_logo.xml # Meta logo vector drawable
      AndroidManifest.xml         # Horizon OS manifest with panel layout
    build.gradle.kts              # App module: dependencies, SDK config
  gradle/libs.versions.toml       # Version catalog (Spatial SDK, Compose, etc.)
  build.gradle.kts                # Root build file
  settings.gradle.kts             # Repository and module config
```

## Build & Deploy

Prerequisites: JDK 17, Android Studio (Meerkat+), Meta Spatial Simulator or Quest device.

```bash
./gradlew assembleDebug    # Build debug APK
./gradlew installDebug     # Install and run on connected device/simulator
```

## Architecture

Single-activity Compose app. `MainActivity` extends `ComponentActivity` — all UI is Jetpack Compose inside `setContent { }`.

### UI Structure

```
MainActivity (ComponentActivity)
  └─ SpatialTheme + CompositionLocalProvider (theme + text color)
      └─ HorizonOSApp()
          ├─ LazyColumn (side nav, 160dp wide)
          │   └─ SpatialSideNavItem × 3 (Home, Features, Tools)
          └─ AnimatedContent (tab content area)
              ├─ HomeContent()
              ├─ FeaturesContent()
              └─ ToolsContent()
```

### Key Composables

| Composable | Purpose |
|---|---|
| `HorizonOSApp()` | Root layout: side nav + animated content area |
| `ScrollableTabContent()` | Scrollable column with bottom gradient fade overlay |
| `InfoCard()` | Reusable card with accent icon, title, description |
| `HomeContent()` | Welcome tab with Meta logo and getting-started info |
| `FeaturesContent()` | Platform features overview |
| `ToolsContent()` | Developer tools overview |

## Key Dependencies

| Dependency | Purpose |
|---|---|
| `com.meta.spatial:meta-spatial-sdk-uiset` | UISet theme, components (SpatialTheme, SecondaryCard, SpatialSideNavItem) |
| `androidx.activity:activity-compose` | Compose integration with ComponentActivity |
| `androidx.compose.*` | Jetpack Compose UI framework |
| `androidx.compose.material3` | Material 3 icons and components |

Versions are managed in `gradle/libs.versions.toml`. The Spatial SDK version is controlled by the `spatialsdk` version variable.

## UISet Theming

This template uses Meta's UISet design system:

- `SpatialTheme { }` — wraps the app in UISet theming
- `LocalColorScheme.current.primaryAlphaBackground` — light text for dark backgrounds
- `LocalColorScheme.current.secondaryAlphaBackground` — dimmer secondary text
- `LocalColorScheme.current.panel` — gradient brush for dark panel backgrounds

### Pitfalls

- **Always set panel background.** Without `.background(brush = LocalColorScheme.current.panel)`, UISet components render light text on white/transparent.
- **Always propagate text color.** Use `CompositionLocalProvider(LocalContentColor provides ...)` at the top level.
- **SpatialSideNavItem defaults.** Don't set `collapsed = true` and `dense = true` together — items become invisible.

## Horizon OS Manifest

```xml
<!-- Required: Horizon OS SDK version targeting -->
<horizonos:uses-horizonos-sdk
  horizonos:minSdkVersion="69"
  horizonos:targetSdkVersion="69" />

<!-- Supported Quest devices -->
<meta-data
  android:name="com.oculus.supportedDevices"
  android:value="quest2|questpro|quest3" />

<!-- Panel default size (2D app window dimensions) -->
<layout android:defaultHeight="640dp" android:defaultWidth="1024dp" />
```

## Platform Constraints (Horizon OS)

These Android features are NOT available on Horizon OS:
- Google Mobile Services (GMS) — Auth, Location, Ads, Billing
- Android Notification API
- Camera access
- Google Play Billing

Use Android-native alternatives where available (e.g., `LocationManager` instead of GMS Location).

## Adding a New Tab

1. Add a new `SpatialSideNavItem` in the `LazyColumn` with the next index
2. Add a new composable function (e.g., `SettingsContent()`)
3. Add a `when` branch in the `AnimatedContent` block
4. Use `ScrollableTabContent { }` as the wrapper for scrollable content with gradient fade

## Common Modifications

- **Change panel dimensions:** Edit `<layout>` in `AndroidManifest.xml`
- **Add a dependency:** Add to `gradle/libs.versions.toml` under `[libraries]`, reference in `app/build.gradle.kts`
- **Change app name:** Update `android:label` in `AndroidManifest.xml`
- **Change app package:** Update `namespace`/`applicationId` in `app/build.gradle.kts`, `android:name` in manifest, and Kotlin package declaration

## Going Immersive (Spatial SDK)

To convert to a fully immersive 3D experience:

1. Add Spatial SDK dependencies to `app/build.gradle.kts`
2. Change `MainActivity` to extend `AppSystemActivity` instead of `ComponentActivity`
3. Add spatial scene configuration and ECS components

See [Meta Spatial SDK documentation](https://developers.meta.com/horizon/develop/spatial-sdk/) for details.
