package com.timer.utils

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit


class CountDownUtils {

    private lateinit var countDownTimer: CountDownTimer

    interface CountDownCallbacks {
        fun onCountDownFinished()
        fun onCountDownUpdated(millisUntilFinished: Long, time: String)
    }

    fun startCountDownTimer(millisToStart: Long, countDownCallbacks: CountDownCallbacks) {

        countDownTimer = object : CountDownTimer(millisToStart, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                val time = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                countDownCallbacks.onCountDownUpdated(millisUntilFinished,time)

            }

            override fun onFinish() {

                countDownCallbacks.onCountDownFinished()

            }

        }.start()

    }

    fun pauseTimer() {

        if (::countDownTimer.isInitialized)
            countDownTimer.cancel()

    }

    fun resumeTimer(leftMillis: Long, countDownCallbacks: CountDownCallbacks) {

        startCountDownTimer(leftMillis, countDownCallbacks)

    }

    fun stopTimer() {

        if (::countDownTimer.isInitialized)
            countDownTimer.cancel()

    }

}