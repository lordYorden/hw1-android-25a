package com.example.a25a_hw1.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_hw1.R
import com.example.a25a_hw1.adapters.ScoreboardAdapter
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.logic.ScoreManger
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class HighScoreFragment : Fragment() {


    private lateinit var highScore_ET_location: TextInputEditText

    private lateinit var highScore_BTN_send: MaterialButton

    private lateinit var highScore_RV_scoreboard: RecyclerView

    var highScoreItemClicked: Callback_HighScoreItemClicked? = null

    private val scores = ScoreManger.getInstance().scores
    private val scoreboardAdapter = ScoreboardAdapter(scores)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        scores[1000] = LatLng(34.0, 42.0)
//        scores[50] = LatLng(32.0, 42.0)
//        scores[80] = LatLng(34.0, 41.0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_high_score, container, false)
        findViews(v)
        initViews(container?.context)
        return v
    }

    private fun initViews(context: Context?) {

        if (context == null){
            return
        }

        scoreboardAdapter.itemCallback = highScoreItemClicked
        highScore_RV_scoreboard.layoutManager = LinearLayoutManager(context)
        highScore_RV_scoreboard.adapter = scoreboardAdapter

        highScore_BTN_send.setOnClickListener { _:View ->
            val coordinates = highScore_ET_location.text?.split(",")
            val lat: Double = coordinates?.get(0)?.toDouble()?: 0.0
            val lon: Double = coordinates?.get(1)?.toDouble()?: 0.0

            itemClicked(lat, lon)
        }
    }

    private fun itemClicked(lat: Double, lon: Double) {
        highScoreItemClicked?.highScoreItemClicked(lat, lon, "test")
    }

    private fun findViews(v: View) {
        highScore_ET_location = v.findViewById(R.id.highScore_ET_location)
        highScore_BTN_send = v.findViewById(R.id.highScore_BTN_send)
        highScore_RV_scoreboard = v.findViewById(R.id.highScore_RV_scoreboard)
    }

    fun updateHighScore(position: Int){
        scoreboardAdapter.notifyItemChanged(position)
    }
}