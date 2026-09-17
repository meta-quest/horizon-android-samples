/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 */

import type {
  LayoutChangeEvent,
  StyleProp,
  TextStyle,
  ViewStyle,
} from 'react-native';

import {useCookVrxTheme, withAlpha} from '../theme/theme';
import React, {useState} from 'react';
import {Pressable, StyleSheet, Text, View} from 'react-native';

export function formatClock(totalSeconds: number): string {
  const safeSeconds = Math.max(0, totalSeconds);
  const minutes = Math.floor(safeSeconds / 60);
  const seconds = safeSeconds % 60;
  return `${minutes}:${seconds.toString().padStart(2, '0')}`;
}

export function Eyebrow({
  text,
  color,
  style,
}: {
  text: string;
  color?: string;
  style?: StyleProp<TextStyle>;
}): React.JSX.Element {
  const theme = useCookVrxTheme();
  return (
    <Text
      style={[theme.text.label, {color: color ?? theme.colors.mutedInk}, style]}>
      {text.toUpperCase()}
    </Text>
  );
}

export function Rule({
  width,
  height,
  color,
  flex,
}: {
  width?: number;
  height: number;
  color: string;
  flex?: boolean;
}): React.JSX.Element {
  return (
    <View
      style={{
        width: flex === true ? undefined : width,
        flex: flex === true ? 1 : undefined,
        height,
        backgroundColor: color,
      }}
    />
  );
}

type ButtonProps = {
  label: string;
  onPress: () => void;
  style?: StyleProp<ViewStyle>;
  disabled?: boolean;
};

const buttonBase: ViewStyle = {
  paddingVertical: 12,
  paddingHorizontal: 16,
  borderRadius: 24,
  alignItems: 'center',
  justifyContent: 'center',
};

export function PrimaryButton({
  label,
  onPress,
  style,
  disabled,
}: ButtonProps): React.JSX.Element {
  const theme = useCookVrxTheme();
  return (
    <Pressable
      onPress={onPress}
      disabled={disabled}
      style={({pressed}) => [
        buttonBase,
        {backgroundColor: theme.colors.tomato, opacity: pressed ? 0.8 : 1},
        disabled === true && styles.disabled,
        style,
      ]}>
      <Text style={[theme.text.label, {color: theme.colors.cream}]}>
        {label.toUpperCase()}
      </Text>
    </Pressable>
  );
}

export function SecondaryButton({
  label,
  onPress,
  style,
  disabled,
}: ButtonProps): React.JSX.Element {
  const theme = useCookVrxTheme();
  return (
    <Pressable
      onPress={onPress}
      disabled={disabled}
      style={({pressed}) => [
        buttonBase,
        {backgroundColor: theme.colors.citrus, opacity: pressed ? 0.8 : 1},
        disabled === true && styles.disabled,
        style,
      ]}>
      <Text style={[theme.text.label, {color: theme.colors.ink}]}>
        {label.toUpperCase()}
      </Text>
    </Pressable>
  );
}

export function BorderedButton({
  label,
  onPress,
  style,
  disabled,
  borderColor,
  labelColor,
}: ButtonProps & {
  borderColor?: string;
  labelColor?: string;
}): React.JSX.Element {
  const theme = useCookVrxTheme();
  return (
    <Pressable
      onPress={onPress}
      disabled={disabled}
      style={({pressed}) => [
        buttonBase,
        styles.bordered,
        {
          borderColor: borderColor ?? theme.colors.ink,
          opacity: pressed ? 0.8 : 1,
        },
        disabled === true && styles.disabled,
        style,
      ]}>
      <Text style={[theme.text.label, {color: labelColor ?? theme.colors.ink}]}>
        {label.toUpperCase()}
      </Text>
    </Pressable>
  );
}

/** Stands in for the UISet icon buttons; React Native core ships no icon set. */
export function CloseButton({
  onPress,
  accessibilityLabel,
  background,
}: {
  onPress: () => void;
  accessibilityLabel: string;
  background?: string;
}): React.JSX.Element {
  const theme = useCookVrxTheme();
  return (
    <Pressable
      onPress={onPress}
      accessibilityRole="button"
      accessibilityLabel={accessibilityLabel}
      style={({pressed}) => [
        styles.closeButton,
        {
          backgroundColor: background ?? withAlpha(theme.colors.ink, 0.08),
          opacity: pressed ? 0.7 : 1,
        },
      ]}>
      <Text style={[theme.text.bodyStrong, {color: theme.colors.ink}]}>✕</Text>
    </Pressable>
  );
}

export function Card({
  variant,
  children,
  onPress,
  style,
  contentPadding,
}: {
  variant: 'primary' | 'secondary' | 'outlined';
  children: React.ReactNode;
  onPress?: () => void;
  style?: StyleProp<ViewStyle>;
  contentPadding: number;
}): React.JSX.Element {
  const theme = useCookVrxTheme();
  const variantStyle: ViewStyle =
    variant === 'primary'
      ? {backgroundColor: theme.colors.citrus}
      : variant === 'secondary'
        ? {backgroundColor: theme.colors.cream}
        : {borderWidth: 1, borderColor: withAlpha(theme.colors.ink, 0.24)};
  const content = (
    <View style={[styles.card, variantStyle, {padding: contentPadding}, style]}>
      {children}
    </View>
  );
  return onPress == null ? (
    content
  ) : (
    <Pressable onPress={onPress}>{content}</Pressable>
  );
}

/**
 * The procedural plate illustration. React Native core has no gradient primitive,
 * so the two-tone background is drawn as bands rather than a linear gradient.
 */
export function DishArtwork({
  recipeId,
  height,
  style,
}: {
  recipeId: string;
  height: number;
  style?: StyleProp<ViewStyle>;
}): React.JSX.Element {
  const theme = useCookVrxTheme();
  const dimensions = theme.dimensions;
  const artwork = artworkColors(recipeId);
  const [width, setWidth] = useState(0);
  const onLayout = (event: LayoutChangeEvent) =>
    setWidth(event.nativeEvent.layout.width);

  const plateSize = width * 0.62;
  const foodSize = width * 0.34;
  const garnishSize = width * 0.16;

  return (
    <View
      onLayout={onLayout}
      style={[
        styles.artwork,
        {
          height,
          borderRadius: dimensions.artworkCornerRadius,
          borderWidth: dimensions.artworkBorderWidth,
          borderColor: withAlpha(theme.colors.ink, 0.16),
          backgroundColor: artwork.background,
        },
        style,
      ]}>
      <View
        style={[styles.artworkBand, {backgroundColor: artwork.backgroundEnd}]}
      />
      <View
        style={[
          styles.artworkLayer,
          {
            width: plateSize,
            height: plateSize,
            borderRadius: plateSize / 2,
            backgroundColor: artwork.plate,
            borderWidth: dimensions.artworkPlateBorderWidth,
            borderColor: withAlpha(theme.colors.cream, 0.7),
            transform: [{rotate: '-8deg'}],
          },
        ]}
      />
      <View
        style={[
          styles.artworkLayer,
          {
            width: foodSize,
            height: foodSize,
            borderRadius: foodSize / 2,
            backgroundColor: artwork.food,
          },
        ]}
      />
      <View
        style={[
          styles.artworkLayer,
          {
            width: garnishSize,
            height: garnishSize,
            borderRadius: garnishSize * 0.4,
            backgroundColor: artwork.garnish,
            transform: [{rotate: '18deg'}],
          },
        ]}
      />
      <Text
        style={[
          theme.text.label,
          styles.artworkLabel,
          {
            color: withAlpha(artwork.backgroundEnd, 0.75),
            fontSize: dimensions.artworkLabelFontSize,
            margin: dimensions.artworkLabelPadding,
          },
        ]}>
        {recipeId.slice(0, 2).toUpperCase()}
      </Text>
    </View>
  );
}

type ArtworkColors = Readonly<{
  background: string;
  backgroundEnd: string;
  plate: string;
  food: string;
  garnish: string;
}>;

const ARTWORK_COLORS: Readonly<Record<string, ArtworkColors>> = {
  smoky_shakshuka: {
    background: '#FFB16D',
    backgroundEnd: '#D84227',
    plate: '#7A1E18',
    food: '#F8D985',
    garnish: '#3D6B45',
  },
  midnight_miso_noodles: {
    background: '#284559',
    backgroundEnd: '#101E29',
    plate: '#D8C39B',
    food: '#9B6B3D',
    garnish: '#E65338',
  },
  charred_lemon_chicken: {
    background: '#E6D64A',
    backgroundEnd: '#8E8B2F',
    plate: '#F1D0A1',
    food: '#8B4B2B',
    garnish: '#214C3A',
  },
  citrus_olive_cake: {
    background: '#F6C76E',
    backgroundEnd: '#E97952',
    plate: '#FFF3D3',
    food: '#E8B45C',
    garnish: '#8A6D2A',
  },
  crispy_mushroom_rice: {
    background: '#B6A284',
    backgroundEnd: '#5B4939',
    plate: '#F3E3C1',
    food: '#806048',
    garnish: '#B13C2F',
  },
  green_goddess_beans: {
    background: '#B6D37B',
    backgroundEnd: '#39724E',
    plate: '#F2E9C9',
    food: '#7FB66D',
    garnish: '#E4E85C',
  },
  market_tomato_toast: {
    background: '#F6A166',
    backgroundEnd: '#E44D32',
    plate: '#D9A96E',
    food: '#C22D29',
    garnish: '#416943',
  },
};

const FALLBACK_ARTWORK: ArtworkColors = {
  background: '#D2B8D8',
  backgroundEnd: '#5C4666',
  plate: '#FFE6C2',
  food: '#292025',
  garnish: '#E9C849',
};

function artworkColors(id: string): ArtworkColors {
  return ARTWORK_COLORS[id] ?? FALLBACK_ARTWORK;
}

const styles = StyleSheet.create({
  disabled: {
    opacity: 0.4,
  },
  bordered: {
    borderWidth: 1.5,
    backgroundColor: 'transparent',
  },
  closeButton: {
    width: 34,
    height: 34,
    borderRadius: 17,
    alignItems: 'center',
    justifyContent: 'center',
  },
  card: {
    borderRadius: 12,
    overflow: 'hidden',
  },
  artwork: {
    width: '100%',
    overflow: 'hidden',
    alignItems: 'center',
    justifyContent: 'center',
  },
  artworkBand: {
    position: 'absolute',
    top: '55%',
    left: 0,
    right: 0,
    bottom: 0,
  },
  artworkLayer: {
    position: 'absolute',
  },
  artworkLabel: {
    position: 'absolute',
    right: 0,
    bottom: 0,
    textAlign: 'right',
  },
});
