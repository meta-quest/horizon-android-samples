# Meta VR Template — Copilot Instructions

A starter Android project for building 2D panel apps for mixed reality.

## Project Structure

```
MetaVRTemplate/
  app/
    src/main/
      java/.../metavrtemplate/
        MainActivity.kt          # Single-activity Compose app (all UI in one file)
      res/
        drawable/ic_meta_logo.xml # Meta logo vector drawable
      AndroidManifest.xml         # App manifest with panel layout
    build.gradle.kts              # App module: dependencies, SDK config
  gradle/libs.versions.toml       # Version catalog (UI Set, Compose, etc.)
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
      └─ MetaVRApp()
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
| `MetaVRApp()` | Root layout: side nav + animated content area |
| `ScrollableTabContent()` | Scrollable column with bottom gradient fade overlay |
| `InfoCard()` | Reusable card with accent icon, title, description |
| `HomeContent()` | Welcome tab with Meta logo and getting-started info |
| `FeaturesContent()` | Platform features overview |
| `ToolsContent()` | Developer tools overview |

## Key Dependencies

| Dependency | Purpose |
|---|---|
| `com.meta.spatial:meta-spatial-sdk-uiset` | UI Set theme, components (SpatialTheme, SecondaryCard, SpatialSideNavItem) |
| `androidx.activity:activity-compose` | Compose integration with ComponentActivity |
| `androidx.compose.*` | Jetpack Compose UI framework |
| `androidx.compose.material3` | Material 3 icons and components |

Versions are managed in `gradle/libs.versions.toml`. The UI Set version is controlled by the `spatialsdk` version variable.

## UI Set Theming

This template uses Meta's UI Set design system:

- `SpatialTheme { }` — wraps the app in UI Set theming
- `LocalColorScheme.current.primaryAlphaBackground` — light text for dark backgrounds
- `LocalColorScheme.current.secondaryAlphaBackground` — dimmer secondary text
- `LocalColorScheme.current.panel` — gradient brush for dark panel backgrounds

### Pitfalls

- **Always set panel background.** Without `.background(brush = LocalColorScheme.current.panel)`, UI Set components render light text on white/transparent.
- **Always propagate text color.** Use `CompositionLocalProvider(LocalContentColor provides ...)` at the top level.
- **SpatialSideNavItem defaults.** Don't set `collapsed = true` and `dense = true` together — items become invisible.

## Manifest Configuration

```xml
<!-- Required: Meta VR SDK version targeting -->
<metavr:uses-metavr-sdk
  metavr:minSdkVersion="69"
  metavr:targetSdkVersion="207" />

<!-- Legacy form, still accepted; declared alongside the current one -->
<horizonos:uses-horizonos-sdk
  horizonos:minSdkVersion="69"
  horizonos:targetSdkVersion="207" />

<!-- Supported Quest devices -->
<meta-data
  android:name="com.oculus.supportedDevices"
  android:value="quest2|questpro|quest3|quest3s" />

<!-- Panel default size (2D app window dimensions) -->
<layout android:defaultHeight="640dp" android:defaultWidth="1024dp" />
```

- `metavr:minSdkVersion` is the oldest installable OS release.
- `metavr:targetSdkVersion` opts into behavior through that OS release and
  should advance independently of the minimum.
- These values are separate from Android's `minSdk` and `targetSdk`.

## Look and Pinch

Look and pinch is the **default input method on devices that ship without controllers**, so these
are requirements, not preferences. Two OS facts drive all of them:

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

Requires **Jetpack Compose 1.10.0 or newer** for the system to infer a composable's real shape; on
older versions it falls back to rectangular bounds. This template resolves Compose 1.11.0.

Native tooltips never appear under look and pinch. A tooltip carrying real meaning has to move to
the Gaze SDK, which is not a dependency of this template.

## Platform Constraints

These Android features are NOT available on the OS:
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
- **Change app name:** Update `app_name` in `app/src/main/res/values/strings.xml`
- **Change app package:** Update `namespace`/`applicationId` in `app/build.gradle.kts`, `android:name` in manifest, and Kotlin package declaration
