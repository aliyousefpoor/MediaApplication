package com.zarin.shahabzarrin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MediaAdapter() :
    RecyclerView.Adapter<MediaViewHolder>() {
    private val items = ArrayList<MediaItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
        return MediaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(mediaItems: List<MediaItem>) {
        items.clear()
        items.addAll(mediaItems)
        notifyDataSetChanged()
    }
}