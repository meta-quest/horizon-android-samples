/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

export type Ingredient = Readonly<{
  text: string;
  tip?: string;
}>;

export type RecipeStep = Readonly<{
  text: string;
  timerSeconds: number;
}>;

export type Recipe = Readonly<{
  id: string;
  title: string;
  category: string;
  image: string;
  servings: number;
  totalMinutes: number;
  difficulty: string;
  ingredients: ReadonlyArray<Ingredient>;
  steps: ReadonlyArray<RecipeStep>;
}>;
