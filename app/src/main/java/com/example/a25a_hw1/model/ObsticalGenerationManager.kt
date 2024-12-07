package com.example.a25a_hw1.model

import com.example.a25a_hw1.utilities.Constants

class ObstacleGenerationManager(private val numLanes: Int, private val tumbleweedsPerRow: Int = 1) {

    val currentRow: Array<Boolean>
        get() = generateTumbleweedsRow()

    fun generateTumbleweeds(numRows: Int): Array<Array<Boolean>> {
        val tumbleweeds = Array(numRows) { Array(numLanes) { false } }
        for (i in 0 until numRows-Constants.GameLogic.TUMBLEWEEDS_STARTING_OFFSET) {
            tumbleweeds[i] = generateTumbleweedsRow()
        }
        return tumbleweeds
    }

    private fun generateTumbleweedsRow(): Array<Boolean> {
        val tumbleweeds = Array(numLanes) { false }

        for (i in 1..tumbleweedsPerRow) {
            val rand = (1..numLanes).random()
            tumbleweeds[rand-1] = true
        }
        return tumbleweeds
    }


}