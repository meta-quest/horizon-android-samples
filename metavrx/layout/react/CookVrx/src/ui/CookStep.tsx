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
import {
  BorderedButton,
  Eyebrow,
  formatClock,
  PrimaryButton,
  Rule,
  SecondaryButton,
} from './primitives';
import React from 'react';
import {StyleSheet, Text, View} from 'react-native';

export function CookStep({
  recipe,
  stepIndex,
  isComplete,
  onPrevious,
  onNext,
  onStartTimer,
  onReturnToLibrary,
  style,
}: {
  recipe: Recipe;
  stepIndex: number;
  isComplete: boolean;
  onPrevious: () => void;
  onNext: () => void;
  onStartTimer: () => void;
  onReturnToLibrary: () => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  const step = recipe.steps[stepIndex];
  const isLastStep = stepIndex === recipe.steps.length - 1;

  return (
    <View
      style={[
        styles.root,
        {
          backgroundColor: colors.ink,
          padding: dimensions.stepContentPadding,
        },
        style,
      ]}>
      <View style={styles.headerRow}>
        <View>
          <Eyebrow text="Now cooking" color={colors.citrus} />
          <Text style={[text.recipeTitle, {color: colors.cream}]}>
            {recipe.title}
          </Text>
        </View>
        <View
          style={[
            styles.badge,
            {backgroundColor: colors.tomato, padding: dimensions.stepBadgePadding},
          ]}>
          <Eyebrow
            text={isComplete ? 'Done' : `${stepIndex + 1}/${recipe.steps.length}`}
            color={colors.cream}
          />
        </View>
      </View>

      {isComplete ? (
        <CompleteState recipe={recipe} />
      ) : (
        <View>
          <View style={styles.stepNumberRow}>
            <Text
              style={[
                text.display,
                {
                  color: colors.tomato,
                  fontSize: dimensions.stepNumberFontSize,
                  lineHeight: dimensions.stepNumberLineHeight,
                },
              ]}>
              {`${stepIndex + 1}`.padStart(2, '0')}
            </Text>
            <View style={{width: dimensions.stepNumberSpacing}} />
            <Rule
              flex
              height={dimensions.stepRuleHeight}
              color={withAlpha(colors.cream, 0.25)}
            />
          </View>
          <View style={{height: dimensions.stepInstructionSpacing}} />
          <Text
            style={[
              text.title,
              {
                color: colors.cream,
                fontSize: dimensions.stepInstructionFontSize,
                lineHeight: dimensions.stepInstructionLineHeight,
              },
            ]}>
            {step.text}
          </Text>
          {step.timerSeconds > 0 && (
            <>
              <View style={{height: dimensions.stepTimerSpacing}} />
              <SecondaryButton
                label={`Start ${formatClock(step.timerSeconds)} timer`}
                onPress={onStartTimer}
                style={styles.stepTimerButton}
              />
            </>
          )}
        </View>
      )}

      {isComplete ? (
        <PrimaryButton
          label="Back to the recipe shelf"
          onPress={onReturnToLibrary}
        />
      ) : (
        <View style={[styles.controls, {gap: dimensions.stepControlSpacing}]}>
          <BorderedButton
            label="← Previous"
            onPress={onPrevious}
            disabled={stepIndex === 0}
            borderColor={colors.cream}
            labelColor={colors.cream}
            style={styles.previousButton}
          />
          <PrimaryButton
            label={isLastStep ? 'Plate it →' : 'Next step →'}
            onPress={onNext}
            style={styles.nextButton}
          />
        </View>
      )}
    </View>
  );
}

function CompleteState({recipe}: {recipe: Recipe}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View
      style={[
        styles.complete,
        {paddingHorizontal: dimensions.completionHorizontalPadding},
      ]}>
      <Text
        style={[
          text.display,
          styles.centered,
          {
            color: colors.citrus,
            fontSize: dimensions.completionFontSize,
            lineHeight: dimensions.completionLineHeight,
          },
        ]}>
        {'DINNER\nIS READY.'}
      </Text>
      <View style={{height: dimensions.completionMessageSpacing}} />
      <Text style={[text.note, styles.centered, {color: colors.cream}]}>
        {`${recipe.title} · serve it while the edges are still singing.`}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
    justifyContent: 'space-between',
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  badge: {
    borderRadius: 999,
    alignItems: 'center',
    justifyContent: 'center',
  },
  stepNumberRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  stepTimerButton: {
    alignSelf: 'flex-start',
  },
  controls: {
    flexDirection: 'row',
  },
  previousButton: {
    flex: 1,
  },
  nextButton: {
    flex: 1.3,
  },
  complete: {
    alignItems: 'center',
  },
  centered: {
    textAlign: 'center',
  },
});
