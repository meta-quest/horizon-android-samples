/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.meta.spatial.samples.cookvrxrn

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultReactActivityDelegate

/**
 * Main activity for the CookVrx React sample.
 *
 * The main component name must match the string registered via `AppRegistry.registerComponent` in
 * `index.js` (which reads it from `app.json`).
 */
class MainActivity : ReactActivity() {

  override fun getMainComponentName(): String = "CookVrxRn"

  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName)
}
