package com.example.a25a_hw1.utilities

class Constants {

    object GameLogic {
        const val TUMBLEWEEDS_PER_ROW = 1
        const val DELAY = 500L
        const val DODGE_POINTS = 5
        const val COIN_POINTS = 100
        const val VIBRATION_DURATION = 500L
        const val TUMBLEWEEDS_STARTING_OFFSET = 3
        const val COWBOY_ROW_END_OFFSET = 1
    }

    object Sensors {
        const val USING_TILT = false
        const val SAMPLE_RATE = 500
        const val UP_DELAY_MODIFIER = 30
        const val DOWN_DELAY_MODIFIER = 20
        const val MAX_UP_SPEED = -200
        const val MAX_DOWN_SPEED = 200

        const val TILT_SIDE_THRESHOLD: Double = 3.0
        const val TILT_UP_THRESHOLD: Double = 1.0
        const val TILT_DOWN_THRESHOLD: Double = 5.0
    }

    object BundleKeys {
        const val SCORE_KEY:String = "SCORE_KEY"
        const val STATUS_KEY :String = "STATUS_KEY"
    }
}