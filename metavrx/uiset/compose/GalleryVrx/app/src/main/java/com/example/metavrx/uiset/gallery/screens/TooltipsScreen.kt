/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.metavrx.uiset.gallery.R
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import com.example.metavrx.uiset.gallery.ui.icon
import metavrx.uiset.compose.UiSetAnchor
import metavrx.uiset.compose.anchor
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.theme.icons.Icons
import metavrx.uiset.compose.tooltip.Tooltip
import metavrx.uiset.compose.tooltip.TooltipPlacement

/** Tooltips follow explicitly tracked anchors; visibility is owned by the caller. */
@Composable
fun TooltipsScreen() {
  Screen {
    Demo("On an icon button", "The usual case: explain an icon that has no label.") {
      DemoRow {
        TooltipAnchor(
            title = "Bookmark",
            anchor = { interactionSource ->
              IconButton(
                  icon = icon { Icons.Regular.Bookmark },
                  onClick = {},
                  contentDescription = stringResource(R.string.icon_bookmark_content_description),
                  style = ButtonStyle.Secondary,
                  interactionSource = interactionSource,
              )
            },
        )
        TooltipAnchor(
            title = "Settings",
            subtitle = "Adjust panel and display options",
            anchor = { interactionSource ->
              IconButton(
                  icon = icon { Icons.Regular.Settings },
                  onClick = {},
                  contentDescription = stringResource(R.string.icon_settings_content_description),
                  style = ButtonStyle.Secondary,
                  interactionSource = interactionSource,
              )
            },
        )
      }
    }

    Demo("With an icon", "A leading icon inside the tooltip itself.") {
      DemoRow {
        TooltipAnchor(
            title = "Passthrough is on",
            subtitle = "Your room is visible behind this panel",
            icon = icon { Icons.Regular.Info },
            anchor = { interactionSource ->
              LabelButton(
                  label = "Hover me",
                  onClick = {},
                  style = ButtonStyle.Primary,
                  interactionSource = interactionSource,
              )
            },
        )
      }
    }

    Demo("Placement", "The bubble can prefer the space below its anchor.") {
      DemoRow {
        TooltipAnchor(
            title = "Below the anchor",
            placement = TooltipPlacement.Below,
            anchor = { interactionSource ->
              LabelButton(
                  label = "Tooltip below",
                  onClick = {},
                  style = ButtonStyle.Primary,
                  interactionSource = interactionSource,
              )
            },
        )
      }
    }
  }
}

@Composable
private fun TooltipAnchor(
    title: String,
    anchor: @Composable (MutableInteractionSource) -> Unit,
    subtitle: String? = null,
    icon: (@Composable () -> Unit)? = null,
    placement: TooltipPlacement = TooltipPlacement.Above,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isHovered by interactionSource.collectIsHoveredAsState()
  val anchorTracker = UiSetAnchor.rememberAnchor()
  Box {
    Box(Modifier.anchor(anchorTracker)) { anchor(interactionSource) }
    if (isHovered) {
      Tooltip(
          title = title,
          anchor = anchorTracker,
          subtitle = subtitle,
          icon = icon,
          placement = placement,
      )
    }
  }
}
