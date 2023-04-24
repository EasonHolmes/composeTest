package com.example.myapplication.ui.utils

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class TimerLifecycle(
    private val millisUntilFinished: Long,
    private val countDownInterval: Long
) : CountDownTimer(millisUntilFinished, countDownInterval), DefaultLifecycleObserver {
    private var running = false
    override fun onTick(millisUntilFinished: Long) {
        running = true
    }

    override fun onFinish() {
        running = false
    }
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        onFinish()
        this.cancel()
    }

    fun isRunning() = running
}
