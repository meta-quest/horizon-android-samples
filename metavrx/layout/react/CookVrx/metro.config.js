/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

const {getDefaultConfig, mergeConfig} = require('@react-native/metro-config');
const fs = require('fs');
const path = require('path');

const appNodeModules = path.resolve(__dirname, 'node_modules');
const sdkDistPackages = [
  '@metavr/layout-compat',
  '@metavr/layout-window-compat',
]
  .map(packageName => path.join(appNodeModules, packageName))
  .filter(packagePath => fs.existsSync(packagePath))
  .map(packagePath => fs.realpathSync(packagePath))
  .filter(packagePath => !packagePath.startsWith(appNodeModules));

module.exports = mergeConfig(getDefaultConfig(__dirname), {
  watchFolders: sdkDistPackages,
  resolver: {
    // Metro resolves a symlinked package's own imports from its real location.
    // Pin shared runtime packages to this app's copies so local links and
    // registry-installed packages behave identically.
    nodeModulesPaths: [appNodeModules],
    extraNodeModules: {
      react: path.join(appNodeModules, 'react'),
      'react-native': path.join(appNodeModules, 'react-native'),
    },
  },
});
