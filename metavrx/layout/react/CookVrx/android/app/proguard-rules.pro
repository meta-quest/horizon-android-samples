# Copyright (c) Meta Platforms, Inc. and affiliates.
#
# This source code is licensed under the MIT license found in the
# LICENSE file in the root directory of this source tree.

# Sample-owned Android entry points. SDK APIs are retained by the AAR's
# consumer rules, so this file deliberately contains no SDK package wildcard.
-keep class com.meta.spatial.samples.cookvrxrn.MainApplication { public <init>(); }
-keep class com.meta.spatial.samples.cookvrxrn.MainActivity { public <init>(); }
