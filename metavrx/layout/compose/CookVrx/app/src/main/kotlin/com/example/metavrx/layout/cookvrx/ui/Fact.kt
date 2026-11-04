/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Fact(label: String, value: String, modifier: Modifier = Modifier) {
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier =
          modifier.padding(
              horizontal = dimensions.detailFactHorizontalPadding,
              vertical = dimensions.detailFactVerticalPadding,
          ),
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Eyebrow(label)
    BasicText(
        text = value,
        style = CookVrxTheme.bodyStrong.copy(color = CookVrxTheme.colors.ink),
    )
  }
}
