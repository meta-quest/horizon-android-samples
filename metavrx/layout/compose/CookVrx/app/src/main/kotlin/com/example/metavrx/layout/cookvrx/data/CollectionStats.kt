/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.data

import androidx.compose.runtime.Immutable

@Immutable
data class CollectionStats(
    val quickest: Recipe,
    val longest: Recipe,
    val averageMinutes: Int,
    val byCategory: CountGroup,
    val byDifficulty: CountGroup,
    val staples: CountGroup,
) {
  companion object {
    fun from(recipes: List<Recipe>): CollectionStats? {
      if (recipes.isEmpty()) return null
      return CollectionStats(
          quickest = recipes.minBy(Recipe::totalMinutes),
          longest = recipes.maxBy(Recipe::totalMinutes),
          averageMinutes = recipes.sumOf(Recipe::totalMinutes) / recipes.size,
          byCategory = recipes.countBy(Recipe::category),
          byDifficulty = recipes.countBy(Recipe::difficulty),
          staples =
              CountGroup(
                  counts =
                      PantryStaples.map { (staple, pattern) ->
                            staple to
                                recipes.count { recipe ->
                                  recipe.ingredients.any { pattern in it.text }
                                }
                          }
                          .filter { (_, count) -> count > 1 }
                          .sortedByDescending { (_, count) -> count }
                          .take(MaxStaples),
                  total = recipes.size,
              ),
      )
    }
  }
}

@Immutable data class CountGroup(val counts: List<Pair<String, Int>>, val total: Int)

private fun List<Recipe>.countBy(key: (Recipe) -> String): CountGroup = CountGroup(
    counts = groupingBy(key).eachCount().toList().sortedByDescending { (_, count) -> count },
    total = size,
)

private val PantryStaples =
    listOf("Egg", "Garlic", "Olive oil", "Lemon", "Sesame", "Soy sauce").map { staple ->
      staple to Regex("\\b${Regex.escape(staple)}s?\\b", RegexOption.IGNORE_CASE)
    }
private const val MaxStaples = 4
