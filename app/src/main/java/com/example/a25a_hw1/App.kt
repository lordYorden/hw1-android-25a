package com.example.a25a_hw1

import android.app.Application
import com.example.a25a_hw1.utilities.SignalManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SignalManager.init(this)
    }
}