/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.metavrx.layout.cookvrx.data.Recipe
import com.example.metavrx.layout.cookvrx.state.CookVrxMode
import com.example.metavrx.layout.cookvrx.state.CookVrxViewModel
import com.example.metavrx.layout.cookvrx.state.MobileCookTool
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.LabelButton

@Composable
fun AppRoot(viewModel: CookVrxViewModel) {
  when (viewModel.mode) {
    CookVrxMode.Browse -> MobileBrowseLayout(viewModel)
    CookVrxMode.Cook -> MobileCookLayout(viewModel)
  }
}

@Composable
private fun MobileBrowseLayout(viewModel: CookVrxViewModel) {
  val selectedRecipe = viewModel.selectedRecipe
  if (selectedRecipe == null) {
    RecipeLibrary(viewModel)
  } else {
    RecipeDetails(viewModel = viewModel, recipe = selectedRecipe)
  }
}

// The Box is the swap slot for the tool picker: it holds the fixed tool-strip height steady while
// its single child changes, so removing it would make the layout jump between tools.
@Suppress("JetpackComposeBoxWithSingleComposable")
@Composable
private fun MobileCookLayout(viewModel: CookVrxViewModel) {
  val recipe = viewModel.selectedRecipe ?: return
  val dimensions = CookVrxTheme.dimensions
  Column(modifier = Modifier.fillMaxSize().background(CookVrxTheme.colors.ink)) {
    CurrentStep(viewModel = viewModel, recipe = recipe, modifier = Modifier.weight(1f))
    if (!viewModel.isComplete) {
      MobileToolPicker(
          selected = viewModel.mobileCookTool,
          onSelected = viewModel::showMobileTool,
      )
      Box(modifier = Modifier.fillMaxWidth().height(dimensions.mobileCookToolHeight)) {
        when (viewModel.mobileCookTool) {
          MobileCookTool.Ingredients -> Ingredients(viewModel = viewModel, recipe = recipe)
          MobileCookTool.Timers -> Timers(viewModel)
        }
      }
    }
  }
}

@Composable
internal fun RecipeLibrary(viewModel: CookVrxViewModel, modifier: Modifier = Modifier) {
  val dimensions = CookVrxTheme.dimensions
  val windowWidth = LocalConfiguration.current.screenWidthDp.dp
  LibraryScreen(
      recipes = viewModel.filteredRecipes,
      categories = viewModel.categories,
      selectedCategory = viewModel.selectedCategory,
      selectedRecipeId = viewModel.selectedRecipeId,
      columnCount = dimensions.libraryColumnCountFor(windowWidth),
      onCategorySelected = viewModel::selectCategory,
      onRecipeSelected = viewModel::selectRecipe,
      modifier = modifier,
  )
}

@Composable
internal fun RecipeDetails(
    viewModel: CookVrxViewModel,
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
  RecipeDetail(
      recipe = recipe,
      onClose = viewModel::closeRecipe,
      onStartCooking = viewModel::startCooking,
      modifier = modifier,
  )
}

@Composable
internal fun CurrentStep(
    viewModel: CookVrxViewModel,
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
  CookStep(
      recipe = recipe,
      stepIndex = viewModel.stepIndex,
      isComplete = viewModel.isComplete,
      onPrevious = viewModel::previousStep,
      onNext = viewModel::nextStep,
      onStartTimer = viewModel::startStepTimer,
      onReturnToLibrary = viewModel::returnToLibrary,
      modifier = modifier,
  )
}

@Composable
internal fun Ingredients(
    viewModel: CookVrxViewModel,
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
  IngredientsPanel(
      recipe = recipe,
      checkedIngredients = viewModel.checkedIngredients,
      onIngredientChecked = viewModel::toggleIngredient,
      modifier = modifier,
  )
}

@Composable
internal fun Timers(viewModel: CookVrxViewModel, modifier: Modifier = Modifier) {
  TimersPanel(
      timers = viewModel.timers,
      onAddQuickTimer = viewModel::addQuickTimer,
      onExtendTimer = viewModel::extendTimer,
      onRemoveTimer = viewModel::removeTimer,
      modifier = modifier,
  )
}

@Composable
private fun MobileToolPicker(
    selected: MobileCookTool,
    onSelected: (MobileCookTool) -> Unit,
) {
  val dimensions = CookVrxTheme.dimensions
  Row(
      modifier =
          Modifier.fillMaxWidth()
              .background(CookVrxTheme.colors.cream)
              .padding(
                  horizontal = dimensions.mobileToolPickerHorizontalPadding,
                  vertical = dimensions.mobileToolPickerVerticalPadding,
              ),
      horizontalArrangement = Arrangement.spacedBy(dimensions.mobileToolPickerSpacing),
  ) {
    MobileToolButton(
        label = "Ingredients",
        selected = selected == MobileCookTool.Ingredients,
        onClick = { onSelected(MobileCookTool.Ingredients) },
        modifier = Modifier.weight(1f),
    )
    MobileToolButton(
        label = "Timers",
        selected = selected == MobileCookTool.Timers,
        onClick = { onSelected(MobileCookTool.Timers) },
        modifier = Modifier.weight(1f),
    )
  }
}

@Composable
private fun MobileToolButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
  if (selected) {
    LabelButton(
        label = label.uppercase(),
        onClick = onClick,
        modifier = modifier,
        expanded = true,
        labelTextStyle = CookVrxTheme.label,
    )
  } else {
    LabelButton(
        label = label.uppercase(),
        onClick = onClick,
        modifier = modifier,
        style = ButtonStyle.Bordered.copy(borderColor = CookVrxTheme.colors.ink),
        expanded = true,
        labelTextStyle = CookVrxTheme.label,
    )
  }
}
