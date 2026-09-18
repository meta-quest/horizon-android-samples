/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.R
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.Screen
import com.example.metavrx.uiset.gallery.ui.icon
import metavrx.uiset.compose.slider.Slider
import metavrx.uiset.compose.slider.SliderSize
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun SlidersScreen() {
  var small by remember { mutableFloatStateOf(0.3f) }
  var medium by remember { mutableFloatStateOf(0.5f) }
  var large by remember { mutableFloatStateOf(0.7f) }
  var withIcons by remember { mutableFloatStateOf(0.4f) }
  var withText by remember { mutableFloatStateOf(0.5f) }

  Screen {
    Demo("Three sizes", "Same behaviour, different track and thumb dimensions.") {
      Column(
          verticalArrangement = Arrangement.spacedBy(24.dp),
          modifier = Modifier.fillMaxWidth(),
      ) {
        Slider(
            value = small,
            onValueChange = { small = it },
            contentDescription = stringResource(R.string.small_slider_content_description),
            size = SliderSize.Small,
        )
        Slider(
            value = medium,
            onValueChange = { medium = it },
            contentDescription = stringResource(R.string.medium_slider_content_description),
        )
        Slider(
            value = large,
            onValueChange = { large = it },
            contentDescription = stringResource(R.string.large_slider_content_description),
            size = SliderSize.Large,
        )
      }
    }

    Demo("Hint icons", "A leading and trailing icon pair, e.g. for brightness.") {
      Slider(
          value = withIcons,
          onValueChange = { withIcons = it },
          contentDescription = stringResource(R.string.brightness_slider_content_description),
          startIcon = icon { Icons.Regular.BrightnessLow },
          endIcon = icon { Icons.Regular.BrightnessOn },
      )
    }

    Demo("Hint text", "A leading and trailing label pair.") {
      Slider(
          value = withText,
          onValueChange = { withText = it },
          contentDescription = stringResource(R.string.volume_slider_content_description),
          startLabel = "Quiet",
          endLabel = "Loud",
      )
    }

    Demo("Disabled") {
      Slider(
          value = 0.5f,
          onValueChange = {},
          contentDescription = stringResource(R.string.disabled_slider_content_description),
          enabled = false,
      )
    }
  }
}
