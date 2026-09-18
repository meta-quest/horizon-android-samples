# CookVrx

A mobile-compatible Jetpack Compose cookbook that promotes contextual tools into spatial
windows on Meta Quest, built with the **Meta VR Layout SDK**. Browse recipes in the main
panel, open one detail card to the right, then enter Cook Mode to place ingredients on the
left and timers on the right.

## Build path
Standard Android — a normal Jetpack Compose app. It builds and runs like any Android
project and becomes spatial on Meta Horizon OS through the Layout SDK. No Unity, Unreal, or
immersive-app framework is needed.

## SDK
**Meta VR Layout SDK** — Jetpack Compose. Declared in
`gradle/libs.versions.toml`:
- `com.meta.metavrx.layout:layout-compose-compat`
- `com.meta.metavrx.layout:layout-window-compose-compat`

The sample imports `com.meta.metavrx:metavrx-bom` and declares all three
MetaVRX artifacts without versions. Public builds therefore follow the exact
SDK versions selected by the BOM.

## What it highlights
The sample is a tour of the Layout SDK's window composition and placement model. It declares
at most two child windows at once, and every child uses `WindowFallback.Inline` — when a
window can't be placed (on a phone, or an unsupported Horizon OS version) its content renders
inline in the main panel instead of disappearing, which is what keeps the app fully usable as
a single-surface mobile app. Specifically:

- Conditional `SpatialWindow` composition for browse vs. cook modes
- Stable window keys and mirrored `WindowAnchor.Start` / `WindowAnchor.End` placement
- `WindowFallback.Inline` with a dedicated compact mobile layout
- Theme propagation through `SpatialScene(theme = ...)`
- Injectable dimensions profiles for headset-specific scale and layout policy
- Asset-driven recipe loading and shared Compose state for steps, ingredients, and timers

## Build & run
```bash
./gradlew assembleDebug    # build
./gradlew installDebug     # build and install to a connected device
```
Or open the project in Android Studio, let it sync, and press Run.

## Where things live
- `app/src/main/kotlin/com/example/metavrx/layout/cookvrx/` — `CookVrxActivity`, `data/`, `state/`, `ui/`
- `app/src/main/assets/recipes/` — recipe JSON
- `README.md` — full pattern walkthrough

## SDK reference for your agent
The authoritative SDK documentation and agent skills come from the **metavr CLI**. Install it
(https://developers.meta.com/horizon/install-cli), then run `metavr init` in the project to
set up SDK docs and skills for your agent. Command reference:
```bash
npx -y metavr --markdown-help
```
Public docs: https://developers.meta.com/horizon/documentation/
