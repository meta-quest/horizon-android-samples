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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.Screen
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.control.Checkbox
import metavrx.uiset.compose.control.RadioButton
import metavrx.uiset.compose.control.Switch
import metavrx.uiset.compose.theme.UiSetTheme

@Composable
private fun Labelled(label: String, control: @Composable () -> Unit) {
  Row(
      modifier = Modifier.semantics(mergeDescendants = true) {},
      verticalAlignment = Alignment.CenterVertically,
  ) {
    control()
    Spacer(Modifier.width(12.dp))
    Text(
        text = label,
        style = UiSetTheme.typography.body,
        color = UiSetTheme.colorScheme.background.content.primary,
    )
  }
}

@Composable
fun ControlsScreen() {
  var checked by remember { mutableStateOf(true) }
  var switched by remember { mutableStateOf(true) }
  var choice by remember { mutableStateOf(0) }

  Screen {
    Demo("Checkbox", "Binary selection. Pass null to onCheckedChange for a read-only control.") {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Labelled("Interactive") {
          Checkbox(
              checked = checked,
              onCheckedChange = { checked = it },
              contentDescription = null,
          )
        }
        Labelled("Checked") {
          Checkbox(checked = true, onCheckedChange = null, contentDescription = null)
        }
        Labelled("Unchecked") {
          Checkbox(
              checked = false,
              onCheckedChange = null,
              contentDescription = null,
          )
        }
        Labelled("Disabled") {
          Checkbox(
              checked = true,
              onCheckedChange = {},
              contentDescription = null,
              enabled = false,
          )
        }
      }
    }

    Demo("Radio button", "Single selection within a group.") {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        listOf("Passthrough", "Immersive", "Auto").forEachIndexed { index, label ->
          Labelled(label) {
            RadioButton(
                selected = choice == index,
                onClick = { choice = index },
                contentDescription = null,
            )
          }
        }
        Labelled("Disabled") {
          RadioButton(
              selected = false,
              onClick = {},
              contentDescription = null,
              enabled = false,
          )
        }
      }
    }

    Demo("Switch", "Immediate-effect toggle. Also used by this app's light/dark control.") {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Labelled("Interactive") {
          Switch(
              checked = switched,
              onCheckedChange = { switched = it },
              contentDescription = null,
          )
        }
        Labelled("On") {
          Switch(checked = true, onCheckedChange = {}, contentDescription = null)
        }
        Labelled("Off") {
          Switch(checked = false, onCheckedChange = {}, contentDescription = null)
        }
        Labelled("Disabled") {
          Switch(
              checked = true,
              onCheckedChange = {},
              contentDescription = null,
              enabled = false,
          )
        }
      }
    }
  }
}
