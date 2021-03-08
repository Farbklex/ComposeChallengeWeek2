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
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class CountdownViewModel : ViewModel() {
    private val _timer = MutableLiveData(0)
    private val _timerText = Transformations.map(_timer) { millisecondsToStringTime(it) }
    private val _isRunning = MutableLiveData<Boolean>(false)

    /**
     * The countdown time in minutes:seconds
     */
    val timer: LiveData<String> = _timerText
    val showStartButton: LiveData<Boolean> = Transformations.map(_isRunning) { !it }
    val showStopButton: LiveData<Boolean> = Transformations.map(_isRunning) { it }

    var countDownTimer: CountDownTimer? = null

    private fun millisecondsToStringTime(milliseconds: Int): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        return "$minutes:${seconds - (minutes * 60)}"
    }

    fun onTimerChanged(newTimer: Int) {
        _timer.value = newTimer
    }

    fun startClicked() {
        _timer.value?.let {
            val startTime: Long = (it).toLong()
            countDownTimer = object : CountDownTimer(startTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    _timer.value = millisUntilFinished.toInt()
                }

                override fun onFinish() {
                    _isRunning.value = false
                }
            }.start()
            _isRunning.value = true
        }
    }

    fun stopClicked() {
        countDownTimer?.let {
            it.cancel()
            _isRunning.value = false
        }
    }
}
