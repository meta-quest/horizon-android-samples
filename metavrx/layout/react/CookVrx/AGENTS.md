# CookVrx React

A mobile-compatible React Native cookbook that promotes contextual tools into spatial windows on
Meta Quest, built with the **Meta VR Layout SDK**. Browse recipes in the main panel, open one
detail card to the right, then enter Cook Mode to place ingredients on the left and timers on the
right.

## Build path
Standard Android — a normal React Native Android app. It builds and runs like any React Native
project and becomes spatial on Meta Horizon OS through the Layout SDK. No Unity, Unreal, or
immersive-app framework is needed.

## SDK
**Meta VR Layout SDK** — React Native. Two version-coupled packages, each a
JS/native pair:
- JS: `@metavr/layout-compat` and `@metavr/layout-window-compat` (see `package.json`)
- Native: `com.meta.metavrx.layout:layout-react-compat` and
  `com.meta.metavrx.layout:layout-window-react-compat`
  (Maven AARs; `android/gradle/libs.versions.toml` declares the window artifact, whose POM pins the
  matching base AAR)

Both npm packages must be direct app dependencies: React Native autolinking discovers each one
independently, and each contributes its own native registration and codegen schema.

## What it highlights
- The Layout SDK surface in React: conditional `SpatialWindow` composition, stable window keys,
  mirrored start/end anchoring, and inline fallback.
- Consuming a version-coupled base + window artifact pair across the JS/native boundary.
- Applying the `com.facebook.react` Gradle plugin rather than hand-pinning `react-android` /
  `hermes-android` — the plugin derives both from the installed `react-native`, which is what keeps
  the native runtime, Hermes, and the JS Metro serves in agreement under React Native 0.85's
  mandatory New Architecture.
- Metro `watchFolders` + pinned `react` / `react-native` resolution, which also supports local
  development when npm installs an SDK dependency as a symlink.

## Build & run
```bash
npm start                  # start the Metro bundler (leave running)
adb reverse tcp:8081 tcp:8081
npx react-native run-android --no-packager
```
Gradle directly, if you prefer: `cd android && ./gradlew installDebug`.

`adb reverse` is required: debug builds allow cleartext to `localhost` / `127.0.0.1` / `10.0.2.2`
only (`android/app/src/debug/res/xml/network_security_config.xml`), so Metro served on a LAN address
is refused and reads as a failed bundle download.

## Where things live
- `App.tsx`, `src/`, `assets/` — the React Native app; `index.js` and the Metro/Babel configs at
  the root
- `android/` — Gradle host app, React Native Gradle plugin, and `PackageList` autolinking
- `README.md` — what the sample demonstrates, how the SDK is consumed, and the build walkthrough

## SDK reference for your agent
The authoritative SDK documentation and agent skills come from the **metavr CLI**. Install it
(https://developers.meta.com/horizon/install-cli), then run `metavr init` in the project to
set up SDK docs and skills for your agent. Command reference:
```bash
npx -y metavr --markdown-help
```
Public docs: https://developers.meta.com/horizon/documentation/
