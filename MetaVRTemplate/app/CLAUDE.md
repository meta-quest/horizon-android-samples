# App Source Guide

## Architecture

Single-activity Compose app. `MainActivity` is the only activity — all UI is built with Jetpack Compose inside `setContent { }`.

### File Layout

- `MainActivity.kt` — App entry point and all composables (intentionally single-file for template simplicity)
- `AndroidManifest.xml` — Activity registration, Meta VR SDK targeting, panel dimensions
- `res/drawable/ic_meta_logo.xml` — Meta logo vector used on the Home tab

## UI Structure

```
MainActivity (ComponentActivity)
  └─ UiSetTheme(darkColorScheme())
      └─ MetaVRApp()
          ├─ LazyColumn (side nav, 160dp wide)
          │   └─ SideNavItem × 3 (Home, Features, Tools)
          └─ AnimatedContent (tab content area)
              ├─ HomeContent()
              ├─ FeaturesContent()
              └─ ToolsContent()
```

Tab switching uses `AnimatedContent` with `fadeIn() togetherWith fadeOut()` transitions.

## Adding a New Tab

1. Add a new `SideNavItem` in the `LazyColumn` with the next index
2. Add a new composable function (e.g., `SettingsContent()`)
3. Add a `when` branch in the `AnimatedContent` block
4. Use `ScrollableTabContent { }` as the wrapper for scrollable content with gradient fade

## Key Composables

| Composable | Purpose |
|---|---|
| `MetaVRApp()` | Root layout: side nav + animated content area |
| `ScrollableTabContent()` | Scrollable column with bottom gradient fade overlay |
| `InfoCard()` | Reusable card with accent icon, title, description |
| `HomeContent()` | Welcome tab with Meta logo and getting-started info |
| `FeaturesContent()` | Platform features overview |
| `ToolsContent()` | Developer tools overview |

## Colors

There are no hard-coded colors. Card icons are tinted with the first stop of
`UiSetTheme.colorScheme.accent.container`, and the scroll fade ends on the last stop of
`UiSetTheme.colorScheme.background.container`, so both follow the color scheme passed to
`UiSetTheme`.

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
Update `app_name` in `app/src/main/res/values/strings.xml`.

### Change app package
Update in three places:
1. `android:name` in `AndroidManifest.xml` activity declaration
2. `namespace` and `applicationId` in `app/build.gradle.kts`
3. Kotlin `package` declaration and directory structure
