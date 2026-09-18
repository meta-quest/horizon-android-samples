/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.R
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import metavrx.uiset.compose.Surface
import metavrx.uiset.compose.SurfaceDefaults
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.control.Checkbox
import metavrx.uiset.compose.control.Switch
import metavrx.uiset.compose.input.TextField
import metavrx.uiset.compose.theme.DimensionDefaults
import metavrx.uiset.compose.theme.Palette
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.contentColorFor
import metavrx.uiset.compose.theme.withAccent

@Composable
fun ThemeLabScreen() {
  val spacing = UiSetTheme.dimensions.spacing
  var compact by remember { mutableStateOf(false) }
  var displayName by remember { mutableStateOf("") }

  Screen {
    Demo(
        title = "Density preset",
        description = "Compact changes visual geometry; interactive targets remain at least 48dp.",
    ) {
      Row(
          modifier = Modifier.semantics(mergeDescendants = true) {},
          horizontalArrangement = Arrangement.spacedBy(spacing.small),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
            text = if (compact) "Compact" else "Standard",
            style = UiSetTheme.typography.body,
        )
        Switch(
            checked = compact,
            onCheckedChange = { compact = it },
            contentDescription = null,
        )
      }
      UiSetTheme(
          dimensions = if (compact) DimensionDefaults.Compact else DimensionDefaults.Standard,
      ) {
        DemoRow {
          LabelButton(
              label = "Themed button",
              onClick = {},
              modifier = Modifier.width(160.dp),
              style = ButtonStyle.Primary,
              expanded = true,
          )
          TextField(
              value = displayName,
              label = "Display name",
              onValueChange = { displayName = it },
              placeholder = "How others see you",
              modifier = Modifier.width(280.dp),
          )
        }
      }
    }

    Demo(
        title = "One-color brand override",
        description = "A copied scheme chooses safe content and updates accent semantics together.",
    ) {
      UiSetTheme(colorScheme = UiSetTheme.colorScheme.withAccent(Palette.Pink40)) {
        val surfaceColors = SurfaceDefaults.Colors
        Surface(
            modifier = Modifier.fillMaxWidth(),
            colors = surfaceColors,
            border = BorderStroke(1.dp, UiSetTheme.colorScheme.outline),
        ) {
          Row(
              modifier = Modifier.fillMaxWidth().padding(spacing.large),
              horizontalArrangement = Arrangement.spacedBy(spacing.large),
              verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
                text = "Pink40 accent",
                modifier = Modifier.weight(1f),
                style = UiSetTheme.typography.bodyStrong,
            )
            LabelButton(label = "Brand action", onClick = {}, style = ButtonStyle.Primary)
            Checkbox(
                checked = true,
                onCheckedChange = {},
                contentDescription = stringResource(R.string.pink_checkbox_content_description),
            )
          }
        }
      }
    }

    Demo(
        title = "Local component override",
        description = "Same label on both; only the right one overrides colors and padding.",
    ) {
      val purple = Palette.Purple70
      DemoRow {
        LabelButton(label = "Action", onClick = {}, style = ButtonStyle.Primary)
        LabelButton(
            label = "Action",
            onClick = {},
            style =
                ButtonStyle.Primary.copy(
                    containerColor = purple,
                    contentColor = contentColorFor(purple),
                ),
            dimensions = UiSetTheme.dimensions.buttons.copy(horizontalPadding = 32.dp),
        )
      }
    }
  }
}
