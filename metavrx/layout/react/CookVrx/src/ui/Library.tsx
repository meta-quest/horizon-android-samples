/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {Recipe} from '../data/types';
import type {CookbookDimensions} from '../theme/theme';
import type {LayoutChangeEvent, StyleProp, ViewStyle} from 'react-native';

import {useCookVrxTheme, withAlpha} from '../theme/theme';
import {
  BorderedButton,
  Card,
  DishArtwork,
  Eyebrow,
  PrimaryButton,
  Rule,
} from './primitives';
import React, {useState} from 'react';
import {FlatList, StyleSheet, Text, View} from 'react-native';

/**
 * Recipe columns that fit in `width`, capped at the profile's column count. `width` of 0 means the
 * list has not been measured yet, so the profile count stands in until the first layout.
 */
function columnsForWidth(width: number, dimensions: CookbookDimensions): number {
  if (width === 0) {
    return dimensions.libraryColumnCount;
  }
  const available =
    width - dimensions.libraryContentPadding * 2 + dimensions.libraryHorizontalSpacing;
  const perColumn = dimensions.libraryMinCardWidth + dimensions.libraryHorizontalSpacing;
  return Math.max(1, Math.min(dimensions.libraryColumnCount, Math.floor(available / perColumn)));
}

export function LibraryScreen({
  recipes,
  categories,
  selectedCategory,
  selectedRecipeId,
  onCategorySelected,
  onRecipeSelected,
  style,
}: {
  recipes: ReadonlyArray<Recipe>;
  categories: ReadonlyArray<string>;
  selectedCategory: string;
  selectedRecipeId: string | null;
  onCategorySelected: (category: string) => void;
  onRecipeSelected: (recipe: Recipe) => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions} = useCookVrxTheme();
  // Measured rather than taken from the profile: the library shares the main panel with whichever
  // spatial windows are declared, and its width jumps whenever one of them promotes out of the
  // inline flow (or falls back into it). Reading the profile alone leaves the grid stuck at the
  // headset column count while the cards squash.
  const [listWidth, setListWidth] = useState(0);
  const columns = columnsForWidth(listWidth, dimensions);
  const onLayout = (event: LayoutChangeEvent) =>
    setListWidth(event.nativeEvent.layout.width);

  return (
    <FlatList
      // `numColumns` cannot change on a mounted list; re-key on the resolved count so a width
      // change (spatial window promoted / demoted, or a wider headset profile) rebuilds the grid.
      key={`library-${columns}`}
      onLayout={onLayout}
      data={recipes}
      keyExtractor={recipe => recipe.id}
      numColumns={columns}
      style={[styles.list, {backgroundColor: colors.paper}, style]}
      contentContainerStyle={{
        padding: dimensions.libraryContentPadding,
        gap: dimensions.libraryVerticalSpacing,
      }}
      // `columnWrapperStyle` is only legal for a multi-column list.
      columnWrapperStyle={
        columns > 1 ? {gap: dimensions.libraryHorizontalSpacing} : undefined
      }
      ListHeaderComponent={
        <View style={{gap: dimensions.libraryVerticalSpacing}}>
          <LibraryHeader recipeCount={recipes.length} />
          <CategoryStrip
            categories={categories}
            selectedCategory={selectedCategory}
            onCategorySelected={onCategorySelected}
          />
        </View>
      }
      ListFooterComponent={
        <View
          style={[
            styles.footer,
            {
              paddingTop: dimensions.libraryFooterTopPadding,
              paddingBottom: dimensions.libraryFooterBottomPadding,
            },
          ]}>
          <Eyebrow text="Field edition · 01" />
          <Eyebrow text="Cook with your whole attention" />
        </View>
      }
      renderItem={({item}) => (
        <RecipeCard
          recipe={item}
          isSelected={item.id === selectedRecipeId}
          onPress={() => onRecipeSelected(item)}
        />
      )}
    />
  );
}

function LibraryHeader({recipeCount}: {recipeCount: number}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View>
      <View style={styles.headerRow}>
        <View style={styles.headerBrand}>
          <Rule
            width={dimensions.libraryHeaderRuleWidth}
            height={dimensions.libraryHeaderRuleHeight}
            color={colors.tomato}
          />
          <View style={{width: dimensions.libraryHeaderRuleSpacing}} />
          <Eyebrow text="CookVrx · A field cookbook" color={colors.ink} />
        </View>
        <View
          style={[
            styles.recipeCountPill,
            {
              backgroundColor: colors.ink,
              paddingHorizontal: dimensions.libraryRecipeCountHorizontalPadding,
              paddingVertical: dimensions.libraryRecipeCountVerticalPadding,
            },
          ]}>
          <Eyebrow text={`${recipeCount} recipes`} color={colors.cream} />
        </View>
      </View>
      <View style={{height: dimensions.libraryTitleTopSpacing}} />
      <Text style={[text.display, {color: colors.ink}]}>
        {'COOK SOMETHING\nWORTH PAUSING FOR.'}
      </Text>
      <View style={{height: dimensions.libraryDescriptionTopSpacing}} />
      <Text style={[text.note, {color: colors.mutedInk}]}>
        Small rituals, hot pans, and useful timers for whatever tonight becomes.
      </Text>
      <View style={{height: dimensions.libraryDividerTopSpacing}} />
      <View
        style={{
          height: dimensions.libraryDividerHeight,
          backgroundColor: withAlpha(colors.ink, 0.24),
        }}
      />
    </View>
  );
}

function CategoryStrip({
  categories,
  selectedCategory,
  onCategorySelected,
}: {
  categories: ReadonlyArray<string>;
  selectedCategory: string;
  onCategorySelected: (category: string) => void;
}): React.JSX.Element {
  const {colors, dimensions} = useCookVrxTheme();
  return (
    <View
      style={[
        styles.categoryStrip,
        {
          paddingVertical: dimensions.categoryVerticalPadding,
          gap: dimensions.categorySpacing,
        },
      ]}>
      {categories.map(category =>
        category === selectedCategory ? (
          <PrimaryButton
            key={category}
            label={category}
            onPress={() => onCategorySelected(category)}
          />
        ) : (
          <BorderedButton
            key={category}
            label={category}
            onPress={() => onCategorySelected(category)}
            borderColor={colors.ink}
          />
        ),
      )}
    </View>
  );
}

function RecipeCard({
  recipe,
  isSelected,
  onPress,
}: {
  recipe: Recipe;
  isSelected: boolean;
  onPress: () => void;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View style={styles.cardSlot}>
      <Card
        variant="secondary"
        onPress={onPress}
        contentPadding={dimensions.recipeCardPadding}
        // The border is always present and only its colour changes. Adding and removing
        // `borderWidth` blanks the card on deselection: the card also has `borderRadius` +
        // `overflow: 'hidden'`, so its children are clipped to a rounded-rect path that the
        // background drawable ignores — the frame keeps painting while every child disappears
        // until the next selection recomputes the path. A constant width also keeps the content
        // box from reflowing each time selection moves.
        style={{
          borderWidth: dimensions.recipeCardSelectedBorderWidth,
          borderColor: isSelected ? colors.tomato : 'transparent',
        }}>
        <DishArtwork
          recipeId={recipe.id}
          height={dimensions.recipeCardArtworkHeight}
        />
        <View style={{height: dimensions.recipeCardArtworkSpacing}} />
        <View style={styles.cardMetaRow}>
          <Eyebrow text={recipe.category} color={colors.tomato} />
          <Eyebrow text={`${recipe.totalMinutes} min`} />
        </View>
        <View style={{height: dimensions.recipeCardMetaSpacing}} />
        <Text
          numberOfLines={2}
          ellipsizeMode="tail"
          style={[text.recipeTitle, {color: colors.ink}]}>
          {recipe.title}
        </Text>
        <View style={{height: dimensions.recipeCardFooterSpacing}} />
        <View style={styles.cardFooterRow}>
          <Rule
            width={dimensions.recipeCardFooterRuleWidth}
            height={dimensions.recipeCardFooterRuleHeight}
            color={colors.citrus}
          />
          <View style={{width: dimensions.recipeCardFooterRuleSpacing}} />
          <Text style={[text.note, {color: colors.mutedInk}]}>
            {`${recipe.difficulty} · serves ${recipe.servings}`}
          </Text>
        </View>
      </Card>
    </View>
  );
}

const styles = StyleSheet.create({
  list: {
    flex: 1,
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  headerBrand: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  recipeCountPill: {
    borderRadius: 999,
  },
  categoryStrip: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  cardSlot: {
    flex: 1,
  },
  cardMetaRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  cardFooterRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
});
