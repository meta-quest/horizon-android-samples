/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import metavrx.uiset.compose.theme.icons.Icons

/**
 * One entry in the side rail, and one screen in the gallery.
 *
 * [keywords] backs the search bar: matching is over the title plus these, so searching "toggle"
 * finds Controls even though no title contains that word.
 *
 * Note there is no `icon` property here — see [Section.icon].
 */
enum class Section(
    val title: String,
    val keywords: List<String> = emptyList(),
) {
  BUTTONS(
      "Buttons",
      listOf(
          "primary",
          "secondary",
          "bordered",
          "borderless",
          "destructive",
          "icon",
          "circle",
          "shelf",
          "tile",
          "label",
      ),
  ),
  CARDS("Cards", listOf("surface", "container", "outlined", "primary", "secondary")),
  CONTROLS("Controls", listOf("toggle", "selection", "checkbox", "radio", "switch")),
  THEME_LAB("Theme Lab", listOf("theme", "density", "compact", "brand", "customization")),
  DIALOGS("Dialogs", listOf("modal", "alert", "confirm", "choice", "steps", "info", "basic")),
  DROPDOWNS("Dropdowns", listOf("menu", "select", "picker", "filled", "borderless")),
  INPUT("Input", listOf("text", "field", "form", "validation", "keyboard", "search")),
  SLIDERS("Sliders", listOf("range", "value", "scrub", "small", "medium", "large")),
  TOOLTIPS("Tooltips", listOf("hint", "popover", "hover", "anchor")),
  TYPOGRAPHY("Typography", listOf("text", "font", "inter", "headline", "body")),
  ICONS("Icons", listOf("glyph", "symbol", "pictogram")),
  ;

  fun matches(query: String): Boolean {
    if (query.isBlank()) return true
    val q = query.trim().lowercase()
    return title.lowercase().contains(q) || keywords.any { it.lowercase().contains(q) }
  }
}

/**
 * The rail icon for a section.
 *
 * This is a `@Composable` extension rather than an enum constructor property because every
 * `Icons.Regular.*` has a `@Composable` getter — it resolves a vector resource — so the value can
 * only be read during composition. Enum constructor arguments are evaluated at class
 * initialisation, which is not a composition, and the compiler rejects it with "@Composable
 * invocations can only happen from the context of a @Composable function".
 */
val Section.icon: ImageVector
  @Composable
  get() =
      when (this) {
        Section.BUTTONS -> Icons.Regular.Add
        Section.CARDS -> Icons.Regular.Apps
        Section.CONTROLS -> Icons.Regular.CheckCircle
        Section.THEME_LAB -> Icons.Regular.Color
        Section.DIALOGS -> Icons.Regular.Info
        Section.DROPDOWNS -> Icons.Regular.ChevronDown
        Section.INPUT -> Icons.Regular.Search
        Section.SLIDERS -> Icons.Regular.Settings
        Section.TOOLTIPS -> Icons.Regular.Question
        Section.TYPOGRAPHY -> Icons.Regular.ChatText
        Section.ICONS -> Icons.Regular.Compose
      }
