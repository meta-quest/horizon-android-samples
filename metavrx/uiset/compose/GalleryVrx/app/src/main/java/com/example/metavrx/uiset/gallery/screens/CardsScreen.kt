/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.card.OutlinedCard
import metavrx.uiset.compose.card.PrimaryCard
import metavrx.uiset.compose.card.SecondaryCard
import metavrx.uiset.compose.theme.LocalContentColors
import metavrx.uiset.compose.theme.UiSetTheme

@Composable
private fun CardBody(title: String, body: String) {
  Text(
      text = title,
      style = UiSetTheme.typography.title,
      color = LocalContentColors.current.primary,
  )
  Spacer(Modifier.height(6.dp))
  Text(
      text = body,
      style = UiSetTheme.typography.bodySmall,
      color = LocalContentColors.current.secondary,
  )
}

@Composable
fun CardsScreen() {
  Screen {
    Demo("Variants", "Three surface treatments. Each takes a ColumnScope content slot.") {
      DemoRow {
        PrimaryCard(modifier = Modifier.width(260.dp)) {
          CardBody("Primary", "The most prominent surface. Use for the main content of a panel.")
        }
        SecondaryCard(modifier = Modifier.width(260.dp)) {
          CardBody("Secondary", "A quieter surface for grouping. Every card on this screen is one.")
        }
        OutlinedCard(modifier = Modifier.width(260.dp)) {
          CardBody("Outlined", "A border instead of a fill, for low-emphasis grouping.")
        }
      }
    }

    Demo("Clickable", "Passing onClick makes the whole card an interactive surface.") {
      DemoRow {
        PrimaryCard(modifier = Modifier.width(260.dp), onClick = {}) {
          CardBody("Tap me", "This card is clickable and shows UISet indication on press.")
        }
        OutlinedCard(modifier = Modifier.width(260.dp), onClick = {}) {
          CardBody("Also tappable", "Same, with the outlined treatment.")
        }
      }
    }

    Demo("Custom padding", "Copy the themed dimensions to change one value locally.") {
      DemoRow {
        SecondaryCard(
            modifier = Modifier.width(260.dp),
            dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = 8.dp),
        ) {
          CardBody("Tight", "8dp padding.")
        }
        SecondaryCard(
            modifier = Modifier.width(260.dp),
            dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = 32.dp),
        ) {
          CardBody("Roomy", "32dp padding.")
        }
      }
    }
  }
}
