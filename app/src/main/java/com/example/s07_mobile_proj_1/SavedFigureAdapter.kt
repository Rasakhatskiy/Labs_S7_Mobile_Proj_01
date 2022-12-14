package com.example.s07_mobile_proj_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s07_mobile_proj_1.databinding.SavedFigureItemBinding

class SavedFigureAdapter(var listener: Listener): RecyclerView.Adapter<SavedFigureAdapter.NameHolder>() {

    val savedFigureList = ArrayList<SavedFigure>()

    class NameHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = SavedFigureItemBinding.bind(item)
        fun bind(savedFigure: SavedFigure, listener: Listener) {
            binding.tvTitle.text = savedFigure.name

            itemView.setOnClickListener {
                listener.onClick(savedFigure)
            }
        }
    }

    fun addSavedFigure(savedFigure: SavedFigure) {
        savedFigureList.add(savedFigure)
        notifyItemInserted(savedFigureList.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_figure_item, parent, false)
        return NameHolder(view)
    }

    override fun onBindViewHolder(holder: NameHolder, position: Int) {
        holder.bind(savedFigureList[position], listener)
    }

    override fun getItemCount(): Int {
        return savedFigureList.size
    }

    interface Listener {
        fun onClick(savedFigure: SavedFigure) {

        }
    }

}
