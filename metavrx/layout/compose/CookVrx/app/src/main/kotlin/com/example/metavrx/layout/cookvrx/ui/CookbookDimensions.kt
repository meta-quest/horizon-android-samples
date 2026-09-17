/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Stable
interface CookbookDimensions {
  val libraryColumnCount: Int
  val libraryMinCardWidth: Dp

  val detailWindowWidth: Dp
  val detailWindowHeight: Dp
  val cookToolWindowWidth: Dp
  val inlineCookToolWidth: Dp
  val mobileCookToolHeight: Dp
  val mobileToolPickerHorizontalPadding: Dp
  val mobileToolPickerVerticalPadding: Dp
  val mobileToolPickerSpacing: Dp

  val artworkCornerRadius: Dp
  val artworkBorderWidth: Dp
  val artworkPlateBorderWidth: Dp
  val artworkLabelPadding: Dp

  val libraryContentPadding: Dp
  val libraryHorizontalSpacing: Dp
  val libraryVerticalSpacing: Dp
  val libraryFooterTopPadding: Dp
  val libraryFooterBottomPadding: Dp
  val libraryHeaderRuleWidth: Dp
  val libraryHeaderRuleHeight: Dp
  val libraryHeaderRuleSpacing: Dp
  val libraryRecipeCountHorizontalPadding: Dp
  val libraryRecipeCountVerticalPadding: Dp
  val libraryTitleTopSpacing: Dp
  val libraryDescriptionTopSpacing: Dp
  val libraryDividerTopSpacing: Dp
  val libraryDividerHeight: Dp
  val categoryVerticalPadding: Dp
  val categorySpacing: Dp
  val recipeCardSelectedBorderWidth: Dp
  val recipeCardPadding: Dp
  val recipeCardArtworkHeight: Dp
  val recipeCardArtworkSpacing: Dp
  val recipeCardMetaSpacing: Dp
  val recipeCardFooterSpacing: Dp
  val recipeCardFooterRuleWidth: Dp
  val recipeCardFooterRuleHeight: Dp
  val recipeCardFooterRuleSpacing: Dp

  val detailContentPadding: Dp
  val detailArtworkTopSpacing: Dp
  val detailArtworkHeight: Dp
  val detailTitleTopSpacing: Dp
  val detailFactsTopSpacing: Dp
  val detailActionTopSpacing: Dp
  val detailSectionTopSpacing: Dp
  val detailSectionRuleSpacing: Dp
  val detailSectionRuleHeight: Dp
  val detailIngredientsTopSpacing: Dp
  val detailIngredientVerticalPadding: Dp
  val detailIngredientIndexSize: Dp
  val detailIngredientIndexCornerRadius: Dp
  val detailIngredientSpacing: Dp
  val detailFooterTopSpacing: Dp
  val detailFooterPadding: Dp
  val detailFactHorizontalPadding: Dp
  val detailFactVerticalPadding: Dp

  val stepContentPadding: Dp
  val stepBadgePadding: Dp
  val stepNumberSpacing: Dp
  val stepRuleHeight: Dp
  val stepInstructionSpacing: Dp
  val stepTimerSpacing: Dp
  val stepControlSpacing: Dp
  val completionHorizontalPadding: Dp
  val completionMessageSpacing: Dp

  val ingredientsContentPadding: Dp
  val ingredientsTitleSpacing: Dp
  val ingredientsSubtitleSpacing: Dp
  val ingredientsListSpacing: Dp
  val ingredientRowVerticalPadding: Dp
  val ingredientRowSpacing: Dp
  val ingredientCheckboxSize: Dp
  val ingredientCheckboxBorderWidth: Dp
  val ingredientTipSpacing: Dp
  val ingredientTipRuleWidth: Dp
  val ingredientTipRuleHeight: Dp
  val ingredientTipRuleSpacing: Dp
  val ingredientDividerHeight: Dp

  val timersContentPadding: Dp
  val timerQuickActionsTopSpacing: Dp
  val timerQuickActionSpacing: Dp
  val timersListSpacing: Dp
  val timerEmptyHorizontalPadding: Dp
  val timerEmptyVerticalPadding: Dp
  val timerCardSpacing: Dp
  val timerCardPadding: Dp
  val timerClockSpacing: Dp
  val timerProgressSpacing: Dp
  val timerProgressHeight: Dp
  val timerExtensionSpacing: Dp
  val timerExtensionTopSpacing: Dp

  val displayFontSize: TextUnit
  val displayLineHeight: TextUnit
  val displayLetterSpacing: TextUnit
  val titleFontSize: TextUnit
  val titleLineHeight: TextUnit
  val titleLetterSpacing: TextUnit
  val recipeTitleFontSize: TextUnit
  val recipeTitleLineHeight: TextUnit
  val bodyFontSize: TextUnit
  val bodyLineHeight: TextUnit
  val labelFontSize: TextUnit
  val labelLineHeight: TextUnit
  val labelLetterSpacing: TextUnit
  val noteFontSize: TextUnit
  val noteLineHeight: TextUnit
  val artworkLabelFontSize: TextUnit
  val stepNumberFontSize: TextUnit
  val stepNumberLineHeight: TextUnit
  val stepInstructionFontSize: TextUnit
  val stepInstructionLineHeight: TextUnit
  val completionFontSize: TextUnit
  val completionLineHeight: TextUnit
  val timerEmptyFontSize: TextUnit
  val timerClockFontSize: TextUnit
  val timerClockLineHeight: TextUnit
}

internal fun CookbookDimensions.libraryColumnCountFor(width: Dp): Int {
  val availableWidth = width - libraryContentPadding * 2 + libraryHorizontalSpacing
  val perColumnWidth = libraryMinCardWidth + libraryHorizontalSpacing
  return (availableWidth.value / perColumnWidth.value).toInt().coerceIn(1, libraryColumnCount)
}

object DefaultCookbookDimensions : CookbookDimensions {
  override val libraryColumnCount = 3
  override val libraryMinCardWidth = 240.dp

  override val detailWindowWidth = 360.dp
  override val detailWindowHeight = 560.dp
  override val cookToolWindowWidth = 300.dp
  override val inlineCookToolWidth = 230.dp
  override val mobileCookToolHeight = 230.dp
  override val mobileToolPickerHorizontalPadding = 16.dp
  override val mobileToolPickerVerticalPadding = 8.dp
  override val mobileToolPickerSpacing = 8.dp

  override val artworkCornerRadius = 4.dp
  override val artworkBorderWidth = 1.dp
  override val artworkPlateBorderWidth = 7.dp
  override val artworkLabelPadding = 10.dp

  override val libraryContentPadding = 24.dp
  override val libraryHorizontalSpacing = 16.dp
  override val libraryVerticalSpacing = 16.dp
  override val libraryFooterTopPadding = 12.dp
  override val libraryFooterBottomPadding = 28.dp
  override val libraryHeaderRuleWidth = 42.dp
  override val libraryHeaderRuleHeight = 4.dp
  override val libraryHeaderRuleSpacing = 10.dp
  override val libraryRecipeCountHorizontalPadding = 12.dp
  override val libraryRecipeCountVerticalPadding = 7.dp
  override val libraryTitleTopSpacing = 20.dp
  override val libraryDescriptionTopSpacing = 10.dp
  override val libraryDividerTopSpacing = 22.dp
  override val libraryDividerHeight = 1.dp
  override val categoryVerticalPadding = 2.dp
  override val categorySpacing = 8.dp
  override val recipeCardSelectedBorderWidth = 3.dp
  override val recipeCardPadding = 9.dp
  override val recipeCardArtworkHeight = 124.dp
  override val recipeCardArtworkSpacing = 12.dp
  override val recipeCardMetaSpacing = 7.dp
  override val recipeCardFooterSpacing = 8.dp
  override val recipeCardFooterRuleWidth = 20.dp
  override val recipeCardFooterRuleHeight = 2.dp
  override val recipeCardFooterRuleSpacing = 7.dp

  override val detailContentPadding = 18.dp
  override val detailArtworkTopSpacing = 14.dp
  override val detailArtworkHeight = 180.dp
  override val detailTitleTopSpacing = 16.dp
  override val detailFactsTopSpacing = 10.dp
  override val detailActionTopSpacing = 18.dp
  override val detailSectionTopSpacing = 24.dp
  override val detailSectionRuleSpacing = 10.dp
  override val detailSectionRuleHeight = 1.dp
  override val detailIngredientsTopSpacing = 8.dp
  override val detailIngredientVerticalPadding = 8.dp
  override val detailIngredientIndexSize = 27.dp
  override val detailIngredientIndexCornerRadius = 2.dp
  override val detailIngredientSpacing = 10.dp
  override val detailFooterTopSpacing = 14.dp
  override val detailFooterPadding = 14.dp
  override val detailFactHorizontalPadding = 10.dp
  override val detailFactVerticalPadding = 9.dp

  override val stepContentPadding = 26.dp
  override val stepBadgePadding = 11.dp
  override val stepNumberSpacing = 14.dp
  override val stepRuleHeight = 2.dp
  override val stepInstructionSpacing = 18.dp
  override val stepTimerSpacing = 24.dp
  override val stepControlSpacing = 12.dp
  override val completionHorizontalPadding = 8.dp
  override val completionMessageSpacing = 18.dp

  override val ingredientsContentPadding = 18.dp
  override val ingredientsTitleSpacing = 8.dp
  override val ingredientsSubtitleSpacing = 4.dp
  override val ingredientsListSpacing = 18.dp
  override val ingredientRowVerticalPadding = 9.dp
  override val ingredientRowSpacing = 10.dp
  override val ingredientCheckboxSize = 24.dp
  override val ingredientCheckboxBorderWidth = 1.5.dp
  override val ingredientTipSpacing = 2.dp
  override val ingredientTipRuleWidth = 12.dp
  override val ingredientTipRuleHeight = 2.dp
  override val ingredientTipRuleSpacing = 5.dp
  override val ingredientDividerHeight = 1.dp

  override val timersContentPadding = 18.dp
  override val timerQuickActionsTopSpacing = 10.dp
  override val timerQuickActionSpacing = 6.dp
  override val timersListSpacing = 18.dp
  override val timerEmptyHorizontalPadding = 14.dp
  override val timerEmptyVerticalPadding = 36.dp
  override val timerCardSpacing = 10.dp
  override val timerCardPadding = 13.dp
  override val timerClockSpacing = 9.dp
  override val timerProgressSpacing = 10.dp
  override val timerProgressHeight = 5.dp
  override val timerExtensionSpacing = 8.dp
  override val timerExtensionTopSpacing = 12.dp

  override val displayFontSize = 42.sp
  override val displayLineHeight = 40.sp
  override val displayLetterSpacing = (-1.2).sp
  override val titleFontSize = 26.sp
  override val titleLineHeight = 28.sp
  override val titleLetterSpacing = (-0.4).sp
  override val recipeTitleFontSize = 20.sp
  override val recipeTitleLineHeight = 22.sp
  override val bodyFontSize = 15.sp
  override val bodyLineHeight = 21.sp
  override val labelFontSize = 11.sp
  override val labelLineHeight = 14.sp
  override val labelLetterSpacing = 1.2.sp
  override val noteFontSize = 13.sp
  override val noteLineHeight = 17.sp
  override val artworkLabelFontSize = 10.sp
  override val stepNumberFontSize = 58.sp
  override val stepNumberLineHeight = 58.sp
  override val stepInstructionFontSize = 30.sp
  override val stepInstructionLineHeight = 36.sp
  override val completionFontSize = 50.sp
  override val completionLineHeight = 48.sp
  override val timerEmptyFontSize = 13.sp
  override val timerClockFontSize = 38.sp
  override val timerClockLineHeight = 38.sp
}
