package com.example.a25a_hw1.utilities

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.VIBRATOR_MANAGER_SERVICE
import androidx.appcompat.app.AppCompatActivity.VIBRATOR_SERVICE
import java.lang.ref.WeakReference

class SignalManager private constructor(context: Context) {

    companion object {

        @Volatile
        private var instance: SignalManager? = null

        fun getInstance(): SignalManager {
            return instance
                ?: throw IllegalStateException("SignalManager must be initialized by calling init(context) before use")
        }

        fun init(context: Context): SignalManager {
            return instance ?: synchronized(this) {
                instance ?: SignalManager(context).also { instance = it }
            }
        }
    }

    private val contextRef = WeakReference(context)

    fun toast(text: String) {
        contextRef.get()?.let { context ->
            Toast
                .makeText(
                    context,
                    text,
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    fun vibrate(duration: Long = 500L) {
        contextRef.get()?.let { context ->
            val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val SOSPattern = longArrayOf(
                    0,
                    200,
                    100,
                    200,
                    100,
                    200,
                    300,
                    500,
                    100,
                    500,
                    100,
                    500,
                    300,
                    200,
                    100,
                    200,
                    100,
                    200
                )

                val waveFormVibrationEffect = VibrationEffect.createWaveform(
                    SOSPattern,
                    -1
                )

                val oneShotVibrationEffect = VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )

                vibrator.vibrate(oneShotVibrationEffect)
            } else {
                vibrator.vibrate(duration)
            }
        }
    }

}