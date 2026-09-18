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
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import com.example.metavrx.uiset.gallery.ui.icon
import kotlinx.collections.immutable.persistentListOf
import metavrx.uiset.compose.dropdown.Dropdown
import metavrx.uiset.compose.dropdown.DropdownItem
import metavrx.uiset.compose.dropdown.IconDropdown
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun DropdownsScreen() {
  val items = remember {
    persistentListOf(
        DropdownItem(title = "Low", subtitle = "72 Hz"),
        DropdownItem(title = "Medium", subtitle = "90 Hz"),
        DropdownItem(title = "High", subtitle = "120 Hz"),
        DropdownItem(title = "Unavailable", enabled = false),
    )
  }
  var filled by remember { mutableStateOf<DropdownItem?>(null) }
  var borderless by remember { mutableStateOf<DropdownItem?>(null) }
  var withDividers by remember { mutableStateOf(items[1]) }
  var iconChoice by remember { mutableStateOf(items[2]) }
  var borderlessIconChoice by remember { mutableStateOf<DropdownItem?>(null) }

  Screen {
    Demo("Filled", "The default pill treatment, with an optional placeholder and subtitle.") {
      DemoRow {
        Dropdown(
            items = items,
            onItemSelected = { filled = it },
            selectedItem = filled,
            placeholder = "Refresh rate",
        )
      }
    }

    Demo("Borderless", "Set filled = false for a lower-emphasis trigger.") {
      DemoRow {
        Dropdown(
            items = items,
            onItemSelected = { borderless = it },
            selectedItem = borderless,
            filled = false,
            placeholder = "Refresh rate",
        )
      }
    }

    Demo("With dividers and leading icon") {
      DemoRow {
        Dropdown(
            items = items,
            onItemSelected = { withDividers = it },
            selectedItem = withDividers,
            leadingIcon = icon { Icons.Regular.Settings },
            showDividers = true,
        )
      }
    }

    Demo("Icon only", "Collapses to a single icon trigger for toolbars.") {
      DemoRow {
        IconDropdown(
            icon = icon { Icons.Regular.Settings },
            items = items,
            onItemSelected = { iconChoice = it },
            selectedItem = iconChoice,
        )
        IconDropdown(
            icon = icon { Icons.Regular.Settings },
            items = items,
            onItemSelected = { borderlessIconChoice = it },
            selectedItem = borderlessIconChoice,
            filled = false,
        )
        IconDropdown(
            icon = icon { Icons.Regular.Settings },
            items = items,
            onItemSelected = {},
            enabled = false,
        )
      }
    }
  }
}
