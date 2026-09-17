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
import {Eyebrow, Rule} from './primitives';
import React from 'react';
import {Pressable, ScrollView, StyleSheet, Text, View} from 'react-native';

export function IngredientsPanel({
  recipe,
  checkedIngredients,
  onIngredientChecked,
  style,
}: {
  recipe: Recipe;
  checkedIngredients: ReadonlySet<number>;
  onIngredientChecked: (index: number) => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <ScrollView
      style={[styles.root, {backgroundColor: colors.paper}, style]}
      contentContainerStyle={{padding: dimensions.ingredientsContentPadding}}>
      <Eyebrow text="Station 01 · Ingredients" color={colors.tomato} />
      <View style={{height: dimensions.ingredientsTitleSpacing}} />
      <Text style={[text.title, {color: colors.ink}]}>Ingredients</Text>
      <View style={{height: dimensions.ingredientsSubtitleSpacing}} />
      <Text style={[text.note, {color: colors.mutedInk}]}>
        Tap as each ingredient hits the pan.
      </Text>
      <View style={{height: dimensions.ingredientsListSpacing}} />
      {recipe.ingredients.map((ingredient, index) => {
        const checked = checkedIngredients.has(index);
        return (
          <View key={`${recipe.id}-ingredient-${index}`}>
            <Pressable
              onPress={() => onIngredientChecked(index)}
              accessibilityRole="checkbox"
              accessibilityState={{checked}}
              style={[
                styles.row,
                {
                  paddingVertical: dimensions.ingredientRowVerticalPadding,
                  gap: dimensions.ingredientRowSpacing,
                },
              ]}>
              <View
                style={[
                  styles.checkbox,
                  {
                    width: dimensions.ingredientCheckboxSize,
                    height: dimensions.ingredientCheckboxSize,
                    borderRadius: dimensions.ingredientCheckboxSize / 2,
                    borderWidth: dimensions.ingredientCheckboxBorderWidth,
                    borderColor: colors.ink,
                    backgroundColor: checked ? colors.leaf : 'transparent',
                  },
                ]}>
                {checked && (
                  <Text style={[text.bodyStrong, {color: colors.cream}]}>✓</Text>
                )}
              </View>
              <View style={[styles.body, checked && styles.checkedBody]}>
                <Text
                  style={[
                    text.body,
                    {color: colors.ink},
                    checked && styles.struckThrough,
                  ]}>
                  {ingredient.text}
                </Text>
                {ingredient.tip != null && (
                  <>
                    <View style={{height: dimensions.ingredientTipSpacing}} />
                    <View style={styles.tipRow}>
                      <Rule
                        width={dimensions.ingredientTipRuleWidth}
                        height={dimensions.ingredientTipRuleHeight}
                        color={colors.citrus}
                      />
                      <View style={{width: dimensions.ingredientTipRuleSpacing}} />
                      <Text style={[text.note, {color: colors.mutedInk}]}>
                        {ingredient.tip}
                      </Text>
                    </View>
                  </>
                )}
              </View>
            </Pressable>
            <View
              style={{
                height: dimensions.ingredientDividerHeight,
                backgroundColor: withAlpha(colors.ink, 0.12),
              }}
            />
          </View>
        );
      })}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  row: {
    flexDirection: 'row',
    alignItems: 'flex-start',
  },
  checkbox: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  body: {
    flex: 1,
  },
  checkedBody: {
    opacity: 0.55,
  },
  struckThrough: {
    textDecorationLine: 'line-through',
  },
  tipRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
});
