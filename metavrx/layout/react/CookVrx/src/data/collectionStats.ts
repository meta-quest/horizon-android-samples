/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {Recipe} from './types';

export type CountGroup = Readonly<{
  counts: ReadonlyArray<Readonly<{label: string; count: number}>>;
  total: number;
}>;

export type CollectionStats = Readonly<{
  quickest: Recipe;
  longest: Recipe;
  averageMinutes: number;
  byCategory: CountGroup;
  byDifficulty: CountGroup;
  staples: CountGroup;
}>;

export function computeCollectionStats(
  recipes: ReadonlyArray<Recipe>,
): CollectionStats | null {
  if (recipes.length === 0) {
    return null;
  }
  const byMinutes = [...recipes].sort((a, b) => a.totalMinutes - b.totalMinutes);
  const totalMinutes = recipes.reduce((sum, recipe) => sum + recipe.totalMinutes, 0);
  return {
    quickest: byMinutes[0],
    longest: byMinutes[byMinutes.length - 1],
    averageMinutes: Math.floor(totalMinutes / recipes.length),
    byCategory: countBy(recipes, recipe => recipe.category),
    byDifficulty: countBy(recipes, recipe => recipe.difficulty),
    staples: {
      counts: PANTRY_STAPLES.map(({label, pattern}) => ({
        label,
        count: recipes.filter(recipe =>
          recipe.ingredients.some(ingredient => pattern.test(ingredient.text)),
        ).length,
      }))
        .filter(({count}) => count > 1)
        .sort((a, b) => b.count - a.count)
        .slice(0, MAX_STAPLES),
      total: recipes.length,
    },
  };
}

function countBy(
  recipes: ReadonlyArray<Recipe>,
  key: (recipe: Recipe) => string,
): CountGroup {
  const counts = new Map<string, number>();
  for (const recipe of recipes) {
    counts.set(key(recipe), (counts.get(key(recipe)) ?? 0) + 1);
  }
  return {
    counts: [...counts.entries()]
      .map(([label, count]) => ({label, count}))
      .sort((a, b) => b.count - a.count),
    total: recipes.length,
  };
}

const PANTRY_STAPLES: ReadonlyArray<Readonly<{label: string; pattern: RegExp}>> = [
  'Egg',
  'Garlic',
  'Olive oil',
  'Lemon',
  'Sesame',
  'Soy sauce',
].map(label => ({label, pattern: new RegExp(`\\b${label}s?\\b`, 'i')}));

const MAX_STAPLES = 4;
