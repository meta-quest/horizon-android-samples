/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.coerceAtLeast
import com.example.metavrx.layout.cookvrx.data.Recipe
import com.example.metavrx.layout.cookvrx.state.CookVrxMode
import com.example.metavrx.layout.cookvrx.state.CookVrxViewModel
import com.example.metavrx.layout.cookvrx.state.TrackedBoundsState
import metavrx.layout.window.compose.LocalPromoted
import metavrx.layout.window.compose.LocalSpatialWindowSupport
import metavrx.layout.window.compose.SpatialWindow
import metavrx.layout.window.compose.layout.OffsetStep
import metavrx.layout.window.compose.layout.WindowAnchor
import metavrx.layout.window.compose.layout.WindowEdge
import metavrx.layout.window.compose.layout.WindowModifier
import metavrx.layout.window.compose.layout.anchor
import metavrx.layout.window.compose.layout.matchParentHeight
import metavrx.layout.window.compose.layout.offset
import metavrx.layout.window.compose.layout.size
import metavrx.layout.window.compose.layout.width
import metavrx.layout.window.compose.state.WindowFallback

@Composable
fun SpatialAppRoot(viewModel: CookVrxViewModel) {
  if (!LocalSpatialWindowSupport.current.isAvailable) {
    AppRoot(viewModel)
    return
  }

  when (viewModel.mode) {
    CookVrxMode.Browse -> SpatialBrowseLayout(viewModel)
    CookVrxMode.Cook -> SpatialCookLayout(viewModel)
  }
}

@Composable
private fun SpatialBrowseLayout(viewModel: CookVrxViewModel) {
  val collectionInfoButtonBounds = remember { TrackedBoundsState() }
  Row(modifier = Modifier.fillMaxSize()) {
    RecipeLibrary(
        viewModel = viewModel,
        onCollectionInfoClick = viewModel::toggleCollectionInfo,
        modifier = Modifier.weight(1f),
        collectionInfoButtonBounds = collectionInfoButtonBounds,
    )
    viewModel.selectedRecipe?.let { recipe -> DetailWindow(viewModel, recipe) }
    if (viewModel.isCollectionInfoOpen) {
      CollectionInfoWindow(viewModel = viewModel, buttonBounds = collectionInfoButtonBounds)
    }
  }
}

@Composable
private fun CollectionInfoWindow(viewModel: CookVrxViewModel, buttonBounds: TrackedBoundsState) {
  val dimensions = CookVrxTheme.dimensions
  val anchorOffset =
      with(LocalDensity.current) {
        DpOffset(
            x = buttonBounds.bottomEndOffset.x.toDp(),
            y =
                buttonBounds.bottomEndOffset.y
                    .toDp()
                    .coerceAtLeast(dimensions.collectionInfoWindowMinTopOffset),
        )
      }
  SpatialWindow(
      key = "collection-info",
      modifier =
          WindowModifier.size(
                  width = dimensions.collectionInfoWindowWidth,
                  height = dimensions.collectionInfoWindowHeight,
              )
              .anchor(WindowAnchor(parent = WindowEdge.Top.End, child = WindowEdge.Top.End))
              .offset(
                  x = anchorOffset.x,
                  y = -anchorOffset.y,
                  z = OffsetStep.Near,
              ),
      fallbackStrategy = WindowFallback.Inline,
  ) {
    val modifier =
        if (LocalPromoted.current) {
          Modifier.fillMaxSize()
        } else {
          Modifier.width(dimensions.collectionInfoWindowWidth).fillMaxHeight()
        }
    CollectionInfo(viewModel = viewModel, modifier = modifier)
  }
}

@Composable
private fun DetailWindow(viewModel: CookVrxViewModel, recipe: Recipe) {
  val dimensions = CookVrxTheme.dimensions
  SpatialWindow(
      key = "detail",
      modifier =
          WindowModifier.size(
                  width = dimensions.detailWindowWidth,
                  height = dimensions.detailWindowHeight,
              )
              .anchor(WindowAnchor.End)
              .offset(x = -OffsetStep.Near, z = OffsetStep.MidNear),
      fallbackStrategy = WindowFallback.Inline,
  ) {
    RecipeDetails(viewModel = viewModel, recipe = recipe)
  }
}

@Composable
private fun SpatialCookLayout(viewModel: CookVrxViewModel) {
  val recipe = viewModel.selectedRecipe ?: return
  val dimensions = CookVrxTheme.dimensions
  Row(modifier = Modifier.fillMaxSize()) {
    IngredientsWindow(
        viewModel = viewModel,
        recipe = recipe,
        inlineModifier = Modifier.width(dimensions.inlineCookToolWidth).fillMaxHeight(),
    )
    CurrentStep(viewModel = viewModel, recipe = recipe, modifier = Modifier.weight(1f))
    TimersWindow(
        viewModel = viewModel,
        inlineModifier = Modifier.width(dimensions.inlineCookToolWidth).fillMaxHeight(),
    )
  }
}

@Composable
private fun IngredientsWindow(
    viewModel: CookVrxViewModel,
    recipe: Recipe,
    inlineModifier: Modifier,
) {
  val dimensions = CookVrxTheme.dimensions
  SpatialWindow(
      key = "ingredients",
      modifier =
          WindowModifier.width(dimensions.cookToolWindowWidth)
              .matchParentHeight()
              .anchor(WindowAnchor.Start)
              .offset(x = OffsetStep.Near, z = OffsetStep.Near),
      fallbackStrategy = WindowFallback.Inline,
  ) {
    val modifier = if (LocalPromoted.current) Modifier.fillMaxSize() else inlineModifier
    Ingredients(viewModel = viewModel, recipe = recipe, modifier = modifier)
  }
}

@Composable
private fun TimersWindow(viewModel: CookVrxViewModel, inlineModifier: Modifier) {
  val dimensions = CookVrxTheme.dimensions
  SpatialWindow(
      key = "timers",
      modifier =
          WindowModifier.width(dimensions.cookToolWindowWidth)
              .matchParentHeight()
              .anchor(WindowAnchor.End)
              .offset(x = -OffsetStep.Near, z = OffsetStep.Near),
      fallbackStrategy = WindowFallback.Inline,
  ) {
    val modifier = if (LocalPromoted.current) Modifier.fillMaxSize() else inlineModifier
    Timers(viewModel = viewModel, modifier = modifier)
  }
}
