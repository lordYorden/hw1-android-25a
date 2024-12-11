package com.example.a25a_hw1.logic

import com.example.a25a_hw1.utilities.Constants

class SettingsManager {
    object Difficulty {
        var delay: Long = Constants.GameLogic.DELAY
            private set

        var tumbleweedsPerRow: Int = Constants.GameLogic.TUMBLEWEEDS_PER_ROW
            private set

        fun setSpeed(speed: Long) {
            delay = speed
        }

        fun setTumbleweedsPerRow(num: Int) {
            tumbleweedsPerRow = num
        }
    }
}