package com.example.carzilla.New.newwork.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract  class GenricAdapter <T>(var items: ArrayList<T>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract fun configure(
            item: T,
            holder: ViewHolder,
            position: Int
    )

    fun update(items: ArrayList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        configure(items[position], holder as ViewHolder,position)
    }

}

class ViewHolder(view: View): RecyclerView.ViewHolder(view)