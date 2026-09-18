/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.input.SearchBar
import metavrx.uiset.compose.navigation.SideNavItem
import metavrx.uiset.compose.navigation.SideNavItemDefaults
import metavrx.uiset.compose.theme.UiSetTheme

/**
 * Left rail. Built from `SideNavItem` and `SearchBar` so the app's own navigation is itself a demo
 * of the library.
 *
 * The search bar filters [sections] via [Section.matches], which searches keywords as well as
 * titles — so "toggle" finds Controls. Filtering the rail rather than the content keeps the search
 * genuinely functional instead of decorative.
 */
@Composable
fun Sidebar(
    sections: List<Section>,
    selected: Section,
    query: String,
    onQueryChange: (String) -> Unit,
    onSelect: (Section) -> Unit,
    modifier: Modifier = Modifier,
) {
  val navigationColors = SideNavItemDefaults.Colors
  val spacing = UiSetTheme.dimensions.spacing

  Column(
      modifier =
          modifier
              .background(UiSetTheme.colorScheme.surfaceVariant.container.brush)
              .padding(horizontal = spacing.twoXLarge, vertical = spacing.twoXLarge),
  ) {
    Text(
        text = "GalleryVrx",
        style = UiSetTheme.typography.title,
        color = UiSetTheme.colorScheme.surfaceVariant.content.primary,
    )

    Spacer(Modifier.height(spacing.twoXLarge))

    SearchBar(
        query = query,
        placeholder = "Search components",
        onQueryChange = onQueryChange,
        onSearch = { /* filtering is live; submit is a no-op here */ },
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(Modifier.height(spacing.twoXLarge))

    if (sections.isEmpty()) {
      Text(
          text = "No components match \"$query\"",
          style = UiSetTheme.typography.bodySmall,
          color = UiSetTheme.colorScheme.surfaceVariant.content.secondary,
          modifier = Modifier.padding(horizontal = spacing.small),
      )
    }

    // The rail must scroll: the section list overflows the panel on a device, and a plain Column
    // clips the overflow rather than making it reachable.
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.xSmall),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
      sections.forEach { section ->
        SideNavItem(
            icon = {
              Icon(
                  imageVector = section.icon,
                  contentDescription = null,
                  modifier = Modifier.size(24.dp),
              )
            },
            onClick = { onSelect(section) },
            primaryLabel = section.title,
            selected = section == selected,
            colors = navigationColors,
            modifier = Modifier.fillMaxWidth(),
        )
      }
    }
  }
}
