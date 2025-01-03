package com.example.a25a_hw1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.a25a_hw1.R
import com.example.a25a_hw1.adapters.ScoreboardAdapter
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.logic.ScoreManger
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Objects

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
        initViews(v, container)
        return v
    }

    private fun initViews(v: View, container: ViewGroup?) {

        scoreboardAdapter.itemCallback = highScoreItemClicked
        highScore_RV_scoreboard.layoutManager = LinearLayoutManager(container?.context)
        highScore_RV_scoreboard.adapter = scoreboardAdapter

        highScore_BTN_send.setOnClickListener { v:View ->
            var coordinates = highScore_ET_location.text?.split(",")
            var lat: Double = coordinates?.get(0)?.toDouble()?: 0.0
            var lon: Double = coordinates?.get(1)?.toDouble()?: 0.0

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

    fun updateHighScore(){
        scoreboardAdapter.notifyDataSetChanged()
    }
}