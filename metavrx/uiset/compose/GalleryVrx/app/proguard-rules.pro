# Copyright (c) Meta Platforms, Inc. and affiliates.
#
# This source code is licensed under the MIT license found in the
# LICENSE file in the root directory of this source tree.

# This file is named by proguardFiles in the release buildType, so it must exist for
# assembleRelease to run — do not delete it just because it declares no rules. The
# sample needs none of its own: it is plain Compose with no reflection, no
# serialization and no JNI, and the UI Set, Compose and AndroidX AARs each ship their
# own consumer rules.

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
