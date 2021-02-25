/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = 0xFF689f38.toInt()

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

private sealed class Screen {
    object Home : Screen()
    data class Detail(val id: Long) : Screen()
}

val repository = Repository()

// Start building your app here!
@Composable
fun MyApp() {
    var screen by remember { mutableStateOf<Screen>(Screen.Home) }

    Crossfade(targetState = screen) {
        when (it) {
            Screen.Home -> HomeScreen(
                repository = repository,
                navigateToGoatAction = { id -> screen = Screen.Detail(id) }
            )
            is Screen.Detail -> DetailScreen(
                repository = repository,
                goToHomeScreenAction = { screen = Screen.Home },
                goatId = it.id
            )
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
