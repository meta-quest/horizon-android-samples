/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {Recipe} from './types';

// Metro has no runtime directory listing, so the recipes are bundled as static
// imports rather than enumerated from a directory at run time.
import blackSesamePancakes from '../../assets/recipes/black_sesame_pancakes.json';
import charredLemonChicken from '../../assets/recipes/charred_lemon_chicken.json';
import citrusOliveCake from '../../assets/recipes/citrus_olive_cake.json';
import crispyMushroomRice from '../../assets/recipes/crispy_mushroom_rice.json';
import greenGoddessBeans from '../../assets/recipes/green_goddess_beans.json';
import marketTomatoToast from '../../assets/recipes/market_tomato_toast.json';
import midnightMisoNoodles from '../../assets/recipes/midnight_miso_noodles.json';
import smokyShakshuka from '../../assets/recipes/smoky_shakshuka.json';

// Ordered by file name, matching the repository's `.sorted()` asset listing.
export const RECIPES: ReadonlyArray<Recipe> = [
  blackSesamePancakes,
  charredLemonChicken,
  citrusOliveCake,
  crispyMushroomRice,
  greenGoddessBeans,
  marketTomatoToast,
  midnightMisoNoodles,
  smokyShakshuka,
];

export const ALL_CATEGORIES = 'All';

export function categoriesOf(recipes: ReadonlyArray<Recipe>): ReadonlyArray<string> {
  return [ALL_CATEGORIES, ...new Set(recipes.map(recipe => recipe.category))];
}
