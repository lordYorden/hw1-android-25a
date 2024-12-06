package com.example.a25a_l02_03.logic

class GameManager(private val lifeCount: Int = 3, private val numLanes: Int = 3) {
    var score: Int = 0
        private set

    //private val allCountries: List<Country> = DataManager.getAllCountries()

    var cowboyIndex: Int = 0
        private set

    var timesHit: Int = 0
        private set

//    val currentCountry: Country
//        get() = allCountries[currentIndex]
//
//    val isGameEnded: Boolean
//        get() = currentIndex == allCountries.size

    val isGameOver: Boolean
        get() = timesHit == lifeCount

    fun moveRight() {
        if (cowboyIndex+1 < numLanes) {
            cowboyIndex++
        }
    }

    fun moveLeft() {
        if (cowboyIndex-1 >= 0) {
            cowboyIndex--
        }
    }

    fun checkAnswer(expected: Boolean) {
        //check answer and add score
//        if (expected == currentCountry.canEnter)
//            score += Constants.GameLogic.ANSWER_POINTS;

        //else: add 1 to wrong answers
//        else
//            wrongAnswers++

        //go to next index
        cowboyIndex++
    }
}