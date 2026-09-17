# CookVrx - Spatial Cookbook

CookVrx is a mobile-first Jetpack Compose cookbook that promotes contextual tools into spatial
windows on Meta Quest. Browse recipes in the main panel, open one detail card to the right, then
enter Cook Mode to place ingredients on the left and timers on the right.

The app declares no more than two child windows at once. Every child uses `WindowFallback.Inline`,
so phones and unsupported Horizon OS versions keep a complete single-surface flow.

## SDK versions

CookVrx imports `com.meta.metavrx:metavrx-bom` and declares
`com.meta.metavrx.layout:layout-compose-compat`,
`com.meta.metavrx.layout:layout-window-compose-compat`, and
`com.meta.metavrx.uiset:uiset-compose-compat` without versions. The complete
sample therefore advances only when the validated BOM is updated.

## Patterns demonstrated

- Conditional `SpatialWindow` composition for browse and cook modes
- Stable window keys and mirrored `WindowAnchor.Start` / `WindowAnchor.End` placement
- `WindowFallback.Inline` with a dedicated compact mobile layout
- Theme propagation through `SpatialScene(theme = ...)`
- Injectable dimensions profiles for headset-specific scale and layout policy
- Asset-driven recipe loading from `app/src/main/assets/recipes/`
- Shared Compose state for step progress, ingredient checks, and concurrent timers

## Building and running

Open the project in Android Studio, or build it from the command line:

```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Troubleshooting

### Gradle uses the wrong JDK

This sample uses Gradle 8.7, which cannot run on JDK 25. If Gradle stops with a bare JDK version
such as `25.0.4.1`, set `JAVA_HOME` to your JDK 17 installation directory before running the Gradle
wrapper again. In Android Studio, set **Gradle JDK** to JDK 17 under **Settings** > **Build,
Execution, Deployment** > **Build Tools** > **Gradle**.
