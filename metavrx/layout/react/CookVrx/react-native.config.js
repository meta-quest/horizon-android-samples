/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

// Every dependency, including the two `@metavr/layout*-compat` packages, autolinks normally.
//
// Autolinking the SDK is what makes spatial promotion work: it is the only
// mechanism that runs New Architecture codegen for the package, and codegen is
// what emits the TurboModule's JSI binding (`NativeSpatialWindowModuleSpecJSI`)
// plus the `autolinking_ModuleProvider` entry that
// `DefaultTurboModuleManagerDelegate::getTurboModule` consults. The Java
// `ReactPackage` list is not a fallback for that binding — with the package
// opted out, `TurboModuleRegistry.get('SpatialWindowModule')` returns null and
// every window silently renders inline.
//
// The only C++ this produces is React Native's own codegen output, compiled by the app from
// the generated CMakeLists. The `com.meta.metavrx.layout:layout-react-compat` AAR is
// Kotlin only, so nothing is duplicated and the app needs no packaging/exclusion
// rules.
module.exports = {};
