package com.example.a25a_hw1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_hw1.databinding.ScoreItemBinding
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.example.a25a_hw1.model.Score
import com.example.a25a_hw1.utilities.Constants

class ScoreboardAdapter(private val scores: List<Score>) :
    RecyclerView.Adapter<ScoreboardAdapter.ScoreViewHolder>() {

    var itemCallback: Callback_HighScoreItemClicked? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ScoreItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scores.size.coerceAtMost(Constants.ScoreDisplay.NUM_OF_SCORES_DISPLAYED)
    }

    private fun getItem(position: Int): Score{
        return scores[position]
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