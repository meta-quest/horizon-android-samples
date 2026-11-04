/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.roundToIntRect
import kotlin.math.roundToInt

@Stable
class TrackedBoundsState {
  var bottomEndOffset by mutableStateOf(IntOffset.Zero)
    private set

  var bounds by mutableStateOf(IntRect.Zero)
    private set

  internal fun update(coordinates: LayoutCoordinates) {
    val bounds = coordinates.boundsInRoot()
    if (bounds.isEmpty) return
    val rootWidth = coordinates.findRootCoordinates().size.width
    bottomEndOffset =
        IntOffset(x = (rootWidth - bounds.right).roundToInt(), y = bounds.bottom.roundToInt())
    this.bounds = bounds.roundToIntRect()
  }
}

fun Modifier.trackBounds(state: TrackedBoundsState): Modifier = onGloballyPositioned(state::update)
