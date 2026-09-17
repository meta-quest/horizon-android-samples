/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.metavrx.layout.cookvrx.state.CookVrxViewModel
import com.example.metavrx.layout.cookvrx.ui.CookVrxTheme
import com.example.metavrx.layout.cookvrx.ui.SpatialAppRoot
import metavrx.layout.compose.SpatialScene

class CookVrxActivity : ComponentActivity() {
  @SuppressLint("EndpointWithoutSwitchOff") // Standalone MIT sample — no FB kill-switch infra
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SpatialScene(theme = { content -> CookVrxTheme { content() } }) {
        val viewModel: CookVrxViewModel = viewModel()
        SpatialAppRoot(viewModel)
      }
    }
  }
}
