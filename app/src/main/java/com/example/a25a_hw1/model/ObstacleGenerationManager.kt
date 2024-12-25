package com.example.a25a_hw1.model

import com.example.a25a_hw1.R
import com.example.a25a_hw1.interfaces.CollisionCallback
import com.example.a25a_hw1.logic.GameManager
import com.example.a25a_hw1.utilities.Constants

class ObstacleGenerationManager(private val numLanes: Int, private val tumbleweedsPerRow: Int = 1, private val gameManager: GameManager?) {

    private val HEART_RES = R.drawable.heart
    private val COIN_RES = R.drawable.coin
    private val WEED_RES = R.drawable.tumbleweed

    private val nullObstacle = Obstacle(0, false, "null", object: CollisionCallback{
        override fun onCollision(): Boolean {
            //do nothing
            return false
        }
    })

    val currentRow: Array<Obstacle>
        get() = generateTumbleweedsRow()

    fun generateTumbleweeds(numRows: Int): Array<Array<Obstacle>> {
        val obstacles: Array<Array<Obstacle>> = Array(numRows) {Array(numLanes) {nullObstacle}}
        for (i in 0 until numRows-Constants.GameLogic.TUMBLEWEEDS_STARTING_OFFSET) {
            obstacles[i] = generateTumbleweedsRow()
        }
        return obstacles
    }

    private fun generateTumbleweedsRow(): Array<Obstacle> {
        val obstacles: Array<Obstacle> = Array(numLanes) {nullObstacle}

        for (i in 1..tumbleweedsPerRow) {
            val rand = (1..numLanes).random()

            val randType = (1..7).random()

            val obs: Obstacle = when(randType){
                1 -> generateHeart()
                2 -> generateCoin()
                else -> generateWeed()
            }

            obstacles[rand-1] = obs
        }
        return obstacles
    }

    private fun generateHeart(): Obstacle{
        return Obstacle(
            HEART_RES,
            true,
            "heart",
            object: CollisionCallback{
                override fun onCollision():Boolean {
                    gameManager?.heal()
                    return false
                }
            }
        )
    }

    private fun generateCoin(): Obstacle{
        return Obstacle(
            COIN_RES,
            true,
            "coin",
            object: CollisionCallback{
                override fun onCollision(): Boolean {
                    gameManager?.collectCoin()
                    return false
                }
            }
        )
    }

    private fun generateWeed(): Obstacle{
        return Obstacle(
            WEED_RES,
            true,
            "weed",
            object: CollisionCallback{
                override fun onCollision(): Boolean {
                    gameManager?.hit()
                    return true
                }
            }
        )
    }



}