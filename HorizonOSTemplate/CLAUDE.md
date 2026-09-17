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
        values/strings.xml        # app_name, used as the activity label
        values/styles.xml         # Theme.AppTheme, referenced by the manifest
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
<metavr:uses-metavr-sdk
  metavr:minSdkVersion="69"
  metavr:targetSdkVersion="207" />

<!-- Legacy form of the same declaration, still accepted; keep the levels identical -->
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

- `metavr:` namespace: `http://schemas.meta.com/metavr-sdk`
- `horizonos:` namespace: `http://schemas.horizonos/sdk`
- Both elements are declared. Horizon OS and the Developer Dashboard still
  accept the legacy one, and the Meta VR SDK AARs declare both themselves. Their
  levels must match, or the merged manifest carries two different floors.
- `minSdkVersion` is the oldest installable Horizon OS release.
- `targetSdkVersion` opts into behavior through that Horizon OS
  release and should advance independently of the minimum.
- Horizon OS SDK levels are separate from Android's `minSdk` and `targetSdk`.
- `android:launchMode="singleTask"` — standard for Quest apps
- `android:configChanges` — handles orientation/size changes without activity restart

## Look and Pinch

Look and pinch is the **default input method on devices that ship without controllers**, so these
are requirements, not preferences. Two Horizon OS facts drive all of them:

- **The app receives no hover events.** Raw eye-tracking data is never exposed to panel
  applications, so hover is not delivered. Pointer events behave like touch, and anything that
  depends on hover state will not work.
- **The app never receives the user's gaze.** The *system* draws the hover and selection
  affordance from its own "UI Understanding" of the panel. An element the system does not
  recognise as interactive gets no affordance, even if it responds to a pinch, so the user cannot
  tell it is targetable.

Rules for this template:

| Rule | How it is done here |
|---|---|
| Interactive targets are at least 48dp, 60dp recommended | `LookAndPinchMinTargetHeight` is applied to every side-nav item |
| Make interactivity visible to the system | Use `Modifier.clickable`, or a component that takes an `onClick`. Do not draw a bare `Canvas` and handle raw pointer input |
| Never put a click listener on static content | A stray `Modifier.clickable` makes the system draw a highlight on something the user cannot act on |
| Group cards as one target | Put the click listener on the parent container, never on the children, or each child highlights separately |
| Declare the shape before the click | Put `Modifier.clip(shape)` before `clickable`, or give both the same shape — otherwise the system highlights the rectangular bounds |
| Do not style `state_hovered` | Look and pinch never delivers hover events. Style the pressed and selected states instead |

Shape inference needs **Jetpack Compose 1.10.0 or newer**. This template's Compose BOM
(`2024.09.03`) resolves 1.7.x, so the system highlight falls back to rectangular bounds until the
BOM is raised. Everything else above applies regardless of the Compose version.

Native tooltips never appear under look and pinch. A tooltip carrying real meaning has to move to
the Gaze SDK, which is not a dependency of this template.

## Horizon OS Constraints

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
   implementation(libs.meta.spatial.sdk)
   ```
   declaring `meta-spatial-sdk` in `gradle/libs.versions.toml` against the
   existing `spatialsdk` version.
2. Change `MainActivity` to extend `AppSystemActivity` instead of `ComponentActivity`
3. Add spatial scene configuration and ECS components

See [Meta Spatial SDK documentation](https://developers.meta.com/horizon/develop/spatial-sdk/) for details.
