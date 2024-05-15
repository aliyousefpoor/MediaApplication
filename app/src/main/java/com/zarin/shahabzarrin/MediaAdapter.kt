package com.zarin.shahabzarrin

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MediaAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val items = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}