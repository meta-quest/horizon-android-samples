# GalleryVrx

A Jetpack Compose catalog for the **Meta VR UI Set**. Browse and search the component families,
switch between light and dark themes, and use Theme Lab to inspect density, color, and local
component customization.

## Build path
Standard Android — a normal Jetpack Compose app running as a 2D panel on Meta Horizon OS.
No Unity, Unreal, or Spatial SDK needed.

## SDK
**Meta VR UI Set** — Jetpack Compose. Declared in `gradle/libs.versions.toml`:
- `com.meta.metavrx:metavrx-bom`
- `com.meta.metavrx.uiset:uiset-compose-compat`

The BOM selects the validated UI Set version.

## What it highlights
- Buttons, cards, navigation, selection controls, sliders, dialogs, dropdowns, and text input
- `UiSetTheme` with standard and compact density plus light and dark color schemes
- Local component overrides, the semantic type scale, and the `Icons.Regular` collection
- A working gallery shell built from UI Set components, including `SideNavItem` and `SearchBar`

## Build & run
```bash
./gradlew assembleDebug    # build
./gradlew installDebug     # build and install to a connected device
```
Or open the project in Android Studio, let it sync, and press Run.

## Where things live
- `app/src/main/java/com/example/metavrx/uiset/gallery/` — navigation, screens, and theme controls
- `README.md` — component and theme coverage

## SDK reference for your agent
The authoritative SDK documentation and agent skills come from the **metavr CLI**. Install it
(https://developers.meta.com/horizon/install-cli), then run `metavr init` in the project to
set up SDK docs and skills for your agent. Command reference:
```bash
npx -y metavr --markdown-help
```
Public docs: https://developers.meta.com/horizon/documentation/
