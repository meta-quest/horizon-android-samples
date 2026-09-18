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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.metavrx.layout.cookvrx.data.Recipe
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun CookStep(
    recipe: Recipe,
    stepIndex: Int,
    isComplete: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onStartTimer: () -> Unit,
    onReturnToLibrary: () -> Unit,
    modifier: Modifier = Modifier,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier =
          modifier.fillMaxSize().background(colors.ink).padding(dimensions.stepContentPadding),
      verticalArrangement = Arrangement.SpaceBetween,
  ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Column {
        Eyebrow("Now cooking", color = colors.citrus)
        BasicText(
            text = recipe.title,
            style = CookVrxTheme.recipeTitle.copy(color = colors.cream),
        )
      }
      Row(
          horizontalArrangement = Arrangement.spacedBy(dimensions.stepControlSpacing),
          verticalAlignment = Alignment.CenterVertically,
      ) {
        Box(
            modifier =
                Modifier.background(colors.tomato, CircleShape)
                    .padding(dimensions.stepBadgePadding),
            contentAlignment = Alignment.Center,
        ) {
          Eyebrow(
              text = if (isComplete) "Done" else "${stepIndex + 1}/${recipe.steps.size}",
              color = colors.cream,
          )
        }
        IconButton(
            icon = { Icon(imageVector = Icons.Regular.Close, contentDescription = null) },
            onClick = onReturnToLibrary,
            contentDescription = "Exit cooking",
            style =
                ButtonStyle.Bordered.copy(
                    contentColor = colors.cream,
                    borderColor = colors.cream,
                ),
        )
      }
    }

    if (isComplete) {
      CompleteState(recipe = recipe)
    } else {
      val step = recipe.steps[stepIndex]
      Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          BasicText(
              text = (stepIndex + 1).toString().padStart(2, '0'),
              style =
                  CookVrxTheme.display.copy(
                      color = colors.tomato,
                      fontSize = dimensions.stepNumberFontSize,
                      lineHeight = dimensions.stepNumberLineHeight,
                  ),
          )
          Spacer(modifier = Modifier.width(dimensions.stepNumberSpacing))
          Box(
              modifier =
                  Modifier.weight(1f)
                      .height(dimensions.stepRuleHeight)
                      .background(colors.cream.copy(alpha = 0.25f)),
          )
        }
        Spacer(modifier = Modifier.height(dimensions.stepInstructionSpacing))
        BasicText(
            text = step.text,
            style =
                CookVrxTheme.title.copy(
                    color = colors.cream,
                    fontSize = dimensions.stepInstructionFontSize,
                    lineHeight = dimensions.stepInstructionLineHeight,
                ),
        )
        if (step.timerSeconds > 0) {
          Spacer(modifier = Modifier.height(dimensions.stepTimerSpacing))
          LabelButton(
              label = "START ${formatClock(step.timerSeconds)} TIMER",
              onClick = onStartTimer,
              style = ButtonStyle.Secondary,
              labelTextStyle = CookVrxTheme.label,
          )
        }
      }
    }

    if (isComplete) {
      LabelButton(
          label = "BACK TO THE RECIPE SHELF",
          onClick = onReturnToLibrary,
          modifier = Modifier.fillMaxWidth(),
          expanded = true,
          labelTextStyle = CookVrxTheme.label,
      )
    } else {
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(dimensions.stepControlSpacing),
      ) {
        LabelButton(
            label = "← PREVIOUS",
            onClick = onPrevious,
            modifier = Modifier.weight(1f),
            // Cream rather than the theme's disabled roles: this button sits on the dark ink step
            // panel, where the light-scheme disabled colors would vanish.
            style =
                ButtonStyle.Bordered.copy(
                    contentColor = colors.cream,
                    borderColor = colors.cream,
                    disabledContentColor = colors.cream.copy(alpha = 0.35f),
                    disabledBorderColor = colors.cream.copy(alpha = 0.35f),
                ),
            enabled = stepIndex > 0,
            expanded = true,
            labelTextStyle = CookVrxTheme.label,
        )
        LabelButton(
            label = if (stepIndex == recipe.steps.lastIndex) "PLATE IT →" else "NEXT STEP →",
            onClick = onNext,
            modifier = Modifier.weight(1.3f),
            expanded = true,
            labelTextStyle = CookVrxTheme.label,
        )
      }
    }
  }
}

@Composable
private fun CompleteState(recipe: Recipe) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier =
          Modifier.fillMaxWidth().padding(horizontal = dimensions.completionHorizontalPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    BasicText(
        text = "DINNER\nIS READY.",
        style =
            CookVrxTheme.display.copy(
                color = colors.citrus,
                fontSize = dimensions.completionFontSize,
                lineHeight = dimensions.completionLineHeight,
                textAlign = TextAlign.Center,
            ),
    )
    Spacer(modifier = Modifier.height(dimensions.completionMessageSpacing))
    BasicText(
        text = "${recipe.title} · serve it while the edges are still singing.",
        style = CookVrxTheme.note.copy(color = colors.cream, textAlign = TextAlign.Center),
    )
  }
}
