package com.example.a25a_hw1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a25a_hw1.R
import com.example.a25a_hw1.interfaces.Callback_HighScoreItemClicked
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textview.MaterialTextView

class ScoreboardAdapter (private val scores: Map<String, LatLng>) :  RecyclerView.Adapter<ScoreboardAdapter.ViewHolder>() {

    var itemCallback: Callback_HighScoreItemClicked? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Define click listener for the ViewHolder's View
        var sitem_LBL_score: MaterialTextView = view.findViewById(R.id.sitem_LBL_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scores.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = scores.entries.elementAt(position)
        val score = item.key
        val scoreStr = buildString {
            append("Score: ")
            append(score)
        }
        holder.sitem_LBL_score.text = scoreStr

        holder.itemView.setOnClickListener{ v ->
            itemCallback?.highScoreItemClicked(item.value.latitude, item.value.longitude, scoreStr)
        }
    }
}

/*
class CustomAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    */
/**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     *//*

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}*/
