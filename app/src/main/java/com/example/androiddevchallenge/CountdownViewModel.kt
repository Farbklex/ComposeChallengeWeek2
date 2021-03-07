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

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CountdownViewModel : ViewModel() {
    private val _timer = MutableLiveData(0)
    val timer: LiveData<Int> = _timer

    fun onTimerChanged(newTimer: Int) {
        _timer.value = newTimer
    }

    fun startClicked() {
        _timer.value?.let {
            val startTime: Long = (it * 60 * 1000).toLong()
            val countDowntimer = object : CountDownTimer(startTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _timer.value = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                }
            }.start()
        }
    }
}
