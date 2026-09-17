/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.data

import androidx.compose.runtime.Immutable

@Immutable
data class Recipe(
    val id: String,
    val title: String,
    val category: String,
    val image: String,
    val servings: Int,
    val totalMinutes: Int,
    val difficulty: String,
    val ingredients: List<Ingredient>,
    val steps: List<RecipeStep>,
)

@Immutable data class Ingredient(val text: String, val tip: String?)

@Immutable data class RecipeStep(val text: String, val timerSeconds: Int)
