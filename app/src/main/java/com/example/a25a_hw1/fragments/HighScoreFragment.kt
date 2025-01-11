package com.example.a25a_hw1.fragments

import android.annotation.SuppressLint
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
import com.google.android.material.textview.MaterialTextView

class HighScoreFragment : Fragment() {


    private lateinit var highScore_LBL_empty: MaterialTextView
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

    fun setEmptyMsg(visibility: Int){
        highScore_LBL_empty.visibility = visibility
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
    }

    private fun findViews(v: View) {
        highScore_LBL_empty = v.findViewById(R.id.highScore_LBL_empty)
        highScore_RV_scoreboard = v.findViewById(R.id.highScore_RV_scoreboard)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHighScore() {
        scoreboardAdapter.notifyDataSetChanged()
    }
}