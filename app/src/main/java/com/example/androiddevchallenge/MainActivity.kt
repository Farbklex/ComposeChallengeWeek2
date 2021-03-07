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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    private val timerViewModel by viewModels<CountdownViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                TimerScreen(
                    timerViewModel,
                    { timerViewModel.onTimerChanged(it) },
                    { timerViewModel.startClicked() }
                )
            }
        }
    }
}

// Start building your app here!
@Composable
fun TimerScreen(
    timerViewModel: CountdownViewModel,
    onTimeSet: (Int) -> Unit,
    onStartClicked: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        val time: Int by timerViewModel.timer.observeAsState(0)
        TimerView(
            time,
            onTimeSet,
            onStartClicked
        )
    }
}

@Composable
fun TimerView(
    time: Int,
    onTimeSet: (Int) -> Unit,
    onStartClicked: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Text(text = "$time")
            Button(onClick = { onStartClicked() }) {
                Text(text = "Start")
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(3) }) {
                    Text(text = "🍵")
                }
                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(4) }) {
                    Text(text = "🍜")
                }
                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(7) }) {
                    Text(text = "🥚")
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        TimerView(1, {}, {})
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        TimerView(1, {}, {})
    }
}
