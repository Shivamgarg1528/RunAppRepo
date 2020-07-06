package com.st.runningapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.st.runningapp.db.Run

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    private var runs = mutableListOf<Run>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return runs.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = runs[position]
        holder.ivRunImage.setImageBitmap(run.img)
    }

    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRunImage: ImageView = itemView.findViewById(R.id.ivRunImage)
    }

    fun submitList(listOfRuns: List<Run>) {
        runs.clear()
        runs.addAll(listOfRuns)
        notifyDataSetChanged()
    }
}