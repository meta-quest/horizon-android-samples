/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {Recipe} from '../data/types';
import type {StyleProp, ViewStyle} from 'react-native';

import {useCookVrxTheme, withAlpha} from '../theme/theme';
import {
  Card,
  CloseButton,
  DishArtwork,
  Eyebrow,
  PrimaryButton,
  Rule,
} from './primitives';
import React from 'react';
import {ScrollView, StyleSheet, Text, View} from 'react-native';

export function RecipeDetail({
  recipe,
  onClose,
  onStartCooking,
  style,
}: {
  recipe: Recipe;
  onClose: () => void;
  onStartCooking: () => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <ScrollView
      style={[styles.root, {backgroundColor: colors.cream}, style]}
      contentContainerStyle={{padding: dimensions.detailContentPadding}}>
      <View style={styles.headerRow}>
        <Eyebrow text={`Recipe card · ${recipe.category}`} color={colors.tomato} />
        <CloseButton onPress={onClose} accessibilityLabel="Close recipe" />
      </View>
      <View style={{height: dimensions.detailArtworkTopSpacing}} />
      <DishArtwork recipeId={recipe.id} height={dimensions.detailArtworkHeight} />
      <View style={{height: dimensions.detailTitleTopSpacing}} />
      <Text style={[text.title, {color: colors.ink}]}>{recipe.title}</Text>
      <View style={{height: dimensions.detailFactsTopSpacing}} />
      <RecipeFacts recipe={recipe} />
      <View style={{height: dimensions.detailActionTopSpacing}} />
      <PrimaryButton label="Start cooking →" onPress={onStartCooking} />
      <View style={{height: dimensions.detailSectionTopSpacing}} />
      <View style={styles.sectionHeader}>
        <Eyebrow text="Ingredients" color={colors.ink} />
        <View style={{width: dimensions.detailSectionRuleSpacing}} />
        <Rule
          flex
          height={dimensions.detailSectionRuleHeight}
          color={withAlpha(colors.ink, 0.22)}
        />
      </View>
      <View style={{height: dimensions.detailIngredientsTopSpacing}} />
      {recipe.ingredients.map((ingredient, index) => (
        <View
          key={`${recipe.id}-ingredient-${index}`}
          style={[
            styles.ingredientRow,
            {paddingVertical: dimensions.detailIngredientVerticalPadding},
          ]}>
          <View
            style={[
              styles.ingredientIndex,
              {
                width: dimensions.detailIngredientIndexSize,
                height: dimensions.detailIngredientIndexSize,
                borderRadius: dimensions.detailIngredientIndexCornerRadius,
                backgroundColor: index % 2 === 0 ? colors.citrus : colors.paper,
              },
            ]}>
            <Eyebrow text={`${index + 1}`} color={colors.ink} />
          </View>
          <View style={{width: dimensions.detailIngredientSpacing}} />
          <View style={styles.ingredientText}>
            <Text style={[text.body, {color: colors.ink}]}>{ingredient.text}</Text>
            {ingredient.tip != null && (
              <Text style={[text.note, {color: colors.mutedInk}]}>
                {ingredient.tip}
              </Text>
            )}
          </View>
        </View>
      ))}
      <View style={{height: dimensions.detailFooterTopSpacing}} />
      <Card variant="outlined" contentPadding={dimensions.detailFooterPadding}>
        <Text style={[text.note, {color: colors.ink}]}>
          Set everything out before the pan gets hot. Future you will be grateful.
        </Text>
      </Card>
    </ScrollView>
  );
}

function RecipeFacts({recipe}: {recipe: Recipe}): React.JSX.Element {
  return (
    <Card variant="outlined" contentPadding={0}>
      <View style={styles.factsRow}>
        <Fact label="Time" value={`${recipe.totalMinutes}m`} />
        <Fact label="Feeds" value={`${recipe.servings}`} />
        <Fact label="Level" value={recipe.difficulty} />
      </View>
    </Card>
  );
}

function Fact({label, value}: {label: string; value: string}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View
      style={[
        styles.fact,
        {
          paddingHorizontal: dimensions.detailFactHorizontalPadding,
          paddingVertical: dimensions.detailFactVerticalPadding,
        },
      ]}>
      <Eyebrow text={label} />
      <Text style={[text.bodyStrong, {color: colors.ink}]}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  sectionHeader: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  ingredientRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
  },
  ingredientIndex: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  ingredientText: {
    flex: 1,
  },
  factsRow: {
    flexDirection: 'row',
  },
  fact: {
    flex: 1,
    alignItems: 'center',
  },
});
