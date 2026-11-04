/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {CollectionStats, CountGroup} from '../data/collectionStats';
import type {StyleProp, ViewStyle} from 'react-native';

import {useCookVrxTheme, withAlpha} from '../theme/theme';
import {Card, CloseButton, Eyebrow, Rule} from './primitives';
import {Fact} from './Fact';
import React from 'react';
import {ScrollView, StyleSheet, Text, View} from 'react-native';

export function CollectionInfoPanel({
  recipeCount,
  stats,
  onCloseButtonPress,
  style,
}: {
  recipeCount: number;
  stats: CollectionStats | null;
  onCloseButtonPress: () => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  const sectionStyle = {marginTop: dimensions.collectionInfoSectionTopSpacing};
  return (
    <ScrollView
      style={[styles.root, {backgroundColor: colors.cream}, style]}
      contentContainerStyle={{padding: dimensions.collectionInfoContentPadding}}>
      <View style={styles.headerRow}>
        <Eyebrow text="At a glance" color={colors.tomato} />
        <CloseButton
          onPress={onCloseButtonPress}
          accessibilityLabel="Close collection info"
        />
      </View>
      <View style={{height: dimensions.collectionInfoTitleTopSpacing}} />
      <Text style={[text.title, {color: colors.ink}]}>{`${recipeCount} recipes`}</Text>
      {stats != null && (
        <>
          <View style={{height: dimensions.collectionInfoFactsTopSpacing}} />
          <Card variant="outlined" contentPadding={0}>
            <View style={styles.factsRow}>
              <Fact label="Min" value={`${stats.quickest.totalMinutes}m`} />
              <Fact label="Avg" value={`${stats.averageMinutes}m`} />
              <Fact label="Max" value={`${stats.longest.totalMinutes}m`} />
            </View>
          </Card>
          <View style={{height: dimensions.collectionInfoTimeNoteTopSpacing}} />
          <Text style={[text.note, {color: colors.mutedInk}]}>
            {`Fastest is ${stats.quickest.title}; ${stats.longest.title} takes the longest.`}
          </Text>
          <CountSection
            title="By category"
            group={stats.byCategory}
            style={sectionStyle}
          />
          <CountSection
            title="By difficulty"
            group={stats.byDifficulty}
            style={sectionStyle}
          />
          <CountSection
            title="Pantry staples"
            group={stats.staples}
            countLabel={count => `${count}/${recipeCount}`}
            style={sectionStyle}
          />
        </>
      )}
    </ScrollView>
  );
}

function CountSection({
  title,
  group,
  countLabel = count => `${count}`,
  style,
}: {
  title: string;
  group: CountGroup;
  countLabel?: (count: number) => string;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View style={style}>
      <View style={styles.sectionHeader}>
        <Eyebrow text={title} color={colors.ink} />
        <View style={{width: dimensions.collectionInfoSectionRuleSpacing}} />
        <Rule
          flex
          height={dimensions.collectionInfoSectionRuleHeight}
          color={withAlpha(colors.ink, 0.22)}
        />
      </View>
      {group.counts.map(({label, count}) => (
        <View
          key={label}
          style={{paddingVertical: dimensions.collectionInfoRowVerticalPadding}}>
          <View style={styles.countRow}>
            <Text style={[text.body, {color: colors.ink}]}>{label}</Text>
            <Eyebrow text={countLabel(count)} color={colors.ink} />
          </View>
          <View
            style={{
              width: `${(count / group.total) * 100}%`,
              height: dimensions.collectionInfoBarHeight,
              backgroundColor: colors.citrus,
            }}
          />
        </View>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  headerRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  factsRow: {
    flexDirection: 'row',
  },
  sectionHeader: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  countRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
});
