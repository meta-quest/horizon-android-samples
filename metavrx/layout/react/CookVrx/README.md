# CookVrx React

CookVrx is a mobile-first cookbook that promotes contextual tools into spatial
windows on Meta Quest. Browse recipes in the main panel, open one detail card to
the right, then enter Cook Mode to place ingredients on the left and timers on
the right.

The app declares no more than two child windows at once. Every child uses the
default `fallback="inline"`, so phones and Horizon OS versions older than v207
keep a complete single-surface flow.

## How spatial promotion reaches JS

On a Quest, selecting a recipe places the detail window beside the main panel and
cook mode places ingredients and timers. That path depends on both
`@metavr/layout-compat` and `@metavr/layout-window-compat` being direct,
autolinked app dependencies.

Under the New Architecture the JS↔native bindings for the scene and window
TurboModules (`…SpecJSI`) are emitted by **codegen**, which only runs for packages
React Native discovers as direct app dependencies. Each package owns its own
schema and native registration, so both packages must be listed by the app.
`DefaultTurboModuleManagerDelegate::getTurboModule` returns that generated
provider's result and has no fallback to the Java `ReactPackage` list, so a
hand-registered package gets no binding at all: `TurboModuleRegistry.get(
'SpatialWindowModule')` returns `null`, and silently, since `SpatialSceneProvider`
reads an absent module as "spatial unavailable". The Fabric half is unaffected
either way — view managers resolve from the Java package list — which is why an
un-autolinked build still renders every window, just always inline.

So the sample lets the package autolink and takes it from the generated
`PackageList`; see [How it consumes the SDK](#how-it-consumes-the-sdk) for how the AAR and the codegen output
divide the work.

## Patterns demonstrated

- Conditional `SpatialWindow` composition for browse and cook modes
- Stable window labels (`detail`, `ingredients`, `timers`) and mirrored
  `anchor="start"` / `anchor="end"` placement with logical offsets
- Inline fallback with a dedicated compact single-surface layout, selected on
  `useSpatialScene().isSpatialAvailable`
- `useSpatialWindowState(label)` to switch a panel between its promoted
  (`placement === 'spatial'`) and inline sizing
- Theme propagation through a React context that reaches promoted windows
- Injectable dimensions profiles (`CookbookDimensions`) for headset-specific
  scale and layout policy
- Shared state (`useCookVrx`) for step progress, ingredient checks, and
  concurrent timers across windows

React Native core ships no gradient primitive and no icon set, so the plate
artwork uses flat colour bands instead of a linear gradient and the close
affordance is a text glyph.

## How it consumes the SDK

Consumes the published SDK the way any external app does:

- **JS** — `package.json` depends directly on `@metavr/layout-window-compat` and
  its version-matched base package.
- **Native** — `android/app/build.gradle.kts` declares
  `implementation(libs.metavrx.layout.window.react.compat)`, the versionless
  `com.meta.metavrx.layout:layout-window-react-compat` AAR under the
  `com.meta.metavrx:metavrx-bom` platform. Its POM resolves
  `com.meta.metavrx.layout:layout-react-compat` at the exact same version; together they
  carry `SpatialScenePackage`, `SpatialWindowPackage`, the TurboModules, and the
  `ViewManager`. Both AARs are Kotlin only, so neither ships a `.so` and the app has
  nothing extra to package. The window AAR contributes the volumetric-window
  permission; runtime capability detection enables spatial placement on Horizon OS
  v207 and newer. Published samples resolve the AARs from Maven Central.
- **Registration** — the app applies the `com.facebook.react` Gradle plugin (New
  Architecture codegen, autolinking, and the React Native / Hermes versions, all taken from
  `node_modules`). The base and window npm packages each autolink their matching
  `ReactPackage`, so `MainApplication` takes both from the generated `PackageList`
  rather than adding either by hand.

  Each package carries only its own codegen specs. This emits the scene binding
  from `@metavr/layout-compat` and the window binding plus Fabric component from
  `@metavr/layout-window-compat`; see
  [How spatial promotion reaches JS](#how-spatial-promotion-reaches-js). Each
  package's `android/` project is a thin autolinking shim that declares its
  matching AAR and compiles React Native's generated output from that package's `specs/`.


## Layout

```
cookvrx_rn/
├── App.tsx, index.js, app.json    ← React Native app (imports both Layout packages)
├── src/                           ← data, state, theme, UI panels
├── assets/recipes/*.json          ← recipe content as bundled JSON
├── package.json, metro.config.js, babel.config.js
└── android/                       ← Gradle host app
    ├── settings.gradle.kts        ← plugin and dependency repositories
    └── app/                       ← MainApplication/MainActivity + AAR dep
```

## Requirements

| Tool / setting     | Version                              |
| ------------------ | ------------------------------------ |
| JDK                | 17                                   |
| Node.js            | 20.19.4 or newer                     |
| Android NDK        | 27.1.12297006                        |
| compileSdk         | 36                                   |
| minSdk / targetSdk | 29 / 32                              |
| ABI                | `arm64-v8a` only                     |
| Gradle             | 8.13, via the checked-in `./gradlew` |
| Android Gradle Plugin | 8.6.1                             |
| Horizon OS         | v207 or newer for spatial placement  |

`minSdk` is 29 because the SDK's autolinked `android/` project declares 29 and the
manifest merger refuses a consumer below its libraries.

Below Horizon OS v207 the app installs and runs, but `SpatialCapability` reports
spatial unavailable and every `SpatialWindow` takes its `fallback="inline"` path —
the flow is complete, but nothing spatial is demonstrated.

## Building and running

Install the JavaScript dependencies, then build or install the Android app:

```bash
npm install
cd android
./gradlew assembleDebug
./gradlew installDebug
```

Both SDK packages must resolve as *direct* dependencies of the app:

```bash
npm ls @metavr/layout-compat @metavr/layout-window-compat
```

New Architecture codegen runs only for direct dependencies, and each package
carries its own specs and native registration. If one is missing the build still
succeeds and the app still launches — it just renders every window inline, with no
error anywhere. See
[How spatial promotion reaches JS](#how-spatial-promotion-reaches-js).

For iterative development, start Metro from the sample root and run the app:

```bash
npm start                                       # terminal 1
adb reverse tcp:8081 tcp:8081                   # terminal 2, lost on every adb restart
npx react-native run-android --no-packager
```

`adb reverse` is required, not a convenience. Debug builds permit cleartext HTTP to
`localhost`, `127.0.0.1` and `10.0.2.2` only — see
`android/app/src/debug/res/xml/network_security_config.xml`, which the debug variant
alone can see — so Metro reached over a LAN address is refused, and it surfaces as a
failed bundle download rather than as a security setting. Reverse-forwarding the port
keeps the dev server on `localhost` and works over wireless adb too. To serve on a LAN
address instead, add that host to that file explicitly; there is no wildcard or CIDR
form.
## Coming from the Compose sample

You do not need this section to read the sample. If you already know the Compose
version, this is how the two line up:

| Compose                              | React Native                                |
| ------------------------------------ | ------------------------------------------- |
| `CookVrxActivity` + `SpatialScene`    | `App.tsx` + `SpatialSceneProvider`          |
| `SpatialAppRoot` / `AppRoot`          | `SpatialAppRoot` / `MobileAppRoot`          |
| `CookVrxViewModel`                    | `src/state/useCookVrx.ts`                   |
| `RecipeRepository` (`AssetManager`)   | `src/data/recipes.ts` (bundled JSON imports)|
| `CookVrxTheme` / `CookbookDimensions` | `src/theme/theme.tsx`                       |
| `LocalPromoted`                       | `useSpatialWindowState(label).placement`    |
| `WindowFallback.Inline`               | `fallback="inline"` (the default)           |
| UISet buttons / cards                 | `src/ui/primitives.tsx`                     |
