/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {Recipe} from '../data/types';

import {ALL_CATEGORIES, categoriesOf, RECIPES} from '../data/recipes';
import {useCallback, useMemo, useRef, useState} from 'react';

export type CookVrxMode = 'browse' | 'cook';
export type MobileCookTool = 'ingredients' | 'timers';

export type KitchenTimer = Readonly<{
  id: number;
  label: string;
  durationSeconds: number;
  endsAtMs: number;
}>;

export type CookVrxStore = Readonly<{
  recipes: ReadonlyArray<Recipe>;
  categories: ReadonlyArray<string>;
  filteredRecipes: ReadonlyArray<Recipe>;
  selectedRecipe: Recipe | null;
  selectedRecipeId: string | null;
  selectedCategory: string;
  mode: CookVrxMode;
  stepIndex: number;
  isComplete: boolean;
  mobileCookTool: MobileCookTool;
  checkedIngredients: ReadonlySet<number>;
  timers: ReadonlyArray<KitchenTimer>;

  selectCategory: (category: string) => void;
  selectRecipe: (recipe: Recipe) => void;
  closeRecipe: () => void;
  startCooking: () => void;
  nextStep: () => void;
  previousStep: () => void;
  toggleIngredient: (index: number) => void;
  showMobileTool: (tool: MobileCookTool) => void;
  startStepTimer: () => void;
  addQuickTimer: (seconds: number) => void;
  extendTimer: (id: number, seconds: number) => void;
  removeTimer: (id: number) => void;
  returnToLibrary: () => void;
}>;

/**
 * The app's whole store. Held once at the root and read by both the main panel and
 * the `SpatialWindow` children, so step progress, ingredient checks, and timers
 * stay shared across promoted windows.
 */
export function useCookVrx(): CookVrxStore {
  const recipes = RECIPES;
  const [mode, setMode] = useState<CookVrxMode>('browse');
  const [selectedRecipeId, setSelectedRecipeId] = useState<string | null>(null);
  const [selectedCategory, setSelectedCategory] = useState(ALL_CATEGORIES);
  const [stepIndex, setStepIndex] = useState(0);
  const [isComplete, setIsComplete] = useState(false);
  const [mobileCookTool, setMobileCookTool] =
    useState<MobileCookTool>('ingredients');
  const [checkedIngredients, setCheckedIngredients] = useState<
    ReadonlySet<number>
  >(() => new Set());
  const [timers, setTimers] = useState<ReadonlyArray<KitchenTimer>>([]);
  const nextTimerIdRef = useRef(0);

  const categories = useMemo(() => categoriesOf(recipes), [recipes]);

  const selectedRecipe = useMemo(
    () => recipes.find(recipe => recipe.id === selectedRecipeId) ?? null,
    [recipes, selectedRecipeId],
  );

  const filteredRecipes = useMemo(
    () =>
      selectedCategory === ALL_CATEGORIES
        ? recipes
        : recipes.filter(recipe => recipe.category === selectedCategory),
    [recipes, selectedCategory],
  );

  const addTimer = useCallback((label: string, seconds: number) => {
    const id = nextTimerIdRef.current++;
    setTimers(prev => [
      ...prev,
      {id, label, durationSeconds: seconds, endsAtMs: Date.now() + seconds * 1000},
    ]);
  }, []);

  const selectCategory = useCallback((category: string) => {
    setSelectedCategory(category);
  }, []);

  const selectRecipe = useCallback((recipe: Recipe) => {
    setSelectedRecipeId(recipe.id);
  }, []);

  const closeRecipe = useCallback(() => {
    setSelectedRecipeId(null);
  }, []);

  const startCooking = useCallback(() => {
    if (selectedRecipe == null) {
      return;
    }
    setMode('cook');
    setStepIndex(0);
    setIsComplete(false);
    setCheckedIngredients(new Set());
    setTimers([]);
  }, [selectedRecipe]);

  const nextStep = useCallback(() => {
    if (selectedRecipe == null) {
      return;
    }
    const lastIndex = selectedRecipe.steps.length - 1;
    if (stepIndex === lastIndex) {
      setIsComplete(true);
    } else {
      setStepIndex(stepIndex + 1);
    }
  }, [selectedRecipe, stepIndex]);

  const previousStep = useCallback(() => {
    if (isComplete) {
      setIsComplete(false);
    } else {
      setStepIndex(current => Math.max(0, current - 1));
    }
  }, [isComplete]);

  const toggleIngredient = useCallback((index: number) => {
    setCheckedIngredients(prev => {
      const next = new Set(prev);
      if (!next.delete(index)) {
        next.add(index);
      }
      return next;
    });
  }, []);

  const showMobileTool = useCallback((tool: MobileCookTool) => {
    setMobileCookTool(tool);
  }, []);

  const startStepTimer = useCallback(() => {
    if (selectedRecipe == null) {
      return;
    }
    const step = selectedRecipe.steps[stepIndex];
    if (step.timerSeconds <= 0) {
      return;
    }
    addTimer(`Step ${stepIndex + 1} · ${selectedRecipe.title}`, step.timerSeconds);
  }, [addTimer, selectedRecipe, stepIndex]);

  const addQuickTimer = useCallback(
    (seconds: number) => {
      addTimer(seconds < 60 ? 'Quick check' : 'Kitchen check', seconds);
    },
    [addTimer],
  );

  const extendTimer = useCallback((id: number, seconds: number) => {
    const now = Date.now();
    setTimers(prev =>
      prev.map(timer =>
        timer.id === id
          ? {
              ...timer,
              durationSeconds: timer.durationSeconds + seconds,
              endsAtMs: Math.max(timer.endsAtMs, now) + seconds * 1000,
            }
          : timer,
      ),
    );
  }, []);

  const removeTimer = useCallback((id: number) => {
    setTimers(prev => prev.filter(timer => timer.id !== id));
  }, []);

  const returnToLibrary = useCallback(() => {
    setMode('browse');
    setSelectedRecipeId(null);
    setStepIndex(0);
    setIsComplete(false);
    setTimers([]);
    setCheckedIngredients(new Set());
  }, []);

  return {
    recipes,
    categories,
    filteredRecipes,
    selectedRecipe,
    selectedRecipeId,
    selectedCategory,
    mode,
    stepIndex,
    isComplete,
    mobileCookTool,
    checkedIngredients,
    timers,
    selectCategory,
    selectRecipe,
    closeRecipe,
    startCooking,
    nextStep,
    previousStep,
    toggleIngredient,
    showMobileTool,
    startStepTimer,
    addQuickTimer,
    extendTimer,
    removeTimer,
    returnToLibrary,
  };
}
