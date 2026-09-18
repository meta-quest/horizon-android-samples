/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery

import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.navigation.Section
import com.example.metavrx.uiset.gallery.navigation.Sidebar
import com.example.metavrx.uiset.gallery.screens.ButtonsScreen
import com.example.metavrx.uiset.gallery.screens.CardsScreen
import com.example.metavrx.uiset.gallery.screens.ControlsScreen
import com.example.metavrx.uiset.gallery.screens.DialogsScreen
import com.example.metavrx.uiset.gallery.screens.DropdownsScreen
import com.example.metavrx.uiset.gallery.screens.IconsScreen
import com.example.metavrx.uiset.gallery.screens.InputScreen
import com.example.metavrx.uiset.gallery.screens.SlidersScreen
import com.example.metavrx.uiset.gallery.screens.ThemeLabScreen
import com.example.metavrx.uiset.gallery.screens.TooltipsScreen
import com.example.metavrx.uiset.gallery.screens.TypographyScreen
import com.example.metavrx.uiset.gallery.ui.ScreenHeader
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.control.Switch
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.darkColorScheme
import metavrx.uiset.compose.theme.lightColorScheme

/**
 * The gallery shell.
 *
 * Deliberately built out of the library it demonstrates: the rail is `SideNavItem`, the filter is
 * `SearchBar`, and the theme toggle is [Switch]. The chrome is a demo too, so the components are
 * exercised in a working app rather than only rendered in isolation.
 */
@Composable
fun SampleApp() {
  var darkTheme by remember { mutableStateOf(true) }
  var selected by remember { mutableStateOf(Section.BUTTONS) }
  var query by remember { mutableStateOf("") }

  UiSetTheme(colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()) {
    Row(
        modifier =
            Modifier.fillMaxSize().background(UiSetTheme.colorScheme.background.container.brush),
    ) {
      Sidebar(
          sections = Section.entries.filter { it.matches(query) },
          selected = selected,
          query = query,
          onQueryChange = { query = it },
          onSelect = { selected = it },
          modifier = Modifier.width(300.dp).fillMaxHeight(),
      )

      Column(modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp, vertical = 32.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
          ScreenHeader(title = selected.title)
          ThemeToggle(darkTheme = darkTheme, onToggle = { darkTheme = it })
        }

        Spacer(Modifier.height(28.dp))

        // Keyed on `selected` so each section starts at the top, not the last one's offset.
        val scrollState = rememberSaveable(selected, saver = ScrollState.Saver) { ScrollState(0) }

        Box(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
          when (selected) {
            Section.BUTTONS -> ButtonsScreen()
            Section.CARDS -> CardsScreen()
            Section.CONTROLS -> ControlsScreen()
            Section.THEME_LAB -> ThemeLabScreen()
            Section.DIALOGS -> DialogsScreen()
            Section.DROPDOWNS -> DropdownsScreen()
            Section.INPUT -> InputScreen()
            Section.SLIDERS -> SlidersScreen()
            Section.TOOLTIPS -> TooltipsScreen()
            Section.TYPOGRAPHY -> TypographyScreen()
            Section.ICONS -> IconsScreen()
          }
        }
      }
    }
  }
}

/**
 * Flips every screen between [lightColorScheme] and [darkColorScheme], so both schemes get
 * exercised without needing a section of their own.
 */
@Composable
private fun ThemeToggle(darkTheme: Boolean, onToggle: (Boolean) -> Unit) {
  Row(
      modifier = Modifier.semantics(mergeDescendants = true) {},
      verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
        text = if (darkTheme) "Dark" else "Light",
        style = UiSetTheme.typography.bodySmall,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
    Spacer(Modifier.width(12.dp))
    Switch(
        checked = darkTheme,
        onCheckedChange = onToggle,
        contentDescription = stringResource(R.string.theme_toggle_content_description),
    )
  }
}
