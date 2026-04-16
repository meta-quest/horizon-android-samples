/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.meta.horizon.samples.horizonostemplate

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.meta.spatial.uiset.card.SecondaryCard
import com.meta.spatial.uiset.navigation.SpatialSideNavItem
import com.meta.spatial.uiset.theme.LocalColorScheme
import com.meta.spatial.uiset.theme.SpatialTheme

private val AccentBlue = Color(0xFF47A5FA)
private val PanelBottom = Color(0xFF272727)

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      SpatialTheme {
        CompositionLocalProvider(
            LocalContentColor provides LocalColorScheme.current.primaryAlphaBackground,
        ) {
          HorizonOSApp()
        }
      }
    }
  }
}

@Composable
fun HorizonOSApp() {
  var selectedTab by remember { mutableIntStateOf(0) }

  Row(
      modifier =
          Modifier.fillMaxSize()
              .clip(SpatialTheme.shapes.large)
              .background(brush = LocalColorScheme.current.panel)
              .padding(24.dp),
      horizontalArrangement = Arrangement.spacedBy(24.dp),
  ) {
    LazyColumn(
        modifier = Modifier.width(160.dp).fillMaxHeight(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      item {
        SpatialSideNavItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            onClick = { selectedTab = 0 },
            primaryLabel = "Home",
            selected = selectedTab == 0,
        )
      }
      item {
        SpatialSideNavItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Features") },
            onClick = { selectedTab = 1 },
            primaryLabel = "Features",
            selected = selectedTab == 1,
        )
      }
      item {
        SpatialSideNavItem(
            icon = { Icon(Icons.Default.Build, contentDescription = "Tools") },
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
    Box(
        modifier =
            Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, PanelBottom),
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
          tint = AccentBlue,
      )
      Spacer(modifier = Modifier.width(12.dp))
      Text(
          text = title,
          style = SpatialTheme.typography.headline3,
          fontWeight = FontWeight.Bold,
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = description,
        style = SpatialTheme.typography.body1,
        color = LocalColorScheme.current.secondaryAlphaBackground,
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
          text = "Build for Meta Horizon OS",
          style = SpatialTheme.typography.headline1,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
          text = "Build apps and experiences for Meta Horizon OS",
          style = SpatialTheme.typography.body1,
          color = LocalColorScheme.current.secondaryAlphaBackground,
          textAlign = TextAlign.Center,
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text =
            "Meta Horizon OS is an Android-based operating system designed for " +
                "mixed reality. Build 2D panel apps using standard Android APIs " +
                "and Jetpack Compose, or create fully immersive 3D experiences " +
                "with the Meta Spatial SDK.",
        style = SpatialTheme.typography.body1,
    )
    InfoCard(
        icon = Icons.Default.PlayArrow,
        title = "Getting started",
        description =
            "This template gives you a minimal Android project configured for " +
                "Meta Horizon OS. Modify this activity to start building your app. " +
                "Your app runs as a spatial panel in Horizon OS and supports " +
                "multi-window layouts by default.",
    )
    InfoCard(
        icon = Icons.Default.Star,
        title = "Going immersive",
        description =
            "To add immersive 3D content, add the Meta Spatial SDK dependencies " +
                "to your build.gradle.kts and extend AppSystemActivity instead of " +
                "ComponentActivity.",
    )
  }
}

@Composable
fun FeaturesContent() {
  ScrollableTabContent {
    Text(
        text = "Features",
        style = SpatialTheme.typography.headline1,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = "What makes developing for Meta Horizon OS unique",
        style = SpatialTheme.typography.body1,
        color = LocalColorScheme.current.secondaryAlphaBackground,
    )
    InfoCard(
        icon = Icons.Default.Info,
        title = "Spatial panels",
        description =
            "Apps run as spatial panels that float in the user's environment. " +
                "Users can resize and reposition panels—giving your app more " +
                "screen real estate than any phone or tablet.",
    )
    InfoCard(
        icon = Icons.Default.Create,
        title = "Hand tracking and controllers",
        description =
            "Horizon OS supports both hand tracking and controllers. Standard " +
                "Android touch and pointer events work automatically for 2D " +
                "panel apps.",
    )
    InfoCard(
        icon = Icons.Default.Place,
        title = "Mixed reality and passthrough",
        description =
            "Build experiences that blend digital content with the real world. " +
                "Use passthrough to let users see their physical surroundings " +
                "while interacting with your app.",
    )
    InfoCard(
        icon = Icons.Default.Notifications,
        title = "Spatial audio",
        description =
            "Place sounds in 3D space so audio feels like it comes from a " +
                "real location in the user's environment. Standard Android " +
                "audio APIs are supported for panel apps.",
    )
    InfoCard(
        icon = Icons.Default.Search,
        title = "Scene understanding",
        description =
            "Access the user's room layout—including walls, floors, furniture, " +
                "and other surfaces—to anchor content to the physical world " +
                "using the Scene API.",
    )
  }
}

@Composable
fun ToolsContent() {
  ScrollableTabContent {
    Text(
        text = "Developer tools",
        style = SpatialTheme.typography.headline1,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = "Tools to help you build, test, and debug your apps.",
        style = SpatialTheme.typography.body1,
        color = LocalColorScheme.current.secondaryAlphaBackground,
    )
    InfoCard(
        icon = Icons.Default.Build,
        title = "Meta Horizon plugin for Android Studio",
        description =
            "Create projects from templates, inspect data models, and access " +
                "troubleshooting tools directly in Android Studio.",
    )
    InfoCard(
        icon = Icons.Default.PlayArrow,
        title = "Meta Spatial Simulator",
        description =
            "Test your app in a simulated Horizon OS environment on your " +
                "desktop—no headset required. Supports controller emulation " +
                "and room setup.",
    )
    InfoCard(
        icon = Icons.Default.Settings,
        title = "Meta Quest Developer Hub",
        description =
            "Manage your device, capture logs, take screenshots, and monitor " +
                "performance from your desktop. Supports wireless ADB connections.",
    )
    InfoCard(
        icon = Icons.Default.Search,
        title = "Data Model Inspector",
        description =
            "Inspect and debug your app's ECS data model in real time. " +
                "View entities, components, and system state while the app runs.",
    )
  }
}
