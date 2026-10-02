/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.meta.metavr.samples.metavrtemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.card.SecondaryCard
import metavrx.uiset.compose.navigation.SideNavItem
import metavrx.uiset.compose.theme.LocalContentColors
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.darkColorScheme
import metavrx.uiset.compose.theme.icons.Icons

// Look and pinch is the default input method on devices that ship without controllers, and it is
// less precise than a controller ray. Interactive targets must be at least 48dp; 60dp is the
// recommended size, roughly 3 degrees of visual angle at the default panel distance.
private val LookAndPinchMinTargetHeight = 60.dp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      // The Meta VR UI Set provides panel colors, shapes, and typography on top of Compose.
      UiSetTheme(colorScheme = darkColorScheme()) { MetaVRApp() }
    }
  }
}

@Composable
fun MetaVRApp() {
  var selectedTab by remember { mutableIntStateOf(0) }

  // The OS presents the activity as a resizable panel, so paint the UI Set panel
  // background explicitly and keep the content responsive to the available bounds.
  Row(
      modifier =
          Modifier.fillMaxSize()
              .background(UiSetTheme.colorScheme.background.container.brush)
              .padding(24.dp),
      horizontalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    LazyColumn(
        modifier = Modifier.width(160.dp).fillMaxHeight(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      item {
        SideNavItem(
            modifier = Modifier.heightIn(min = LookAndPinchMinTargetHeight),
            icon = { Icon(Icons.Regular.Home, contentDescription = "Home") },
            onClick = { selectedTab = 0 },
            primaryLabel = "Home",
            selected = selectedTab == 0,
        )
      }
      item {
        SideNavItem(
            modifier = Modifier.heightIn(min = LookAndPinchMinTargetHeight),
            icon = { Icon(Icons.Regular.Star, contentDescription = "Features") },
            onClick = { selectedTab = 1 },
            primaryLabel = "Features",
            selected = selectedTab == 1,
        )
      }
      item {
        SideNavItem(
            modifier = Modifier.heightIn(min = LookAndPinchMinTargetHeight),
            icon = { Icon(Icons.Regular.Settings, contentDescription = "Tools") },
            onClick = { selectedTab = 2 },
            primaryLabel = "Tools",
            selected = selectedTab == 2,
        )
      }
    }

    AnimatedContent(
        targetState = selectedTab,
        modifier = Modifier.weight(1f).fillMaxHeight(),
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "tab_content",
    ) { tab ->
      when (tab) {
        0 -> HomeContent()
        1 -> FeaturesContent()
        2 -> ToolsContent()
      }
    }
  }
}

@Composable
fun ScrollableTabContent(content: @Composable () -> Unit) {
  Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier =
            Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 8.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      content()
    }
    // Fades into the last stop of the panel gradient, so it follows the color scheme.
    val panelBottom = UiSetTheme.colorScheme.background.container.colors.asList().last()
    Box(
        modifier =
            Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, panelBottom),
                    ),
                ),
    )
  }
}

@Composable
fun InfoCard(icon: ImageVector, title: String, description: String) {
  SecondaryCard(modifier = Modifier.fillMaxWidth()) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = UiSetTheme.colorScheme.accent.container.colors.asList().first(),
      )
      Spacer(modifier = Modifier.width(12.dp))
      Text(
          text = title,
          style = UiSetTheme.typography.title,
          color = LocalContentColors.current.primary,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = description,
        style = UiSetTheme.typography.body,
        color = LocalContentColors.current.secondary,
    )
  }
}

@Composable
fun HomeContent() {
  ScrollableTabContent {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
          painter = painterResource(id = R.drawable.ic_meta_logo),
          contentDescription = "Meta logo",
          modifier = Modifier.size(128.dp),
      )
      Spacer(modifier = Modifier.height(8.dp))
      Text(
          text = "Build for mixed reality",
          style = UiSetTheme.typography.headline,
          color = UiSetTheme.colorScheme.background.content.primary,
          textAlign = TextAlign.Center,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
          text = "Build apps and experiences for mixed reality",
          style = UiSetTheme.typography.body,
          color = UiSetTheme.colorScheme.background.content.secondary,
          textAlign = TextAlign.Center,
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text =
            "The OS is Android-based and designed for mixed reality. " +
                "Build 2D panel apps using standard Android APIs " +
                "and Jetpack Compose.",
        style = UiSetTheme.typography.body,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
    InfoCard(
        icon = Icons.Regular.Play,
        title = "Getting started",
        description =
            "This template gives you a minimal Android project configured for " +
                "2D panels. Modify this activity to start building your app. " +
                "Your app runs as a panel and supports multi-window layouts " +
                "by default.",
    )
  }
}

@Composable
fun FeaturesContent() {
  ScrollableTabContent {
    Text(
        text = "Features",
        style = UiSetTheme.typography.headline,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
    Text(
        text = "What makes developing for mixed reality unique",
        style = UiSetTheme.typography.body,
        color = UiSetTheme.colorScheme.background.content.secondary,
    )
    InfoCard(
        icon = Icons.Regular.Info,
        title = "Spatial panels",
        description =
            "Apps run as spatial panels that float in the user's environment. " +
                "Users can resize and reposition panels—giving your app more " +
                "screen real estate than any phone or tablet.",
    )
    InfoCard(
        icon = Icons.Regular.HandCursor,
        title = "Hand tracking and controllers",
        description =
            "The OS supports both hand tracking and controllers. Standard " +
                "Android touch and pointer events work automatically for 2D " +
                "panel apps.",
    )
    InfoCard(
        icon = Icons.Regular.Environment,
        title = "Mixed reality and passthrough",
        description =
            "Build experiences that blend digital content with the real world. " +
                "Use passthrough to let users see their physical surroundings " +
                "while interacting with your app.",
    )
    InfoCard(
        icon = Icons.Regular.VolumeOn,
        title = "Spatial audio",
        description =
            "Place sounds in 3D space so audio feels like it comes from a " +
                "real location in the user's environment. Standard Android " +
                "audio APIs are supported for panel apps.",
    )
  }
}

@Composable
fun ToolsContent() {
  ScrollableTabContent {
    Text(
        text = "Developer tools",
        style = UiSetTheme.typography.headline,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
    Text(
        text = "Tools to help you build, test, and debug your apps.",
        style = UiSetTheme.typography.body,
        color = UiSetTheme.colorScheme.background.content.secondary,
    )
    InfoCard(
        icon = Icons.Regular.Computer,
        title = "Meta VR Android Studio Plugin",
        description =
            "Create projects from templates and access troubleshooting tools " +
                "directly in Android Studio.",
    )
    InfoCard(
        icon = Icons.Regular.Play,
        title = "Meta Spatial Simulator",
        description =
            "Test your app in a simulated device environment on your " +
                "desktop—no headset required. Supports controller emulation " +
                "and room setup.",
    )
    InfoCard(
        icon = Icons.Regular.Settings,
        title = "Meta Quest Developer Hub",
        description =
            "Manage your device, capture logs, take screenshots, and monitor " +
                "performance from your desktop. Supports wireless ADB connections.",
    )
  }
}
