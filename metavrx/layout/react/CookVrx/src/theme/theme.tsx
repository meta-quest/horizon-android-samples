/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {TextStyle} from 'react-native';

import React, {createContext, useContext, useMemo} from 'react';

export type CookVrxColors = Readonly<{
  ink: string;
  paper: string;
  cream: string;
  tomato: string;
  citrus: string;
  leaf: string;
  ocean: string;
  mutedInk: string;
}>;

export const fieldNoteColors: CookVrxColors = {
  ink: '#241B16',
  paper: '#F0E2C6',
  cream: '#FFF8E9',
  tomato: '#E84C2E',
  citrus: '#E6D64A',
  leaf: '#4E7A55',
  ocean: '#176C72',
  mutedInk: '#75675B',
};

/** `Color.copy(alpha = ...)` equivalent for the `#RRGGBB` literals above. */
export function withAlpha(color: string, alpha: number): string {
  const value = parseInt(color.slice(1), 16);
  const r = (value >> 16) & 0xff;
  const g = (value >> 8) & 0xff;
  const b = value & 0xff;
  return `rgba(${r}, ${g}, ${b}, ${alpha})`;
}

/**
 * Every spacing, size, and type metric the cookbook draws with, injectable as a
 * profile so a headset build can scale the layout without touching component code.
 */
export type CookbookDimensions = Readonly<{
  /** Upper bound on the recipe grid; the grid narrows below this to fit its measured width. */
  libraryColumnCount: number;
  /** Narrowest a recipe card may get before the grid drops a column. */
  libraryMinCardWidth: number;

  detailWindowWidth: number;
  detailWindowHeight: number;
  cookToolWindowWidth: number;
  inlineCookToolWidth: number;
  mobileCookToolHeight: number;
  mobileToolPickerHorizontalPadding: number;
  mobileToolPickerVerticalPadding: number;
  mobileToolPickerSpacing: number;

  artworkCornerRadius: number;
  artworkBorderWidth: number;
  artworkPlateBorderWidth: number;
  artworkLabelPadding: number;

  libraryContentPadding: number;
  libraryHorizontalSpacing: number;
  libraryVerticalSpacing: number;
  libraryFooterTopPadding: number;
  libraryFooterBottomPadding: number;
  libraryHeaderRuleWidth: number;
  libraryHeaderRuleHeight: number;
  libraryHeaderRuleSpacing: number;
  libraryRecipeCountHorizontalPadding: number;
  libraryRecipeCountVerticalPadding: number;
  libraryTitleTopSpacing: number;
  libraryDescriptionTopSpacing: number;
  libraryDividerTopSpacing: number;
  libraryDividerHeight: number;
  categoryVerticalPadding: number;
  categorySpacing: number;
  recipeCardSelectedBorderWidth: number;
  recipeCardPadding: number;
  recipeCardArtworkHeight: number;
  recipeCardArtworkSpacing: number;
  recipeCardMetaSpacing: number;
  recipeCardFooterSpacing: number;
  recipeCardFooterRuleWidth: number;
  recipeCardFooterRuleHeight: number;
  recipeCardFooterRuleSpacing: number;

  detailContentPadding: number;
  detailArtworkTopSpacing: number;
  detailArtworkHeight: number;
  detailTitleTopSpacing: number;
  detailFactsTopSpacing: number;
  detailActionTopSpacing: number;
  detailSectionTopSpacing: number;
  detailSectionRuleSpacing: number;
  detailSectionRuleHeight: number;
  detailIngredientsTopSpacing: number;
  detailIngredientVerticalPadding: number;
  detailIngredientIndexSize: number;
  detailIngredientIndexCornerRadius: number;
  detailIngredientSpacing: number;
  detailFooterTopSpacing: number;
  detailFooterPadding: number;
  detailFactHorizontalPadding: number;
  detailFactVerticalPadding: number;

  stepContentPadding: number;
  stepBadgePadding: number;
  stepNumberSpacing: number;
  stepRuleHeight: number;
  stepInstructionSpacing: number;
  stepTimerSpacing: number;
  stepControlSpacing: number;
  completionHorizontalPadding: number;
  completionMessageSpacing: number;

  ingredientsContentPadding: number;
  ingredientsTitleSpacing: number;
  ingredientsSubtitleSpacing: number;
  ingredientsListSpacing: number;
  ingredientRowVerticalPadding: number;
  ingredientRowSpacing: number;
  ingredientCheckboxSize: number;
  ingredientCheckboxBorderWidth: number;
  ingredientTipSpacing: number;
  ingredientTipRuleWidth: number;
  ingredientTipRuleHeight: number;
  ingredientTipRuleSpacing: number;
  ingredientDividerHeight: number;

  timersContentPadding: number;
  timerQuickActionsTopSpacing: number;
  timerQuickActionSpacing: number;
  timersListSpacing: number;
  timerEmptyHorizontalPadding: number;
  timerEmptyVerticalPadding: number;
  timerCardSpacing: number;
  timerCardPadding: number;
  timerClockSpacing: number;
  timerProgressSpacing: number;
  timerProgressHeight: number;
  timerExtensionSpacing: number;
  timerExtensionTopSpacing: number;

  displayFontSize: number;
  displayLineHeight: number;
  displayLetterSpacing: number;
  titleFontSize: number;
  titleLineHeight: number;
  titleLetterSpacing: number;
  recipeTitleFontSize: number;
  recipeTitleLineHeight: number;
  bodyFontSize: number;
  bodyLineHeight: number;
  labelFontSize: number;
  labelLineHeight: number;
  labelLetterSpacing: number;
  noteFontSize: number;
  noteLineHeight: number;
  artworkLabelFontSize: number;
  stepNumberFontSize: number;
  stepNumberLineHeight: number;
  stepInstructionFontSize: number;
  stepInstructionLineHeight: number;
  completionFontSize: number;
  completionLineHeight: number;
  timerEmptyFontSize: number;
  timerClockFontSize: number;
  timerClockLineHeight: number;
}>;

export const defaultCookbookDimensions: CookbookDimensions = {
  libraryColumnCount: 3,
  libraryMinCardWidth: 240,

  detailWindowWidth: 360,
  detailWindowHeight: 560,
  cookToolWindowWidth: 300,
  inlineCookToolWidth: 230,
  mobileCookToolHeight: 230,
  mobileToolPickerHorizontalPadding: 16,
  mobileToolPickerVerticalPadding: 8,
  mobileToolPickerSpacing: 8,

  artworkCornerRadius: 4,
  artworkBorderWidth: 1,
  artworkPlateBorderWidth: 7,
  artworkLabelPadding: 10,

  libraryContentPadding: 24,
  libraryHorizontalSpacing: 16,
  libraryVerticalSpacing: 16,
  libraryFooterTopPadding: 12,
  libraryFooterBottomPadding: 28,
  libraryHeaderRuleWidth: 42,
  libraryHeaderRuleHeight: 4,
  libraryHeaderRuleSpacing: 10,
  libraryRecipeCountHorizontalPadding: 12,
  libraryRecipeCountVerticalPadding: 7,
  libraryTitleTopSpacing: 20,
  libraryDescriptionTopSpacing: 10,
  libraryDividerTopSpacing: 22,
  libraryDividerHeight: 1,
  categoryVerticalPadding: 2,
  categorySpacing: 8,
  recipeCardSelectedBorderWidth: 3,
  recipeCardPadding: 9,
  recipeCardArtworkHeight: 124,
  recipeCardArtworkSpacing: 12,
  recipeCardMetaSpacing: 7,
  recipeCardFooterSpacing: 8,
  recipeCardFooterRuleWidth: 20,
  recipeCardFooterRuleHeight: 2,
  recipeCardFooterRuleSpacing: 7,

  detailContentPadding: 18,
  detailArtworkTopSpacing: 14,
  detailArtworkHeight: 180,
  detailTitleTopSpacing: 16,
  detailFactsTopSpacing: 10,
  detailActionTopSpacing: 18,
  detailSectionTopSpacing: 24,
  detailSectionRuleSpacing: 10,
  detailSectionRuleHeight: 1,
  detailIngredientsTopSpacing: 8,
  detailIngredientVerticalPadding: 8,
  detailIngredientIndexSize: 27,
  detailIngredientIndexCornerRadius: 2,
  detailIngredientSpacing: 10,
  detailFooterTopSpacing: 14,
  detailFooterPadding: 14,
  detailFactHorizontalPadding: 10,
  detailFactVerticalPadding: 9,

  stepContentPadding: 26,
  stepBadgePadding: 11,
  stepNumberSpacing: 14,
  stepRuleHeight: 2,
  stepInstructionSpacing: 18,
  stepTimerSpacing: 24,
  stepControlSpacing: 12,
  completionHorizontalPadding: 8,
  completionMessageSpacing: 18,

  ingredientsContentPadding: 18,
  ingredientsTitleSpacing: 8,
  ingredientsSubtitleSpacing: 4,
  ingredientsListSpacing: 18,
  ingredientRowVerticalPadding: 9,
  ingredientRowSpacing: 10,
  ingredientCheckboxSize: 24,
  ingredientCheckboxBorderWidth: 1.5,
  ingredientTipSpacing: 2,
  ingredientTipRuleWidth: 12,
  ingredientTipRuleHeight: 2,
  ingredientTipRuleSpacing: 5,
  ingredientDividerHeight: 1,

  timersContentPadding: 18,
  timerQuickActionsTopSpacing: 10,
  timerQuickActionSpacing: 6,
  timersListSpacing: 18,
  timerEmptyHorizontalPadding: 14,
  timerEmptyVerticalPadding: 36,
  timerCardSpacing: 10,
  timerCardPadding: 13,
  timerClockSpacing: 9,
  timerProgressSpacing: 10,
  timerProgressHeight: 5,
  timerExtensionSpacing: 8,
  timerExtensionTopSpacing: 12,

  displayFontSize: 42,
  displayLineHeight: 40,
  displayLetterSpacing: -1.2,
  titleFontSize: 26,
  titleLineHeight: 32,
  titleLetterSpacing: -0.4,
  recipeTitleFontSize: 20,
  recipeTitleLineHeight: 22,
  bodyFontSize: 15,
  bodyLineHeight: 21,
  labelFontSize: 11,
  labelLineHeight: 14,
  labelLetterSpacing: 1.2,
  noteFontSize: 13,
  noteLineHeight: 17,
  artworkLabelFontSize: 10,
  stepNumberFontSize: 58,
  stepNumberLineHeight: 58,
  stepInstructionFontSize: 30,
  stepInstructionLineHeight: 36,
  completionFontSize: 50,
  completionLineHeight: 48,
  timerEmptyFontSize: 13,
  timerClockFontSize: 38,
  timerClockLineHeight: 38,
};

export type CookVrxTypography = Readonly<{
  display: TextStyle;
  title: TextStyle;
  recipeTitle: TextStyle;
  body: TextStyle;
  bodyStrong: TextStyle;
  label: TextStyle;
  note: TextStyle;
}>;

function createTypography(dimensions: CookbookDimensions): CookVrxTypography {
  const body: TextStyle = {
    fontFamily: 'sans-serif',
    fontSize: dimensions.bodyFontSize,
    lineHeight: dimensions.bodyLineHeight,
  };
  return {
    display: {
      fontFamily: 'serif',
      fontWeight: '900',
      fontSize: dimensions.displayFontSize,
      lineHeight: dimensions.displayLineHeight,
      letterSpacing: dimensions.displayLetterSpacing,
    },
    title: {
      fontFamily: 'serif',
      fontWeight: 'bold',
      fontSize: dimensions.titleFontSize,
      lineHeight: dimensions.titleLineHeight,
      letterSpacing: dimensions.titleLetterSpacing,
    },
    recipeTitle: {
      fontFamily: 'serif',
      fontWeight: 'bold',
      fontSize: dimensions.recipeTitleFontSize,
      lineHeight: dimensions.recipeTitleLineHeight,
    },
    body,
    bodyStrong: {...body, fontWeight: 'bold'},
    label: {
      fontFamily: 'monospace',
      fontWeight: 'bold',
      fontSize: dimensions.labelFontSize,
      lineHeight: dimensions.labelLineHeight,
      letterSpacing: dimensions.labelLetterSpacing,
    },
    note: {
      fontFamily: 'serif',
      fontStyle: 'italic',
      fontSize: dimensions.noteFontSize,
      lineHeight: dimensions.noteLineHeight,
    },
  };
}

export type CookVrxTheme = Readonly<{
  colors: CookVrxColors;
  dimensions: CookbookDimensions;
  text: CookVrxTypography;
}>;

const defaultTheme: CookVrxTheme = {
  colors: fieldNoteColors,
  dimensions: defaultCookbookDimensions,
  text: createTypography(defaultCookbookDimensions),
};

const CookVrxThemeContext = createContext<CookVrxTheme>(defaultTheme);

/**
 * Theme host. `SpatialWindow` children live in the same React tree as the main
 * panel, so context flows into promoted windows exactly as it does inline — the
 * React equivalent of the Compose sample's `SpatialScene(theme = ...)` propagation.
 */
export function CookVrxThemeProvider({
  dimensions = defaultCookbookDimensions,
  children,
}: {
  dimensions?: CookbookDimensions;
  children: React.ReactNode;
}): React.JSX.Element {
  const theme = useMemo<CookVrxTheme>(
    () => ({
      colors: fieldNoteColors,
      dimensions,
      text: createTypography(dimensions),
    }),
    [dimensions],
  );
  return (
    <CookVrxThemeContext.Provider value={theme}>
      {children}
    </CookVrxThemeContext.Provider>
  );
}

export function useCookVrxTheme(): CookVrxTheme {
  return useContext(CookVrxThemeContext);
}
