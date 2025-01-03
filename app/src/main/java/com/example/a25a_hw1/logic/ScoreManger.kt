package com.example.a25a_hw1.logic

import android.content.Context
import com.example.a25a_hw1.utilities.Constants
import com.google.android.gms.maps.model.LatLng
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
            return instance ?: synchronized(this) {
                instance ?: ScoreManger(context).also { instance = it }
            }
        }
    }

    var scores: MutableMap<String, LatLng> = mutableMapOf()

    fun save(){

        val prefy: Prefy = Prefy.getInstance();

        prefy.putArray(Constants.SP_keys.SCOREBOARD_KEY, scores.keys.toTypedArray())
        for (score in scores){
            prefy.putObject(score.key, score.value)
        }
    }

    fun load(){
        val prefy: Prefy = Prefy.getInstance();

        val default: Array<String> = arrayOf()
//        scores.putAll(Prefy.getInstance().getHashMap(Constants.SP_keys.SCOREBOARD_KEY, HashMap()))
        val scoreKeys = prefy.getArray(Constants.SP_keys.SCOREBOARD_KEY, default, Array<String>::class.java)

        for (score in scoreKeys){
            val key: LatLng? = prefy.getObject(score, null, LatLng::class.java)
            key?.let {
                scores.put(score, key)
            }
        }
    }
}