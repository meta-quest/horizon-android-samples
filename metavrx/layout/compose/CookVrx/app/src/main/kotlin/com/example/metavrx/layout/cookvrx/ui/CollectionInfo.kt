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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.metavrx.layout.cookvrx.data.CollectionStats
import com.example.metavrx.layout.cookvrx.data.CountGroup
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.card.OutlinedCard
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun CollectionInfoPanel(
    recipeCount: Int,
    stats: CollectionStats?,
    onCloseButtonClicked: () -> Unit,
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
              .padding(dimensions.collectionInfoContentPadding),
  ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Eyebrow("At a glance", color = colors.tomato)
      IconButton(
          icon = { Icon(imageVector = Icons.Regular.Close, contentDescription = null) },
          onClick = onCloseButtonClicked,
          contentDescription = "Close collection info",
          style = ButtonStyle.Bordered.copy(borderColor = colors.ink),
      )
    }
    Spacer(modifier = Modifier.height(dimensions.collectionInfoTitleTopSpacing))
    BasicText(
        text = "$recipeCount recipes",
        style = CookVrxTheme.title.copy(color = colors.ink),
    )
    if (stats == null) return@Column

    Spacer(modifier = Modifier.height(dimensions.collectionInfoFactsTopSpacing))
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = 0.dp),
    ) {
      Row(modifier = Modifier.fillMaxWidth()) {
        Fact(
            label = "Min",
            value = "${stats.quickest.totalMinutes}m",
            modifier = Modifier.weight(1f),
        )
        Fact(label = "Avg", value = "${stats.averageMinutes}m", modifier = Modifier.weight(1f))
        Fact(
            label = "Max",
            value = "${stats.longest.totalMinutes}m",
            modifier = Modifier.weight(1f),
        )
      }
    }
    Spacer(modifier = Modifier.height(dimensions.collectionInfoTimeNoteTopSpacing))
    BasicText(
        text = "Fastest is ${stats.quickest.title}; ${stats.longest.title} takes the longest.",
        style = CookVrxTheme.note.copy(color = colors.mutedInk),
    )

    val sectionModifier = Modifier.padding(top = dimensions.collectionInfoSectionTopSpacing)
    CountSection(title = "By category", group = stats.byCategory, modifier = sectionModifier)
    CountSection(title = "By difficulty", group = stats.byDifficulty, modifier = sectionModifier)
    CountSection(
        title = "Pantry staples",
        group = stats.staples,
        modifier = sectionModifier,
        countLabel = { count -> "$count/$recipeCount" },
    )
  }
}

@Composable
private fun CountSection(
    title: String,
    group: CountGroup,
    modifier: Modifier = Modifier,
    countLabel: (Int) -> String = Int::toString,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(modifier = modifier.fillMaxWidth()) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Eyebrow(title, color = colors.ink)
      Spacer(modifier = Modifier.width(dimensions.collectionInfoSectionRuleSpacing))
      Box(
          modifier =
              Modifier.weight(1f)
                  .height(dimensions.collectionInfoSectionRuleHeight)
                  .background(colors.ink.copy(alpha = 0.22f)),
      )
    }
    group.counts.forEach { (label, count) ->
      Column(
          modifier =
              Modifier.fillMaxWidth()
                  .padding(vertical = dimensions.collectionInfoRowVerticalPadding),
      ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
          BasicText(text = label, style = CookVrxTheme.body.copy(color = colors.ink))
          Eyebrow(countLabel(count), color = colors.ink)
        }
        Box(
            modifier =
                Modifier.fillMaxWidth(count.toFloat() / group.total)
                    .height(dimensions.collectionInfoBarHeight)
                    .background(colors.citrus),
        )
      }
    }
  }
}
