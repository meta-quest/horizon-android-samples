/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import {useCookVrxTheme} from '../theme/theme';
import {Eyebrow} from './primitives';
import React from 'react';
import {StyleSheet, Text, View} from 'react-native';

export function Fact({label, value}: {label: string; value: string}): React.JSX.Element {
  const {colors, dimensions, text} = useCookVrxTheme();
  return (
    <View
      style={[
        styles.fact,
        {
          paddingHorizontal: dimensions.detailFactHorizontalPadding,
          paddingVertical: dimensions.detailFactVerticalPadding,
        },
      ]}>
      <Eyebrow text={label} />
      <Text style={[text.bodyStrong, {color: colors.ink}]}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  fact: {
    flex: 1,
    alignItems: 'center',
  },
});
