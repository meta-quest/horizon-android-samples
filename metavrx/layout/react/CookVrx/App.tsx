/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {CookVrxStore, MobileCookTool} from './src/state/useCookVrx';
import type {Recipe} from './src/data/types';
import type {OffsetStepValue} from '@metavr/layout-window-compat';

import {useCookVrx} from './src/state/useCookVrx';
import {CookVrxThemeProvider, useCookVrxTheme} from './src/theme/theme';
import {CookStep} from './src/ui/CookStep';
import {IngredientsPanel} from './src/ui/Ingredients';
import {LibraryScreen} from './src/ui/Library';
import {BorderedButton, PrimaryButton} from './src/ui/primitives';
import {RecipeDetail} from './src/ui/RecipeDetail';
import {TimersPanel} from './src/ui/Timers';
import {
  SpatialSceneProvider,
  useSpatialScene,
} from '@metavr/layout-compat';
import {
  OffsetNear,
  SpatialWindow,
  createWindowScene,
  useSpatialWindowState,
} from '@metavr/layout-window-compat';
import React from 'react';
import {StyleSheet, View} from 'react-native';

// Negating an offset constant (`-OffsetNear`) widens it back to `number`, which
// no longer satisfies the -5..5 `OffsetStepValue` union, so the end-anchored
// windows pin the literal instead. `+start` is toward layout-start, so a
// negative step nudges these windows toward the layout-end edge.
const OffsetNearTowardEnd: OffsetStepValue = -1;

// Switching browse -> cook demotes `detail` and promotes `ingredients` + `timers`
// in the same frame. Horizon OS caps an app at three layers and does not free the
// demoted one synchronously, so the second promotion is rejected with "Placement
// rejected. Maxiumum number of layers allowed is 3". The SDK retries, but its
// default holdoff is 0ms — every attempt lands inside the same frame, before the
// layer is released, and the window is abandoned inline. A holdoff spaces the
// retries past the release.
const PROMOTION_RETRY = {attempts: 5, holdoffMs: 250};

export default function App(): React.JSX.Element {
  return (
    <SpatialSceneProvider
      initializer={createWindowScene({retryConfig: PROMOTION_RETRY})}>
      <CookVrxThemeProvider>
        <SpatialAppRoot />
      </CookVrxThemeProvider>
    </SpatialSceneProvider>
  );
}

/**
 * The spatial flow. On a device with no spatial windowing the whole app collapses
 * to the compact single-surface flow; otherwise browse and cook mode each declare
 * their own set of child windows. At most two children
 * are declared at a time, and every one of them uses the default
 * `fallback="inline"` so an unsupported Horizon OS build still renders the
 * complete flow in one surface.
 */
function SpatialAppRoot(): React.JSX.Element {
  const {isSpatialAvailable} = useSpatialScene();
  const store = useCookVrx();

  if (!isSpatialAvailable) {
    return <MobileAppRoot store={store} />;
  }
  return store.mode === 'browse' ? (
    <SpatialBrowseLayout store={store} />
  ) : (
    <SpatialCookLayout store={store} />
  );
}

function SpatialBrowseLayout({
  store,
}: {
  store: CookVrxStore;
}): React.JSX.Element {
  return (
    <View style={styles.row}>
      <Library store={store} />
      {store.selectedRecipe != null && (
        <DetailWindow store={store} recipe={store.selectedRecipe} />
      )}
    </View>
  );
}

function DetailWindow({
  store,
  recipe,
}: {
  store: CookVrxStore;
  recipe: Recipe;
}): React.JSX.Element {
  const {dimensions} = useCookVrxTheme();
  const {placement} = useSpatialWindowState('detail');
  return (
    <SpatialWindow
      label="detail"
      windowWidth={dimensions.detailWindowWidth}
      windowHeight={dimensions.detailWindowHeight}
      anchor="end"
      offset={{start: OffsetNearTowardEnd, z: OffsetNear}}>
      <RecipeDetail
        recipe={recipe}
        onClose={store.closeRecipe}
        onStartCooking={store.startCooking}
        style={
          placement === 'spatial'
            ? styles.fill
            : {width: dimensions.detailWindowWidth}
        }
      />
    </SpatialWindow>
  );
}

function SpatialCookLayout({store}: {store: CookVrxStore}): React.JSX.Element {
  const recipe = store.selectedRecipe;
  if (recipe == null) {
    return <View style={styles.fill} />;
  }
  return (
    <View style={styles.row}>
      <IngredientsWindow store={store} recipe={recipe} />
      <CurrentStep store={store} recipe={recipe} />
      <TimersWindow store={store} />
    </View>
  );
}

function IngredientsWindow({
  store,
  recipe,
}: {
  store: CookVrxStore;
  recipe: Recipe;
}): React.JSX.Element {
  const {dimensions} = useCookVrxTheme();
  const {placement} = useSpatialWindowState('ingredients');
  return (
    <SpatialWindow
      label="ingredients"
      windowWidth={dimensions.cookToolWindowWidth}
      windowHeight={dimensions.cookToolWindowHeight}
      anchor="start"
      offset={{start: OffsetNear, z: OffsetNear}}>
      <IngredientsPanel
        recipe={recipe}
        checkedIngredients={store.checkedIngredients}
        onIngredientChecked={store.toggleIngredient}
        style={
          placement === 'spatial'
            ? styles.fill
            : {width: dimensions.inlineCookToolWidth}
        }
      />
    </SpatialWindow>
  );
}

function TimersWindow({store}: {store: CookVrxStore}): React.JSX.Element {
  const {dimensions} = useCookVrxTheme();
  const {placement} = useSpatialWindowState('timers');
  return (
    <SpatialWindow
      label="timers"
      windowWidth={dimensions.cookToolWindowWidth}
      windowHeight={dimensions.cookToolWindowHeight}
      anchor="end"
      offset={{start: OffsetNearTowardEnd, z: OffsetNear}}>
      <TimersPanel
        timers={store.timers}
        onAddQuickTimer={store.addQuickTimer}
        onExtendTimer={store.extendTimer}
        onRemoveTimer={store.removeTimer}
        style={
          placement === 'spatial'
            ? styles.fill
            : {width: dimensions.inlineCookToolWidth}
        }
      />
    </SpatialWindow>
  );
}

/** The compact single-surface flow, used where spatial windowing is unavailable. */
function MobileAppRoot({store}: {store: CookVrxStore}): React.JSX.Element {
  const {colors, dimensions} = useCookVrxTheme();
  const recipe = store.selectedRecipe;

  if (store.mode === 'browse') {
    return recipe == null ? (
      <Library store={store} />
    ) : (
      <RecipeDetail
        recipe={recipe}
        onClose={store.closeRecipe}
        onStartCooking={store.startCooking}
        style={styles.fill}
      />
    );
  }

  if (recipe == null) {
    return <View style={styles.fill} />;
  }
  return (
    <View style={[styles.fill, {backgroundColor: colors.ink}]}>
      <CurrentStep store={store} recipe={recipe} />
      {!store.isComplete && (
        <>
          <MobileToolPicker
            selected={store.mobileCookTool}
            onSelected={store.showMobileTool}
          />
          <View style={{height: dimensions.mobileCookToolHeight}}>
            {store.mobileCookTool === 'ingredients' ? (
              <IngredientsPanel
                recipe={recipe}
                checkedIngredients={store.checkedIngredients}
                onIngredientChecked={store.toggleIngredient}
              />
            ) : (
              <TimersPanel
                timers={store.timers}
                onAddQuickTimer={store.addQuickTimer}
                onExtendTimer={store.extendTimer}
                onRemoveTimer={store.removeTimer}
              />
            )}
          </View>
        </>
      )}
    </View>
  );
}

function MobileToolPicker({
  selected,
  onSelected,
}: {
  selected: MobileCookTool;
  onSelected: (tool: MobileCookTool) => void;
}): React.JSX.Element {
  const {colors, dimensions} = useCookVrxTheme();
  const tools: ReadonlyArray<{tool: MobileCookTool; label: string}> = [
    {tool: 'ingredients', label: 'Ingredients'},
    {tool: 'timers', label: 'Timers'},
  ];
  return (
    <View
      style={[
        styles.toolPicker,
        {
          backgroundColor: colors.cream,
          paddingHorizontal: dimensions.mobileToolPickerHorizontalPadding,
          paddingVertical: dimensions.mobileToolPickerVerticalPadding,
          gap: dimensions.mobileToolPickerSpacing,
        },
      ]}>
      {tools.map(({tool, label}) =>
        tool === selected ? (
          <PrimaryButton
            key={tool}
            label={label}
            onPress={() => onSelected(tool)}
            style={styles.fill}
          />
        ) : (
          <BorderedButton
            key={tool}
            label={label}
            onPress={() => onSelected(tool)}
            borderColor={colors.ink}
            style={styles.fill}
          />
        ),
      )}
    </View>
  );
}

function Library({store}: {store: CookVrxStore}): React.JSX.Element {
  return (
    <LibraryScreen
      recipes={store.filteredRecipes}
      categories={store.categories}
      selectedCategory={store.selectedCategory}
      selectedRecipeId={store.selectedRecipeId}
      onCategorySelected={store.selectCategory}
      onRecipeSelected={store.selectRecipe}
      style={styles.fill}
    />
  );
}

function CurrentStep({
  store,
  recipe,
}: {
  store: CookVrxStore;
  recipe: Recipe;
}): React.JSX.Element {
  return (
    <CookStep
      recipe={recipe}
      stepIndex={store.stepIndex}
      isComplete={store.isComplete}
      onPrevious={store.previousStep}
      onNext={store.nextStep}
      onStartTimer={store.startStepTimer}
      onReturnToLibrary={store.returnToLibrary}
      style={styles.fill}
    />
  );
}

const styles = StyleSheet.create({
  row: {
    flex: 1,
    flexDirection: 'row',
  },
  fill: {
    flex: 1,
  },
  toolPicker: {
    flexDirection: 'row',
  },
});
