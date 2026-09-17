/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.layout.cookvrx.state

import android.app.Application
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.metavrx.layout.cookvrx.data.Recipe
import com.example.metavrx.layout.cookvrx.data.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class CookVrxMode {
  Browse,
  Cook,
}

enum class MobileCookTool {
  Ingredients,
  Timers,
}

data class KitchenTimer(
    val id: Long,
    val label: String,
    val durationSeconds: Int,
    val endsAtElapsedRealtime: Long,
)

class CookVrxViewModel(application: Application) : AndroidViewModel(application) {
  var recipes by mutableStateOf<List<Recipe>>(emptyList())
    private set

  val categories: List<String>
    get() = listOf("All") + recipes.map(Recipe::category).distinct()

  var mode by mutableStateOf(CookVrxMode.Browse)
    private set

  var selectedRecipeId by mutableStateOf<String?>(null)
    private set

  var selectedCategory by mutableStateOf("All")
    private set

  var stepIndex by mutableIntStateOf(0)
    private set

  var isComplete by mutableStateOf(false)
    private set

  var mobileCookTool by mutableStateOf(MobileCookTool.Ingredients)
    private set

  val checkedIngredients = mutableStateMapOf<Int, Boolean>()
  val timers = mutableStateListOf<KitchenTimer>()
  private var nextTimerId = 0L

  init {
    viewModelScope.launch {
      recipes = withContext(Dispatchers.IO) { RecipeRepository(application.assets).loadRecipes() }
    }
  }

  val selectedRecipe: Recipe?
    get() = recipes.firstOrNull { it.id == selectedRecipeId }

  val filteredRecipes: List<Recipe>
    get() =
        if (selectedCategory == "All") recipes
        else recipes.filter { it.category == selectedCategory }

  fun selectCategory(category: String) {
    selectedCategory = category
  }

  fun selectRecipe(recipe: Recipe) {
    selectedRecipeId = recipe.id
  }

  fun closeRecipe() {
    selectedRecipeId = null
  }

  fun startCooking() {
    if (selectedRecipe == null) return
    mode = CookVrxMode.Cook
    stepIndex = 0
    isComplete = false
    checkedIngredients.clear()
    timers.clear()
  }

  fun nextStep() {
    val lastIndex = selectedRecipe?.steps?.lastIndex ?: return
    if (stepIndex == lastIndex) isComplete = true else stepIndex += 1
  }

  fun previousStep() {
    if (isComplete) {
      isComplete = false
    } else if (stepIndex > 0) {
      stepIndex -= 1
    }
  }

  fun toggleIngredient(index: Int) {
    checkedIngredients[index] = checkedIngredients[index] != true
  }

  fun showMobileTool(tool: MobileCookTool) {
    mobileCookTool = tool
  }

  fun startStepTimer() {
    val recipe = selectedRecipe ?: return
    val step = recipe.steps[stepIndex]
    if (step.timerSeconds <= 0) return
    addTimer(label = "Step ${stepIndex + 1} · ${recipe.title}", seconds = step.timerSeconds)
  }

  fun addQuickTimer(seconds: Int) {
    val label = if (seconds < 60) "Quick check" else "Kitchen check"
    addTimer(label = label, seconds = seconds)
  }

  fun extendTimer(id: Long, seconds: Int) {
    val index = timers.indexOfFirst { it.id == id }
    if (index < 0) return
    val timer = timers[index]
    val now = SystemClock.elapsedRealtime()
    timers[index] =
        timer.copy(
            durationSeconds = timer.durationSeconds + seconds,
            endsAtElapsedRealtime = maxOf(timer.endsAtElapsedRealtime, now) + seconds * 1_000L,
        )
  }

  fun removeTimer(id: Long) {
    timers.removeAll { it.id == id }
  }

  fun returnToLibrary() {
    mode = CookVrxMode.Browse
    selectedRecipeId = null
    stepIndex = 0
    isComplete = false
    timers.clear()
    checkedIngredients.clear()
  }

  private fun addTimer(label: String, seconds: Int) {
    val now = SystemClock.elapsedRealtime()
    timers +=
        KitchenTimer(
            id = nextTimerId++,
            label = label,
            durationSeconds = seconds,
            endsAtElapsedRealtime = now + seconds * 1_000L,
        )
  }
}
