/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.data

import android.content.res.AssetManager
import org.json.JSONObject

class RecipeRepository(private val assets: AssetManager) {
  fun loadRecipes(): List<Recipe> =
      assets
          .list(RECIPES_DIRECTORY)
          .orEmpty()
          .filter { it.endsWith(".json") }
          .sorted()
          .mapNotNull { fileName ->
            runCatching {
              assets.open("$RECIPES_DIRECTORY/$fileName").bufferedReader().use { reader ->
                parseRecipe(JSONObject(reader.readText()))
              }
            }
                .getOrNull()
          }

  private fun parseRecipe(json: JSONObject): Recipe = Recipe(
      id = json.getString("id"),
      title = json.getString("title"),
      category = json.getString("category"),
      image = json.getString("image"),
      servings = json.getInt("servings"),
      totalMinutes = json.getInt("totalMinutes"),
      difficulty = json.getString("difficulty"),
      ingredients =
          json.getJSONArray("ingredients").let { ingredients ->
            List(ingredients.length()) { index ->
              ingredients.getJSONObject(index).let { ingredient ->
                Ingredient(
                    text = ingredient.getString("text"),
                    tip = ingredient.optString("tip").takeIf(String::isNotBlank),
                )
              }
            }
          },
      steps =
          json.getJSONArray("steps").let { steps ->
            List(steps.length()) { index ->
              steps.getJSONObject(index).let { step ->
                RecipeStep(
                    text = step.getString("text"),
                    timerSeconds = step.optInt("timerSeconds"),
                )
              }
            }
          },
  )

  private companion object {
    const val RECIPES_DIRECTORY = "recipes"
  }
}
