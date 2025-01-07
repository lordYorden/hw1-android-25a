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

    object BundleKeys {
        const val SCORE_KEY: String = "SCORE_KEY"
        const val STATUS_KEY: String = "STATUS_KEY"
    }

    object SP_keys {
        const val SCOREBOARD_KEY: String = "SCOREBOARD_KEY"
    }

    object ScoreDisplay {
        const val NUM_OF_SCORES_DISPLAYED: Int = 10
    }

    object Permission {
        const val LOCATION_REQUEST_CODE: Int = 20
    }

}