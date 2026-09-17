/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import kotlin.math.roundToInt
import metavrx.uiset.compose.theme.BrushSpec
import metavrx.uiset.compose.theme.ColorRole
import metavrx.uiset.compose.theme.ContentColors
import metavrx.uiset.compose.theme.InteractionOverlay
import metavrx.uiset.compose.theme.ProgressColors
import metavrx.uiset.compose.theme.StatusColors
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.lightColorScheme

@Immutable
data class CookVrxColors(
    val ink: Color,
    val paper: Color,
    val cream: Color,
    val tomato: Color,
    val citrus: Color,
    val leaf: Color,
    val ocean: Color,
    val mutedInk: Color,
)

private val FieldNoteColors = CookVrxColors(
    ink = Color(0xFF241B16),
    paper = Color(0xFFF0E2C6),
    cream = Color(0xFFFFF8E9),
    tomato = Color(0xFFE84C2E),
    citrus = Color(0xFFE6D64A),
    leaf = Color(0xFF4E7A55),
    ocean = Color(0xFF176C72),
    mutedInk = Color(0xFF75675B),
)

private val FieldNoteContent = ContentColors(
    primary = FieldNoteColors.ink,
    secondary = FieldNoteColors.mutedInk,
    icon = FieldNoteColors.ink,
)

private val FieldNoteBaseScheme = lightColorScheme()

// `surface` reads as tomato rather than a neutral because the one PrimaryCard in the app is the
// finished-timer card, where the fill IS the alarm.
private val CookVrxColorScheme =
    FieldNoteBaseScheme.copy(
        background =
            ColorRole(
                container =
                    BrushSpec.VerticalGradient(FieldNoteColors.cream, FieldNoteColors.paper),
                content = FieldNoteContent,
            ),
        surface =
            ColorRole(
                container = BrushSpec.Solid(FieldNoteColors.tomato),
                content = ContentColors(FieldNoteColors.ink),
            ),
        surfaceVariant = ColorRole(BrushSpec.Solid(FieldNoteColors.cream), FieldNoteContent),
        accent =
            ColorRole(
                container = BrushSpec.Solid(FieldNoteColors.tomato),
                content = ContentColors(FieldNoteColors.cream),
            ),
        accentMuted =
            ColorRole(
                container = BrushSpec.Solid(FieldNoteColors.citrus),
                content = ContentColors(FieldNoteColors.ink),
            ),
        selected =
            ColorRole(
                container = BrushSpec.Solid(FieldNoteColors.ocean),
                content = ContentColors(FieldNoteColors.cream),
            ),
        positive =
            StatusColors(
                content = FieldNoteColors.leaf,
                container = FieldNoteColors.leaf,
                onContainer = FieldNoteColors.cream,
            ),
        negative =
            StatusColors(
                content = FieldNoteColors.tomato,
                container = FieldNoteColors.tomato,
                onContainer = FieldNoteColors.cream,
            ),
        outline = FieldNoteColors.mutedInk,
        interactions =
            FieldNoteBaseScheme.interactions.copy(
                default =
                    InteractionOverlay(
                        hover = FieldNoteColors.tomato.copy(alpha = 0.18f),
                        pressed = FieldNoteColors.ink.copy(alpha = 0.18f),
                    ),
            ),
        progress =
            ProgressColors(
                track = FieldNoteColors.ink.copy(alpha = 0.14f),
                indicator = FieldNoteColors.tomato,
                onMediaTrack = FieldNoteColors.cream.copy(alpha = 0.3f),
                onMediaIndicator = FieldNoteColors.cream,
            ),
    )

private val LocalCookVrxColors = staticCompositionLocalOf { FieldNoteColors }
private val LocalCookbookDimensions =
    staticCompositionLocalOf<CookbookDimensions> {
      error("Cookbook dimensions were not provided")
    }

object CookVrxTheme {
  val colors: CookVrxColors
    @Composable @ReadOnlyComposable get() = LocalCookVrxColors.current

  val dimensions: CookbookDimensions
    @Composable @ReadOnlyComposable get() = LocalCookbookDimensions.current

  val display: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Black,
        fontSize = dimensions.displayFontSize,
        lineHeight = dimensions.displayLineHeight,
        letterSpacing = dimensions.displayLetterSpacing,
    )

  val title: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = dimensions.titleFontSize,
        lineHeight = dimensions.titleLineHeight,
        letterSpacing = dimensions.titleLetterSpacing,
    )

  val recipeTitle: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = dimensions.recipeTitleFontSize,
        lineHeight = dimensions.recipeTitleLineHeight,
    )

  val body: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = dimensions.bodyFontSize,
        lineHeight = dimensions.bodyLineHeight,
    )

  val bodyStrong: TextStyle
    @Composable @ReadOnlyComposable get() = body.copy(fontWeight = FontWeight.Bold)

  val label: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = dimensions.labelFontSize,
        lineHeight = dimensions.labelLineHeight,
        fontWeight = FontWeight.Bold,
        letterSpacing = dimensions.labelLetterSpacing,
    )

  val note: TextStyle
    @Composable
    @ReadOnlyComposable
    get() = TextStyle(
        fontFamily = FontFamily.Serif,
        fontStyle = FontStyle.Italic,
        fontSize = dimensions.noteFontSize,
        lineHeight = dimensions.noteLineHeight,
    )
}

@Composable
fun CookVrxTheme(
    dimensions: CookbookDimensions = DefaultCookbookDimensions,
    content: @Composable () -> Unit,
) {
  UiSetTheme(colorScheme = CookVrxColorScheme) {
    CompositionLocalProvider(
        LocalCookVrxColors provides FieldNoteColors,
        LocalCookbookDimensions provides dimensions,
        content = content,
    )
  }
}

@Composable
fun Eyebrow(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = CookVrxTheme.colors.mutedInk,
) {
  BasicText(
      text = text.uppercase(),
      modifier = modifier,
      style = CookVrxTheme.label.copy(color = color),
  )
}

@Composable
fun DishArtwork(recipeId: String, modifier: Modifier = Modifier) {
  val dimensions = CookVrxTheme.dimensions
  val colors = artworkColors(recipeId)
  val dishFraction = 0.8f
  Box(
      modifier =
          modifier
              .clip(RoundedCornerShape(dimensions.artworkCornerRadius))
              .background(Brush.linearGradient(listOf(colors.background, colors.backgroundEnd)))
              .border(
                  dimensions.artworkBorderWidth,
                  CookVrxTheme.colors.ink.copy(alpha = 0.16f),
                  RoundedCornerShape(dimensions.artworkCornerRadius),
              ),
  ) {
    Box(
        modifier =
            Modifier.align(Alignment.Center)
                .shortestSideFraction(dishFraction)
                .rotate(-8f)
                .background(colors.plate, CircleShape)
                .border(
                    dimensions.artworkPlateBorderWidth,
                    CookVrxTheme.colors.cream.copy(alpha = 0.7f),
                    CircleShape,
                ),
    )
    Box(
        modifier =
            Modifier.align(Alignment.Center)
                .shortestSideFraction(dishFraction * 0.34f / 0.62f)
                .background(colors.food, CircleShape),
    )
    Box(
        modifier =
            Modifier.align(Alignment.Center)
                .shortestSideFraction(dishFraction * 0.16f / 0.62f)
                .rotate(18f)
                .background(colors.garnish, RoundedCornerShape(40)),
    )
    BasicText(
        text = recipeId.take(2).uppercase(),
        modifier = Modifier.align(Alignment.BottomEnd).padding(dimensions.artworkLabelPadding),
        style =
            CookVrxTheme.label.copy(
                color = colors.backgroundEnd.copy(alpha = 0.75f),
                fontSize = dimensions.artworkLabelFontSize,
                textAlign = TextAlign.End,
            ),
    )
  }
}

private fun Modifier.shortestSideFraction(fraction: Float): Modifier =
    layout { measurable, constraints ->
      val maxSide =
          when {
            constraints.hasBoundedWidth && constraints.hasBoundedHeight ->
                minOf(constraints.maxWidth, constraints.maxHeight)
            constraints.hasBoundedWidth -> constraints.maxWidth
            constraints.hasBoundedHeight -> constraints.maxHeight
            else -> 0
          }
      val side = (maxSide * fraction).roundToInt()
      val placeable = measurable.measure(Constraints.fixed(side, side))
      layout(side, side) { placeable.placeRelative(0, 0) }
    }

private data class ArtworkColors(
    val background: Color,
    val backgroundEnd: Color,
    val plate: Color,
    val food: Color,
    val garnish: Color,
)

private fun artworkColors(id: String): ArtworkColors =
    when (id) {
      "smoky_shakshuka" ->
          ArtworkColors(
              Color(0xFFFFB16D),
              Color(0xFFD84227),
              Color(0xFF7A1E18),
              Color(0xFFF8D985),
              Color(0xFF3D6B45),
          )
      "midnight_miso_noodles" ->
          ArtworkColors(
              Color(0xFF284559),
              Color(0xFF101E29),
              Color(0xFFD8C39B),
              Color(0xFF9B6B3D),
              Color(0xFFE65338),
          )
      "charred_lemon_chicken" ->
          ArtworkColors(
              Color(0xFFE6D64A),
              Color(0xFF8E8B2F),
              Color(0xFFF1D0A1),
              Color(0xFF8B4B2B),
              Color(0xFF214C3A),
          )
      "citrus_olive_cake" ->
          ArtworkColors(
              Color(0xFFF6C76E),
              Color(0xFFE97952),
              Color(0xFFFFF3D3),
              Color(0xFFE8B45C),
              Color(0xFF8A6D2A),
          )
      "crispy_mushroom_rice" ->
          ArtworkColors(
              Color(0xFFB6A284),
              Color(0xFF5B4939),
              Color(0xFFF3E3C1),
              Color(0xFF806048),
              Color(0xFFB13C2F),
          )
      "green_goddess_beans" ->
          ArtworkColors(
              Color(0xFFB6D37B),
              Color(0xFF39724E),
              Color(0xFFF2E9C9),
              Color(0xFF7FB66D),
              Color(0xFFE4E85C),
          )
      "market_tomato_toast" ->
          ArtworkColors(
              Color(0xFFF6A166),
              Color(0xFFE44D32),
              Color(0xFFD9A96E),
              Color(0xFFC22D29),
              Color(0xFF416943),
          )
      else ->
          ArtworkColors(
              Color(0xFFD2B8D8),
              Color(0xFF5C4666),
              Color(0xFFFFE6C2),
              Color(0xFF292025),
              Color(0xFFE9C849),
          )
    }

fun formatClock(totalSeconds: Int): String {
  val safeSeconds = totalSeconds.coerceAtLeast(0)
  return "%d:%02d".format(safeSeconds / 60, safeSeconds % 60)
}
