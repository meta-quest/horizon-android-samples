/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.metavrx.uiset.gallery.R
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import com.example.metavrx.uiset.gallery.ui.icon
import metavrx.uiset.compose.button.ButtonShelf
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.button.TextTileButton
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun ButtonsScreen() {
  var shelfSelected by remember { mutableStateOf(false) }
  var passthroughSelected by remember { mutableStateOf(true) }
  var immersiveSelected by remember { mutableStateOf(false) }

  Screen {
    Demo("Labelled", "The five button roles, each with an optional leading icon.") {
      DemoRow {
        LabelButton(label = "Primary", onClick = {}, style = ButtonStyle.Primary)
        LabelButton(label = "Secondary", onClick = {}, style = ButtonStyle.Secondary)
        LabelButton(label = "Bordered", onClick = {}, style = ButtonStyle.Bordered)
        LabelButton(label = "Borderless", onClick = {}, style = ButtonStyle.Borderless)
        LabelButton(label = "Destructive", onClick = {}, style = ButtonStyle.Destructive)
      }
    }

    Demo("With leading icon") {
      DemoRow {
        LabelButton(
            label = "Add item",
            onClick = {},
            style = ButtonStyle.Primary,
            leadingIcon = icon { Icons.Regular.Add },
        )
        LabelButton(
            label = "Settings",
            onClick = {},
            style = ButtonStyle.Secondary,
            leadingIcon = icon { Icons.Regular.Settings },
        )
        LabelButton(
            label = "Delete",
            onClick = {},
            style = ButtonStyle.Destructive,
            leadingIcon = icon { Icons.Regular.Close },
        )
      }
    }

    Demo("Disabled", "Every button honours `enabled`.") {
      DemoRow {
        LabelButton(
            label = "Primary",
            onClick = {},
            style = ButtonStyle.Primary,
            enabled = false,
        )
        LabelButton(
            label = "Secondary",
            onClick = {},
            style = ButtonStyle.Secondary,
            enabled = false,
        )
        LabelButton(
            label = "Destructive",
            onClick = {},
            style = ButtonStyle.Destructive,
            enabled = false,
        )
      }
    }

    Demo("Icon only", "Circular icon buttons use the same five semantic styles.") {
      DemoRow {
        IconButton(
            icon = icon { Icons.Regular.Add },
            onClick = {},
            contentDescription = stringResource(R.string.icon_add_content_description),
            style = ButtonStyle.Primary,
        )
        IconButton(
            icon = icon { Icons.Regular.Settings },
            onClick = {},
            contentDescription = stringResource(R.string.icon_settings_content_description),
            style = ButtonStyle.Secondary,
        )
        IconButton(
            icon = icon { Icons.Regular.Bookmark },
            onClick = {},
            contentDescription = stringResource(R.string.icon_bookmark_content_description),
            style = ButtonStyle.Bordered,
        )
        IconButton(
            icon = icon { Icons.Regular.Settings },
            onClick = {},
            contentDescription = stringResource(R.string.icon_settings_content_description),
            style = ButtonStyle.Borderless,
        )
        IconButton(
            icon = icon { Icons.Regular.Close },
            onClick = {},
            contentDescription = stringResource(R.string.icon_close_content_description),
            style = ButtonStyle.Destructive,
        )
      }
    }

    Demo("Button shelf", "A selectable icon + label cell. Stateful — tap to toggle.") {
      DemoRow {
        ButtonShelf(
            icon = icon { Icons.Regular.Bookmark },
            label = "Bookmark",
            selected = shelfSelected,
            onSelectionChange = { shelfSelected = it },
        )
        ButtonShelf(
            icon = icon { Icons.Regular.Settings },
            label = "Settings",
            selected = false,
            onSelectionChange = {},
        )
        ButtonShelf(
            icon = icon { Icons.Regular.Add },
            label = "Disabled",
            enabled = false,
            selected = false,
            onSelectionChange = {},
        )
      }
    }

    Demo("Text tile", "A larger selectable tile with an optional secondary label.") {
      DemoRow {
        TextTileButton(
            label = "Passthrough",
            secondaryLabel = "Mixed reality",
            selected = passthroughSelected,
            onSelectionChange = { passthroughSelected = it },
            icon = icon { Icons.Regular.Settings },
        )
        TextTileButton(
            label = "Immersive",
            secondaryLabel = "Fully virtual",
            selected = immersiveSelected,
            onSelectionChange = { immersiveSelected = it },
        )
        TextTileButton(label = "Disabled", enabled = false, onSelectionChange = {})
      }
    }
  }
}
