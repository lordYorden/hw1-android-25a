package com.example.a25a_hw1.logic

import android.content.Context
import com.example.a25a_hw1.model.Score
import com.example.a25a_hw1.utilities.Constants
import com.paz.prefy_lib.Prefy

class ScoreManger private constructor(context: Context){

    companion object {

        @Volatile
        private var instance: ScoreManger? = null

        fun getInstance(): ScoreManger {
            return instance
                ?: throw IllegalStateException("SignalManager must be initialized by calling init(context) before use")
        }

        fun init(context: Context): ScoreManger {
            Prefy.init(context, false)

            return instance ?: synchronized(this) {
                instance ?: ScoreManger(context).also { instance = it }
            }
        }
    }

    var scores: MutableList<Score> = mutableListOf()

    fun save(){

        val prefy: Prefy = Prefy.getInstance()
        prefy.putArray(Constants.SP_keys.SCOREBOARD_KEY, scores.toTypedArray())
    }

    fun sortScores(){
        scores.sortByDescending { s -> s.score }
    }

    fun load(){
        val prefy: Prefy = Prefy.getInstance()
        val default: Array<Score> = arrayOf()
        val scoresArr = prefy.getArray(Constants.SP_keys.SCOREBOARD_KEY, default, Array<Score>::class.java)
        scores.addAll(scoresArr)
    }
}