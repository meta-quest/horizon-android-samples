/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import metavrx.uiset.compose.input.FieldValidationState
import metavrx.uiset.compose.input.SearchBar
import metavrx.uiset.compose.input.TextField

private val emailPattern = Regex("""[^@\s]+@[A-Za-z]+\.com""")

/**
 * A live validator for the text field.
 *
 * Returning a `Flow` is the shape `TextField` expects, so validation can be asynchronous — a real
 * app might debounce or hit a service. Here it is synchronous and wrapped with `flowOf`.
 *
 * [FieldValidationState] is an enum with no message payload, so the explanatory text belongs in the
 * field's `supportingText`, not in the state.
 */
private fun emailValidator(input: String): Flow<FieldValidationState> = flowOf(
    when {
      input.isEmpty() -> FieldValidationState.Unspecified
      !emailPattern.matches(input) -> FieldValidationState.Invalid
      else -> FieldValidationState.Valid
    },
)

@Composable
fun InputScreen() {
  var plain by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var multi by remember { mutableStateOf("") }
  var search by remember { mutableStateOf("") }
  var audioSearch by remember { mutableStateOf("") }
  var lastSubmitted by remember { mutableStateOf("") }

  Screen {
    Demo("Text field", "Label, placeholder and helper text.") {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TextField(
            value = plain,
            label = "Display name",
            onValueChange = { plain = it },
            placeholder = "How others see you",
            supportingText = "Visible to people in your space",
            modifier = Modifier.fillMaxWidth(),
        )
        TextField(
            value = "",
            label = "Disabled",
            onValueChange = {},
            enabled = false,
            placeholder = "Not editable",
            modifier = Modifier.fillMaxWidth(),
        )
      }
    }

    Demo(
        "Validation",
        "validation returns a Flow<FieldValidationState>. Type an address to see it react.",
    ) {
      TextField(
          value = email,
          label = "Email",
          onValueChange = { email = it },
          placeholder = "you@example.com",
          supportingText = "Needs an alphabetic .com domain",
          validation = ::emailValidator,
          keyboardType = KeyboardType.Email,
          modifier = Modifier.fillMaxWidth(),
      )
    }

    Demo("Multi-line", "singleLine = false for longer input.") {
      TextField(
          value = multi,
          label = "Notes",
          onValueChange = { multi = it },
          placeholder = "Anything worth remembering about this space",
          singleLine = false,
          modifier = Modifier.fillMaxWidth(),
      )
    }

    Demo(
        "Search bar",
        if (lastSubmitted.isEmpty()) "The same component drives this app's sidebar filter."
        else "Last submitted: \"$lastSubmitted\"",
    ) {
      Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SearchBar(
            query = search,
            onQueryChange = { search = it },
            onSearch = { lastSubmitted = it },
            modifier = Modifier.fillMaxWidth(),
        )
        SearchBar(
            query = audioSearch,
            placeholder = "With audio input",
            onAudioClick = {
              // Add your custom audio transcription logic here.
              audioSearch = "Sample text from placeholder for audio transcription"
            },
            onQueryChange = { audioSearch = it },
            onSearch = { lastSubmitted = it },
            modifier = Modifier.fillMaxWidth(),
        )
      }
    }
  }
}
