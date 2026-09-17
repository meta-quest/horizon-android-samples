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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.metavrx.layout.cookvrx.data.Recipe
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.card.OutlinedCard
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun RecipeDetail(
    recipe: Recipe,
    onClose: () -> Unit,
    onStartCooking: () -> Unit,
    modifier: Modifier = Modifier,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier =
          modifier
              .fillMaxSize()
              .background(colors.cream)
              .verticalScroll(rememberScrollState())
              .padding(dimensions.detailContentPadding),
  ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Eyebrow("Recipe card · ${recipe.category}", color = colors.tomato)
      IconButton(
          icon = { Icon(imageVector = Icons.Regular.Close, contentDescription = null) },
          onClick = onClose,
          contentDescription = "Close recipe",
          style = ButtonStyle.Bordered.copy(borderColor = colors.ink),
      )
    }
    Spacer(modifier = Modifier.height(dimensions.detailArtworkTopSpacing))
    DishArtwork(
        recipeId = recipe.id,
        modifier = Modifier.fillMaxWidth().height(dimensions.detailArtworkHeight),
    )
    Spacer(modifier = Modifier.height(dimensions.detailTitleTopSpacing))
    BasicText(text = recipe.title, style = CookVrxTheme.title.copy(color = colors.ink))
    Spacer(modifier = Modifier.height(dimensions.detailFactsTopSpacing))
    RecipeFacts(recipe)
    Spacer(modifier = Modifier.height(dimensions.detailActionTopSpacing))
    LabelButton(
        label = "START COOKING →",
        onClick = onStartCooking,
        modifier = Modifier.fillMaxWidth(),
        expanded = true,
        labelTextStyle = CookVrxTheme.label,
    )
    Spacer(modifier = Modifier.height(dimensions.detailSectionTopSpacing))
    Row(verticalAlignment = Alignment.CenterVertically) {
      Eyebrow("Ingredients", color = colors.ink)
      Spacer(modifier = Modifier.width(dimensions.detailSectionRuleSpacing))
      Box(
          modifier =
              Modifier.weight(1f)
                  .height(dimensions.detailSectionRuleHeight)
                  .background(colors.ink.copy(alpha = 0.22f)),
      )
    }
    Spacer(modifier = Modifier.height(dimensions.detailIngredientsTopSpacing))
    recipe.ingredients.forEachIndexed { index, ingredient ->
      Row(
          modifier =
              Modifier.fillMaxWidth()
                  .padding(vertical = dimensions.detailIngredientVerticalPadding),
          verticalAlignment = Alignment.Top,
      ) {
        Box(
            modifier =
                Modifier.width(dimensions.detailIngredientIndexSize)
                    .height(dimensions.detailIngredientIndexSize)
                    .background(
                        if (index % 2 == 0) colors.citrus else colors.paper,
                        RoundedCornerShape(dimensions.detailIngredientIndexCornerRadius),
                    ),
            contentAlignment = Alignment.Center,
        ) {
          Eyebrow(text = (index + 1).toString(), color = colors.ink)
        }
        Spacer(modifier = Modifier.width(dimensions.detailIngredientSpacing))
        Column(modifier = Modifier.weight(1f)) {
          BasicText(text = ingredient.text, style = CookVrxTheme.body.copy(color = colors.ink))
          ingredient.tip?.let { tip ->
            BasicText(text = tip, style = CookVrxTheme.note.copy(color = colors.mutedInk))
          }
        }
      }
    }
    Spacer(modifier = Modifier.height(dimensions.detailFooterTopSpacing))
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        dimensions =
            UiSetTheme.dimensions.cards.copy(contentPadding = dimensions.detailFooterPadding),
    ) {
      BasicText(
          text = "Set everything out before the pan gets hot. Future you will be grateful.",
          style = CookVrxTheme.note.copy(color = colors.ink),
      )
    }
  }
}

@Composable
private fun RecipeFacts(recipe: Recipe) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  OutlinedCard(
      modifier = Modifier.fillMaxWidth(),
      dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = 0.dp),
  ) {
    Row(modifier = Modifier.fillMaxWidth()) {
      Fact(label = "Time", value = "${recipe.totalMinutes}m", modifier = Modifier.weight(1f))
      Fact(label = "Feeds", value = recipe.servings.toString(), modifier = Modifier.weight(1f))
      Fact(label = "Level", value = recipe.difficulty, modifier = Modifier.weight(1f))
    }
  }
}

@Composable
private fun Fact(label: String, value: String, modifier: Modifier = Modifier) {
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
