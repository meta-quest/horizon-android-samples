/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import com.example.metavrx.layout.cookvrx.data.Recipe
import com.example.metavrx.layout.cookvrx.state.TrackedBoundsState
import com.example.metavrx.layout.cookvrx.state.trackBounds
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.card.SecondaryCard
import metavrx.uiset.compose.theme.UiSetTheme

@Composable
@SuppressLint("JetpackComposeMutableParameter")
fun LibraryScreen(
    recipes: List<Recipe>,
    categories: List<String>,
    selectedCategory: String,
    selectedRecipeId: String?,
    columnCount: Int,
    onCategorySelected: (String) -> Unit,
    onRecipeSelected: (Recipe) -> Unit,
    onCollectionInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
    collectionInfoButtonBounds: TrackedBoundsState? = null,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  LazyVerticalGrid(
      columns = GridCells.Fixed(columnCount),
      modifier = modifier.fillMaxSize().background(colors.paper),
      contentPadding = PaddingValues(dimensions.libraryContentPadding),
      horizontalArrangement = Arrangement.spacedBy(dimensions.libraryHorizontalSpacing),
      verticalArrangement = Arrangement.spacedBy(dimensions.libraryVerticalSpacing),
  ) {
    item(span = { GridItemSpan(maxLineSpan) }) {
      LibraryHeader(
          onCollectionInfoClick = onCollectionInfoClick,
          buttonBounds = collectionInfoButtonBounds,
      )
    }
    item(span = { GridItemSpan(maxLineSpan) }) {
      CategoryStrip(
          categories = categories,
          selectedCategory = selectedCategory,
          onCategorySelected = onCategorySelected,
      )
    }
    items(items = recipes, key = Recipe::id) { recipe ->
      RecipeCard(
          recipe = recipe,
          isSelected = recipe.id == selectedRecipeId,
          onClick = { onRecipeSelected(recipe) },
      )
    }
    item(span = { GridItemSpan(maxLineSpan) }) {
      Row(
          modifier =
              Modifier.fillMaxWidth()
                  .padding(
                      top = dimensions.libraryFooterTopPadding,
                      bottom = dimensions.libraryFooterBottomPadding,
                  ),
          horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Eyebrow("Field edition · 01")
        Eyebrow("Cook with your whole attention")
      }
    }
  }
}

@Suppress("JetpackComposeBoxWithSingleComposable") // Box draws the pill background behind the label
@Composable
private fun LibraryHeader(
    onCollectionInfoClick: () -> Unit,
    buttonBounds: TrackedBoundsState?,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(modifier = Modifier.fillMaxWidth()) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(dimensions.libraryHeaderRowSpacing),
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier =
                Modifier.width(dimensions.libraryHeaderRuleWidth)
                    .height(dimensions.libraryHeaderRuleHeight)
                    .background(colors.tomato),
        )
        Spacer(modifier = Modifier.width(dimensions.libraryHeaderRuleSpacing))
        Eyebrow("CookVrx · A field cookbook", color = colors.ink)
      }
      Box(
          modifier =
              Modifier.let { if (buttonBounds != null) it.trackBounds(buttonBounds) else it }
                  .clip(CircleShape)
                  .background(colors.ink)
                  .clickable(
                      onClickLabel = "Show collection info",
                      role = Role.Button,
                      onClick = onCollectionInfoClick,
                  )
                  .padding(
                      horizontal = dimensions.libraryRecipeCountHorizontalPadding,
                      vertical = dimensions.libraryRecipeCountVerticalPadding,
                  ),
      ) {
        Eyebrow("Show all recipe details", color = colors.cream)
      }
    }
    Spacer(modifier = Modifier.height(dimensions.libraryTitleTopSpacing))
    BasicText(
        text = "COOK SOMETHING\nWORTH PAUSING FOR.",
        style = CookVrxTheme.display.copy(color = colors.ink),
    )
    Spacer(modifier = Modifier.height(dimensions.libraryDescriptionTopSpacing))
    BasicText(
        text = "Small rituals, hot pans, and useful timers for whatever tonight becomes.",
        style = CookVrxTheme.note.copy(color = colors.mutedInk),
    )
    Spacer(modifier = Modifier.height(dimensions.libraryDividerTopSpacing))
    Box(
        modifier =
            Modifier.fillMaxWidth()
                .height(dimensions.libraryDividerHeight)
                .background(colors.ink.copy(alpha = 0.24f)),
    )
  }
}

@Composable
@SuppressLint("JetpackComposeMutableParameter")
private fun CategoryStrip(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
) {
  val dimensions = CookVrxTheme.dimensions
  Row(
      modifier = Modifier.fillMaxWidth().padding(vertical = dimensions.categoryVerticalPadding),
      horizontalArrangement = Arrangement.spacedBy(dimensions.categorySpacing),
  ) {
    categories.forEach { category ->
      val selected = category == selectedCategory
      if (selected) {
        LabelButton(
            label = category.uppercase(),
            onClick = { onCategorySelected(category) },
            labelTextStyle = CookVrxTheme.label,
        )
      } else {
        LabelButton(
            label = category.uppercase(),
            onClick = { onCategorySelected(category) },
            style = ButtonStyle.Bordered.copy(borderColor = CookVrxTheme.colors.ink),
            labelTextStyle = CookVrxTheme.label,
        )
      }
    }
  }
}

@Composable
private fun RecipeCard(recipe: Recipe, isSelected: Boolean, onClick: () -> Unit) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  SecondaryCard(
      modifier =
          Modifier.fillMaxWidth().let { cardModifier ->
            if (isSelected) {
              cardModifier.border(
                  dimensions.recipeCardSelectedBorderWidth,
                  colors.tomato,
                  UiSetTheme.shapes.card,
              )
            } else {
              cardModifier
            }
          },
      onClick = onClick,
      dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = dimensions.recipeCardPadding),
  ) {
    DishArtwork(
        recipeId = recipe.id,
        modifier = Modifier.fillMaxWidth().height(dimensions.recipeCardArtworkHeight),
    )
    Spacer(modifier = Modifier.height(dimensions.recipeCardArtworkSpacing))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Eyebrow(recipe.category, color = colors.tomato)
      Eyebrow("${recipe.totalMinutes} min")
    }
    Spacer(modifier = Modifier.height(dimensions.recipeCardMetaSpacing))
    BasicText(
        text = recipe.title,
        style = CookVrxTheme.recipeTitle.copy(color = colors.ink),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
    Spacer(modifier = Modifier.height(dimensions.recipeCardFooterSpacing))
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
          modifier =
              Modifier.width(dimensions.recipeCardFooterRuleWidth)
                  .height(dimensions.recipeCardFooterRuleHeight)
                  .background(colors.citrus),
      )
      Spacer(modifier = Modifier.width(dimensions.recipeCardFooterRuleSpacing))
      BasicText(
          text = "${recipe.difficulty} · serves ${recipe.servings}",
          style = CookVrxTheme.note.copy(color = colors.mutedInk),
      )
    }
  }
}
