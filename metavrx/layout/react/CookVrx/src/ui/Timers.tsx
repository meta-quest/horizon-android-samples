/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {KitchenTimer} from '../state/useCookVrx';
import type {StyleProp, ViewStyle} from 'react-native';

import {useCookVrxTheme, withAlpha} from '../theme/theme';
import {
  Card,
  CloseButton,
  Eyebrow,
  formatClock,
  SecondaryButton,
} from './primitives';
import React, {useEffect, useState} from 'react';
import {ScrollView, StyleSheet, Text, View} from 'react-native';

export function TimersPanel({
  timers,
  onAddQuickTimer,
  onExtendTimer,
  onRemoveTimer,
  style,
}: {
  timers: ReadonlyArray<KitchenTimer>;
  onAddQuickTimer: (seconds: number) => void;
  onExtendTimer: (id: number, seconds: number) => void;
  onRemoveTimer: (id: number) => void;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  const [now, setNow] = useState(() => Date.now());

  useEffect(() => {
    const tick = setInterval(() => setNow(Date.now()), 1000);
    return () => clearInterval(tick);
  }, []);

  return (
    <ScrollView
      style={[styles.root, {backgroundColor: colors.ocean}, style]}
      contentContainerStyle={{padding: dimensions.timersContentPadding}}>
      <Eyebrow text="Station 02" color={colors.citrus} />
      <Text style={[text.title, {color: colors.cream}]}>Timers</Text>
      <View style={{height: dimensions.timerQuickActionsTopSpacing}} />
      <View style={[styles.quickRow, {gap: dimensions.timerQuickActionSpacing}]}>
        <SecondaryButton
          label="+30 sec"
          onPress={() => onAddQuickTimer(30)}
          style={styles.quickButton}
        />
        <SecondaryButton
          label="+5 min"
          onPress={() => onAddQuickTimer(300)}
          style={styles.quickButton}
        />
      </View>
      <View style={{height: dimensions.timersListSpacing}} />
      {timers.length === 0 ? (
        <Card variant="outlined" contentPadding={0}>
          <View
            style={[
              styles.empty,
              {
                paddingHorizontal: dimensions.timerEmptyHorizontalPadding,
                paddingVertical: dimensions.timerEmptyVerticalPadding,
              },
            ]}>
            <Text
              style={[
                text.label,
                styles.centered,
                {
                  color: withAlpha(colors.cream, 0.72),
                  fontSize: dimensions.timerEmptyFontSize,
                },
              ]}>
              {'NO FIRES\nTO WATCH YET'}
            </Text>
          </View>
        </Card>
      ) : (
        timers.map(timer => (
          <View key={timer.id} style={{marginBottom: dimensions.timerCardSpacing}}>
            <TimerCard
              timer={timer}
              remainingSeconds={Math.floor(
                Math.max(0, timer.endsAtMs - now) / 1000,
              )}
              onExtend={seconds => onExtendTimer(timer.id, seconds)}
              onRemove={() => onRemoveTimer(timer.id)}
            />
          </View>
        ))
      )}
      <View style={{height: dimensions.timersListSpacing}} />
      <Text style={[text.note, {color: withAlpha(colors.cream, 0.72)}]}>
        Use step timers for precision. Add a quick check whenever a pan, cup, or
        broiler needs your future attention.
      </Text>
    </ScrollView>
  );
}

function TimerCard({
  timer,
  remainingSeconds,
  onExtend,
  onRemove,
}: {
  timer: KitchenTimer;
  remainingSeconds: number;
  onExtend: (seconds: number) => void;
  onRemove: () => void;
}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  const finished = remainingSeconds === 0;
  const progress =
    timer.durationSeconds === 0
      ? 0
      : Math.min(1, Math.max(0, remainingSeconds / timer.durationSeconds));

  return (
    <Card
      variant={finished ? 'primary' : 'secondary'}
      contentPadding={dimensions.timerCardPadding}>
      <View style={styles.cardHeader}>
        <Eyebrow text={finished ? 'Ready now' : timer.label} color={colors.ink} />
        <CloseButton onPress={onRemove} accessibilityLabel="Remove timer" />
      </View>
      <View style={{height: dimensions.timerClockSpacing}} />
      <Text
        style={[
          text.display,
          {
            color: colors.ink,
            fontSize: dimensions.timerClockFontSize,
            lineHeight: dimensions.timerClockLineHeight,
          },
        ]}>
        {finished ? 'DONE' : formatClock(remainingSeconds)}
      </Text>
      <View style={{height: dimensions.timerProgressSpacing}} />
      <View
        style={{
          height: dimensions.timerProgressHeight,
          backgroundColor: withAlpha(colors.ink, 0.14),
        }}>
        <View
          style={{
            width: `${progress * 100}%`,
            height: dimensions.timerProgressHeight,
            backgroundColor: finished ? 'transparent' : colors.tomato,
          }}
        />
      </View>
      <View style={{height: dimensions.timerExtensionTopSpacing}} />
      <View style={[styles.quickRow, {gap: dimensions.timerExtensionSpacing}]}>
        <SecondaryButton
          label="+30 sec"
          onPress={() => onExtend(30)}
          style={styles.quickButton}
        />
        <SecondaryButton
          label="+5 min"
          onPress={() => onExtend(300)}
          style={styles.quickButton}
        />
      </View>
    </Card>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  quickRow: {
    flexDirection: 'row',
  },
  quickButton: {
    flex: 1,
  },
  empty: {
    alignItems: 'center',
    justifyContent: 'center',
  },
  centered: {
    textAlign: 'center',
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
});
