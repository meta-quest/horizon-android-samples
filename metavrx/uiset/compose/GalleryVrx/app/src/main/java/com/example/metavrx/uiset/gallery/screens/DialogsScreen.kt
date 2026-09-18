/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.DemoRow
import com.example.metavrx.uiset.gallery.ui.Screen
import com.example.metavrx.uiset.gallery.ui.icon
import kotlinx.collections.immutable.persistentListOf
import metavrx.uiset.compose.button.ButtonStyle
import metavrx.uiset.compose.button.LabelButton
import metavrx.uiset.compose.dialog.BasicDialog
import metavrx.uiset.compose.dialog.ChoiceListDialog
import metavrx.uiset.compose.dialog.ChoiceListDialogItem
import metavrx.uiset.compose.dialog.DialogAction
import metavrx.uiset.compose.dialog.DialogProgress
import metavrx.uiset.compose.dialog.IconDialog
import metavrx.uiset.compose.dialog.InfoDialog
import metavrx.uiset.compose.theme.icons.Icons

/**
 * Dialogs are modal, so they cannot be laid out inline like the other components — each is opened
 * from a button, which is also how a consumer would use them.
 */
@Composable
fun DialogsScreen() {
  var basic by remember { mutableStateOf(false) }
  var withIcon by remember { mutableStateOf(false) }
  var threeActions by remember { mutableStateOf(false) }
  var info by remember { mutableStateOf(false) }
  var choiceList by remember { mutableStateOf(false) }

  val refreshRates = remember {
    persistentListOf(
        ChoiceListDialogItem(
            leadingIcon = icon { Icons.Regular.Settings },
            title = "72 Hz",
            subtitle = "Best battery life",
        ),
        ChoiceListDialogItem(
            leadingIcon = icon { Icons.Regular.Settings },
            title = "90 Hz",
            subtitle = "Balanced",
        ),
        ChoiceListDialogItem(
            leadingIcon = icon { Icons.Regular.Settings },
            title = "120 Hz",
            subtitle = "Smoothest motion",
        ),
    )
  }
  var selectedRate by remember { mutableStateOf(refreshRates[1]) }

  Screen {
    Demo("Basic with progress", "Title, description, actions, and optional step progress.") {
      DemoRow {
        LabelButton(
            label = "Open setup dialog",
            onClick = { basic = true },
            style = ButtonStyle.Primary,
        )
      }
    }

    Demo("With icon", "Adds a leading icon above the title.") {
      DemoRow {
        LabelButton(
            label = "Open icon dialog",
            onClick = { withIcon = true },
            style = ButtonStyle.Secondary,
        )
      }
    }

    Demo("Three actions", "Primary, secondary and tertiary, for destructive confirmations.") {
      DemoRow {
        LabelButton(
            label = "Open three-action dialog",
            onClick = { threeActions = true },
            style = ButtonStyle.Secondary,
        )
      }
    }

    Demo("Info banner", "Adds a highlighted banner between the description and the actions.") {
      DemoRow {
        LabelButton(
            label = "Open info dialog",
            onClick = { info = true },
            style = ButtonStyle.Secondary,
        )
      }
    }

    Demo(
        "Choice list",
        "A selectable list inside the dialog, for picking one of several options.",
    ) {
      DemoRow {
        LabelButton(
            label = "Open choice list dialog",
            onClick = { choiceList = true },
            style = ButtonStyle.Secondary,
        )
      }
    }
  }

  if (basic) {
    BasicDialog(
        title = "Set up passthrough",
        description = "Review your surroundings before continuing to the final confirmation.",
        primaryAction = DialogAction("Continue", onClick = { basic = false }),
        secondaryAction = DialogAction("Cancel", onClick = { basic = false }),
        onDismissRequest = { basic = false },
        progress = DialogProgress(currentStep = 2, totalSteps = 3),
    )
  }

  if (withIcon) {
    IconDialog(
        title = "Calibration complete",
        description = "Your play area has been measured and saved.",
        icon = icon { Icons.Regular.Info },
        primaryAction = DialogAction("Done", onClick = { withIcon = false }),
        onDismissRequest = { withIcon = false },
    )
  }

  if (threeActions) {
    BasicDialog(
        title = "Discard changes?",
        description = "This layout has unsaved changes. Discarding cannot be undone.",
        primaryAction =
            DialogAction("Discard", onClick = { threeActions = false }, destructive = true),
        secondaryAction = DialogAction("Save", onClick = { threeActions = false }),
        tertiaryAction = DialogAction("Cancel", onClick = { threeActions = false }),
        onDismissRequest = { threeActions = false },
    )
  }

  if (info) {
    InfoDialog(
        title = "Guardian is off",
        description = "You are in a stationary boundary. Move carefully.",
        infoIcon = icon { Icons.Regular.Info },
        infoText = "Turning Guardian back on is recommended in open spaces.",
        primaryAction = DialogAction("Turn on", onClick = { info = false }),
        secondaryAction = DialogAction("Keep off", onClick = { info = false }),
        onDismissRequest = { info = false },
    )
  }

  if (choiceList) {
    ChoiceListDialog(
        title = "Display refresh rate",
        description = "Higher rates look smoother but use more battery.",
        listHeader = "Available rates",
        items = refreshRates,
        selectedItem = selectedRate,
        onItemSelected = { selectedRate = it },
        primaryAction = DialogAction("Apply", onClick = { choiceList = false }),
        secondaryAction = DialogAction("Cancel", onClick = { choiceList = false }),
        onDismissRequest = { choiceList = false },
    )
  }
}
