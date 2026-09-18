/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.Screen
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.theme.UiSetTheme

@Composable
private fun Specimen(name: String, style: TextStyle) {
  Row(verticalAlignment = Alignment.Bottom) {
    Text(
        text = name,
        style = UiSetTheme.typography.bodySmall,
        color = UiSetTheme.colorScheme.background.content.secondary,
        modifier = Modifier.width(160.dp),
    )
    Spacer(Modifier.width(16.dp))
    Text(
        text = "The quick brown fox",
        style = style,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
  }
}

/**
 * The semantic platform type scale. Optimistic is the default on Horizon; the bundled Inter family
 * is available through the portable typography preset.
 */
@Composable
fun TypographyScreen() {
  val t = UiSetTheme.typography
  Screen {
    Demo("Hierarchy", "Semantic roles describe intent rather than numbered size levels.") {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Specimen("display", t.display)
        Specimen("headline", t.headline)
        Specimen("title", t.title)
        Specimen("label", t.label)
      }
    }

    Demo("Reading", "Body roles include derived strong weights plus a compact caption.") {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Specimen("body", t.body)
        Specimen("bodyStrong", t.bodyStrong)
        Specimen("bodySmall", t.bodySmall)
        Specimen("bodySmallStrong", t.bodySmallStrong)
        Specimen("caption", t.caption)
      }
    }
  }
}
