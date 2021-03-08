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
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    { timerViewModel.startClicked() },
                    { timerViewModel.stopClicked() }
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
    onStartClicked: () -> Unit,
    onStopClicked: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        val time: String by timerViewModel.timer.observeAsState("00:00")
        val showStartButton: Boolean by timerViewModel.showStartButton.observeAsState(true)
        val showStopButton: Boolean by timerViewModel.showStopButton.observeAsState(false)
        TimerView(
            time,
            showStartButton,
            showStopButton,
            onTimeSet,
            onStartClicked,
            onStopClicked
        )
    }
}

@Composable
fun TimerView(
    time: String,
    showStartButton: Boolean,
    showStopButton: Boolean,
    onTimeSet: (Int) -> Unit,
    onStartClicked: () -> Unit,
    onStopClicked: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = time, style = MaterialTheme.typography.h2)
            if(showStartButton){
                Button(onClick = { onStartClicked() }) {
                    Text(text = "Start")
                }
            }

            if(showStopButton){
                Button(onClick = { onStopClicked() }) {
                    Text(text = "Stop")
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(3 * 60 * 1000) }, enabled = showStartButton) {
                    Text(text = "🍵", fontSize = 36.sp)
                }

                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(4 * 60 * 1000) }, enabled = showStartButton) {
                    Text(text = "🍜", fontSize = 36.sp)
                }

                Button(modifier = Modifier.wrapContentSize(), onClick = { onTimeSet(7 * 60 * 1000) }, enabled = showStartButton) {
                    Text(text = "🥚", fontSize = 36.sp)
                }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        TimerView("1", true, false, {}, {}, {})
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        TimerView("1", true, false, {}, {}, {})
    }
}
