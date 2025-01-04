package com.example.a25a_hw1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_hw1.R
import com.example.a25a_hw1.databinding.ScoreItemBinding
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.model.Score
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textview.MaterialTextView

class ScoreboardAdapter(private val scores: Map<String, LatLng>) :
    RecyclerView.Adapter<ScoreboardAdapter.ScoreViewHolder>() {

    var itemCallback: Callback_HighScoreItemClicked? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ScoreItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scores.size;
    }

    private fun getItem(position: Int): Score{
        val item = scores.entries.elementAt(position)
        val score = item.key
        return Score(score, item.value.latitude, item.value.longitude)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        with(holder){
            with(getItem(position)){
                binding.sitemLBLScore.text = buildString {
                    append("Score: ")
                    append(score)
                }
            }
        }
    }

    inner class ScoreViewHolder(val binding: ScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.scoreCVData.setOnClickListener{
                    val item = getItem(adapterPosition)
                    itemCallback?.highScoreItemClicked(item.lat, item.lng, item.score.toString())
                }
            }
    }
}