package com.example.a25a_hw1.logic

import com.example.a25a_hw1.model.Obstacle
import com.example.a25a_hw1.model.ObstacleGenerationManager
import com.example.a25a_hw1.utilities.Constants

class GameManager(private val lifeCount: Int = 3, private val numLanes: Int = 5, private val numRows: Int = 5) {
    var score: Int = 0
        private set

    private val generationManager =
        ObstacleGenerationManager(numLanes, SettingsManager.Difficulty.tumbleweedsPerRow, this)

    val tumbleweeds: Array<Array<Obstacle>> = generationManager.generateTumbleweeds(numRows)

    var cowboyIndex: Int = 0
        private set

    var timesHit: Int = 0
        private set

    private val nextRow : Array<Obstacle>
        get() = generationManager.currentRow

    val isGameOver: Boolean
        get() = timesHit == lifeCount

    fun moveRight() {
        if (cowboyIndex+1 < numLanes) {
            cowboyIndex++
        }

    }

    fun heal(){
        if (timesHit > 0)
            timesHit--
    }

    fun collectCoin(){
        score += Constants.GameLogic.COIN_POINTS
    }

    fun hit(){
        timesHit++
    }

    fun moveLeft() {
        if (cowboyIndex-1 >= 0) {
            cowboyIndex--
        }

    }

    fun calcHit(): Boolean {

        val obsInWay: Obstacle = tumbleweeds[tumbleweeds.size-1 - Constants.GameLogic.COWBOY_ROW_END_OFFSET][cowboyIndex]

        if (obsInWay.isVisible) {
            return obsInWay.collisionCallback.onCollision()
        }
        else {
            score += Constants.GameLogic.DODGE_POINTS
        }
        return false
    }

    fun advanceTumbleweeds() {
        for (i in numRows-1 downTo 1) {
            for (j in 0 until numLanes) {
                tumbleweeds[i][j] = tumbleweeds[i-1][j]
            }
        }
        tumbleweeds[0] = nextRow
    }
}