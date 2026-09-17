/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import android.os.SystemClock
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.metavrx.layout.cookvrx.state.KitchenTimer
import kotlinx.coroutines.delay
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.IconButton
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.card.OutlinedCard
import metavrx.uiset.compose.card.PrimaryCard
import metavrx.uiset.compose.card.SecondaryCard
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.icons.Icons

@Composable
fun TimersPanel(
    timers: SnapshotStateList<KitchenTimer>,
    onAddQuickTimer: (Int) -> Unit,
    onExtendTimer: (Long, Int) -> Unit,
    onRemoveTimer: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  var now by remember { mutableLongStateOf(SystemClock.elapsedRealtime()) }
  LaunchedEffect(Unit) {
    while (true) {
      delay(1_000)
      now = SystemClock.elapsedRealtime()
    }
  }

  Column(
      modifier =
          modifier
              .fillMaxSize()
              .background(colors.ocean)
              .padding(dimensions.timersContentPadding)
              .verticalScroll(rememberScrollState()),
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Eyebrow("Station 02", color = colors.citrus)
      BasicText(text = "Timers", style = CookVrxTheme.title.copy(color = colors.cream))
      Spacer(modifier = Modifier.height(dimensions.timerQuickActionsTopSpacing))
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(dimensions.timerQuickActionSpacing),
      ) {
        LabelButton(
            label = "+30 SEC",
            onClick = { onAddQuickTimer(30) },
            modifier = Modifier.weight(1f),
            style = ButtonStyle.Secondary,
            expanded = true,
            labelTextStyle = CookVrxTheme.label,
        )
        LabelButton(
            label = "+5 MIN",
            onClick = { onAddQuickTimer(300) },
            modifier = Modifier.weight(1f),
            style = ButtonStyle.Secondary,
            expanded = true,
            labelTextStyle = CookVrxTheme.label,
        )
      }
    }
    Spacer(modifier = Modifier.height(dimensions.timersListSpacing))
    if (timers.isEmpty()) {
      OutlinedCard(
          modifier = Modifier.fillMaxWidth(),
          dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = 0.dp),
      ) {
        Box(
            modifier =
                Modifier.fillMaxWidth()
                    .padding(
                        horizontal = dimensions.timerEmptyHorizontalPadding,
                        vertical = dimensions.timerEmptyVerticalPadding,
                    ),
            contentAlignment = Alignment.Center,
        ) {
          BasicText(
              text = "NO FIRES\nTO WATCH YET",
              style =
                  CookVrxTheme.label.copy(
                      color = colors.cream.copy(alpha = 0.72f),
                      fontSize = dimensions.timerEmptyFontSize,
                      textAlign = TextAlign.Center,
                  ),
          )
        }
      }
    } else {
      timers.forEach { timer ->
        val remaining = ((timer.endsAtElapsedRealtime - now).coerceAtLeast(0L) / 1_000L).toInt()
        TimerCard(
            timer = timer,
            remainingSeconds = remaining,
            onExtend = { seconds -> onExtendTimer(timer.id, seconds) },
            onRemove = { onRemoveTimer(timer.id) },
        )
        Spacer(modifier = Modifier.height(dimensions.timerCardSpacing))
      }
    }
    Spacer(modifier = Modifier.height(dimensions.timersListSpacing))
    BasicText(
        text =
            "Use step timers for precision. Add a quick check whenever a pan, cup, or broiler needs your future attention.",
        style = CookVrxTheme.note.copy(color = colors.cream.copy(alpha = 0.72f)),
    )
  }
}

@Composable
private fun TimerCard(
    timer: KitchenTimer,
    remainingSeconds: Int,
    onExtend: (Int) -> Unit,
    onRemove: () -> Unit,
) {
  val dimensions = CookVrxTheme.dimensions
  val progress =
      if (timer.durationSeconds == 0) 0f
      else remainingSeconds.toFloat().div(timer.durationSeconds).coerceIn(0f, 1f)
  val finished = remainingSeconds == 0
  if (finished) {
    PrimaryCard(
        modifier = Modifier.fillMaxWidth(),
        dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = dimensions.timerCardPadding),
    ) {
      TimerCardContent(
          timer = timer,
          remainingSeconds = remainingSeconds,
          progress = progress,
          finished = true,
          onExtend = onExtend,
          onRemove = onRemove,
      )
    }
  } else {
    SecondaryCard(
        modifier = Modifier.fillMaxWidth(),
        dimensions = UiSetTheme.dimensions.cards.copy(contentPadding = dimensions.timerCardPadding),
    ) {
      TimerCardContent(
          timer = timer,
          remainingSeconds = remainingSeconds,
          progress = progress,
          finished = false,
          onExtend = onExtend,
          onRemove = onRemove,
      )
    }
  }
}

@Suppress("JetpackComposeBoxWithSingleComposable") // Box is the progress track behind the fill bar
@Composable
private fun TimerCardContent(
    timer: KitchenTimer,
    remainingSeconds: Int,
    progress: Float,
    finished: Boolean,
    onExtend: (Int) -> Unit,
    onRemove: () -> Unit,
) {
  val colors = CookVrxTheme.colors
  val dimensions = CookVrxTheme.dimensions
  Column(
      modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Eyebrow(
          text = if (finished) "Ready now" else timer.label,
          modifier = Modifier.weight(1f),
          color = colors.ink,
      )
      IconButton(
          icon = { Icon(imageVector = Icons.Regular.Close, contentDescription = null) },
          onClick = onRemove,
          contentDescription = "Remove timer",
          modifier = Modifier.width(UiSetTheme.dimensions.minimumInteractiveSize),
          style =
              ButtonStyle.Borderless.copy(
                  containerColor = colors.ink.copy(alpha = 0.08f),
                  contentColor = colors.ink,
              ),
      )
    }
    Spacer(modifier = Modifier.height(dimensions.timerClockSpacing))
    BasicText(
        text = if (finished) "DONE" else formatClock(remainingSeconds),
        style =
            CookVrxTheme.display.copy(
                color = colors.ink,
                fontSize = dimensions.timerClockFontSize,
                lineHeight = dimensions.timerClockLineHeight,
            ),
    )
    Spacer(modifier = Modifier.height(dimensions.timerProgressSpacing))
    Box(
        modifier =
            Modifier.fillMaxWidth()
                .height(dimensions.timerProgressHeight)
                .background(colors.ink.copy(alpha = 0.14f)),
    ) {
      Box(
          modifier =
              Modifier.fillMaxWidth(progress)
                  .height(dimensions.timerProgressHeight)
                  .background(if (finished) Color.Transparent else colors.tomato),
      )
    }
    Spacer(modifier = Modifier.height(dimensions.timerExtensionTopSpacing))
    Row(horizontalArrangement = Arrangement.spacedBy(dimensions.timerExtensionSpacing)) {
      LabelButton(
          label = "+30 SEC",
          onClick = { onExtend(30) },
          modifier = Modifier.weight(1f),
          style = ButtonStyle.Secondary,
          expanded = true,
          labelTextStyle = CookVrxTheme.label,
      )
      LabelButton(
          label = "+5 MIN",
          onClick = { onExtend(300) },
          modifier = Modifier.weight(1f),
          style = ButtonStyle.Secondary,
          expanded = true,
          labelTextStyle = CookVrxTheme.label,
      )
    }
  }
}
