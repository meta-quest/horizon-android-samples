# App Source Guide

## Architecture

Single-activity Compose app. `MainActivity` is the only activity — all UI is built with Jetpack Compose inside `setContent { }`.

### File Layout

- `MainActivity.kt` — App entry point and all composables (intentionally single-file for template simplicity)
- `AndroidManifest.xml` — Activity registration, Horizon OS SDK targeting, panel dimensions
- `res/drawable/ic_meta_logo.xml` — Meta logo vector used on the Home tab

## UI Structure

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

Tab switching uses `AnimatedContent` with `fadeIn() togetherWith fadeOut()` transitions.

## Adding a New Tab

1. Add a new `SpatialSideNavItem` in the `LazyColumn` with the next index
2. Add a new composable function (e.g., `SettingsContent()`)
3. Add a `when` branch in the `AnimatedContent` block
4. Use `ScrollableTabContent { }` as the wrapper for scrollable content with gradient fade

## Key Composables

| Composable | Purpose |
|---|---|
| `HorizonOSApp()` | Root layout: side nav + animated content area |
| `ScrollableTabContent()` | Scrollable column with bottom gradient fade overlay |
| `InfoCard()` | Reusable card with accent icon, title, description |
| `HomeContent()` | Welcome tab with Meta logo and getting-started info |
| `FeaturesContent()` | Platform features overview |
| `ToolsContent()` | Developer tools overview |

## Color Constants

```kotlin
val AccentBlue = Color(0xFF47A5FA)   // Icon tint color
val PanelBottom = Color(0xFF272727)  // Bottom of panel gradient (for fade overlay)
```

These match the UISet dark theme palette. If you change the theme, update `PanelBottom` to match the bottom color of `LocalColorScheme.current.panel`.

## Common Modifications

### Change panel dimensions
Edit `AndroidManifest.xml`:
```xml
<layout android:defaultHeight="640dp" android:defaultWidth="1024dp" />
```

### Add a new dependency
Add to `gradle/libs.versions.toml` under `[libraries]`, then reference in `app/build.gradle.kts`:
```kotlin
implementation(libs.your.new.library)
```

### Change app name
Update `android:label` in `AndroidManifest.xml`.

### Change app package
Update in three places:
1. `android:name` in `AndroidManifest.xml` activity declaration
2. `namespace` and `applicationId` in `app/build.gradle.kts`
3. Kotlin `package` declaration and directory structure
