/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import com.example.metavrx.layout.cookvrx.data.Recipe

@Composable
fun IngredientsPanel(
    recipe: Recipe,
    checkedIngredients: SnapshotStateMap<Int, Boolean>,
    onIngredientChecked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier =
          modifier
              .fillMaxSize()
              .background(colors.paper)
              .padding(dimensions.ingredientsContentPadding)
              .verticalScroll(rememberScrollState()),
  ) {
    Eyebrow("Station 01 · Ingredients", color = colors.tomato)
    Spacer(modifier = Modifier.height(dimensions.ingredientsTitleSpacing))
    BasicText(text = "Ingredients", style = CookVrxTheme.title.copy(color = colors.ink))
    Spacer(modifier = Modifier.height(dimensions.ingredientsSubtitleSpacing))
    BasicText(
        text = "Tap as each ingredient hits the pan.",
        style = CookVrxTheme.note.copy(color = colors.mutedInk),
    )
    Spacer(modifier = Modifier.height(dimensions.ingredientsListSpacing))
    recipe.ingredients.forEachIndexed { index, ingredient ->
      val checked = checkedIngredients[index] == true
      Row(
          modifier =
              Modifier.fillMaxWidth()
                  .clickable { onIngredientChecked(index) }
                  .padding(vertical = dimensions.ingredientRowVerticalPadding),
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(dimensions.ingredientRowSpacing),
      ) {
        Box(
            modifier =
                Modifier.size(dimensions.ingredientCheckboxSize)
                    .background(if (checked) colors.leaf else Color.Transparent, CircleShape)
                    .border(dimensions.ingredientCheckboxBorderWidth, colors.ink, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
          if (checked) {
            BasicText(text = "✓", style = CookVrxTheme.bodyStrong.copy(color = colors.cream))
          }
        }
        Column(modifier = Modifier.weight(1f).alpha(if (checked) 0.55f else 1f)) {
          BasicText(
              text = ingredient.text,
              style =
                  CookVrxTheme.body.copy(
                      color = colors.ink,
                      textDecoration = if (checked) TextDecoration.LineThrough else null,
                  ),
          )
          ingredient.tip?.let { tip ->
            Spacer(modifier = Modifier.height(dimensions.ingredientTipSpacing))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                  modifier =
                      Modifier.width(dimensions.ingredientTipRuleWidth)
                          .height(dimensions.ingredientTipRuleHeight)
                          .background(colors.citrus),
              )
              Spacer(modifier = Modifier.width(dimensions.ingredientTipRuleSpacing))
              BasicText(text = tip, style = CookVrxTheme.note.copy(color = colors.mutedInk))
            }
          }
        }
      }
      Box(
          modifier =
              Modifier.fillMaxWidth()
                  .height(dimensions.ingredientDividerHeight)
                  .background(colors.ink.copy(alpha = 0.12f)),
      )
    }
  }
}
