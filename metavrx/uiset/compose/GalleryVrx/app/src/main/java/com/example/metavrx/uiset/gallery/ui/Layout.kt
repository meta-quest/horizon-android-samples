/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.card.SecondaryCard
import metavrx.uiset.compose.theme.LocalContentColors
import metavrx.uiset.compose.theme.UiSetTheme

/** Page title, shown top-left on every screen. */
@Composable
fun ScreenHeader(title: String) {
  Text(
      text = title,
      style = UiSetTheme.typography.display,
      color = UiSetTheme.colorScheme.background.content.primary,
  )
}

/**
 * A labelled group of related components, in a [SecondaryCard].
 *
 * Every screen is built from these so spacing and grouping stay consistent without any screen
 * inventing its own — the failure mode that makes gallery apps look assembled rather than designed.
 */
@Composable
fun Demo(title: String, description: String? = null, content: @Composable ColumnScope.() -> Unit) {
  val spacing = UiSetTheme.dimensions.spacing

  SecondaryCard(modifier = Modifier.fillMaxWidth()) {
    Text(
        text = title,
        style = UiSetTheme.typography.title,
        color = LocalContentColors.current.primary,
    )
    if (description != null) {
      Spacer(Modifier.height(spacing.xSmall))
      Text(
          text = description,
          style = UiSetTheme.typography.bodySmall,
          color = LocalContentColors.current.secondary,
      )
    }
    Spacer(Modifier.height(spacing.large))
    content()
  }
}

/** Vertical stack of [Demo] cards — the standard body of every screen. */
@Composable
fun Screen(content: @Composable ColumnScope.() -> Unit) {
  Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(UiSetTheme.dimensions.spacing.twoXLarge),
      content = content,
  )
}

/** Wrapping row for laying out component variants side by side. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DemoRow(content: @Composable () -> Unit) {
  val spacing = UiSetTheme.dimensions.spacing

  FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(spacing.medium),
      verticalArrangement = Arrangement.spacedBy(spacing.medium),
  ) {
    content()
  }
}

/**
 * Adapts one of the UI Set's icons to the `(@Composable () -> Unit)` slot its `icon` and
 * `leadingIcon` parameters take. Call it as `icon { Icons.Regular.Add }`.
 *
 * The parameter is a `@Composable` LAMBDA rather than an `ImageVector`, and that is load-bearing:
 * every `Icons.Regular.*` property has a `@Composable` getter (it resolves a vector resource), so
 * reading one IS a composable invocation. Taking an `ImageVector` directly would force callers to
 * evaluate it at the call site, which fails anywhere outside composition — an enum constructor
 * argument, a top-level `val`, a `remember { }` block.
 *
 * The outer function is deliberately NOT `@Composable`: it is a plain factory that *returns* a
 * composable lambda, and the vector is read inside that lambda when [Icon] runs.
 */
fun icon(vector: @Composable () -> ImageVector): @Composable () -> Unit = {
  Icon(imageVector = vector(), contentDescription = null, modifier = Modifier.size(24.dp))
}
