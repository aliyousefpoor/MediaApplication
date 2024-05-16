package com.zarin.shahabzarrin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MediaAdapter() :
    RecyclerView.Adapter<MediaViewHolder>() {
    private val items = ArrayList<MediaItem>()
    private var currentItem: Pair<Int, MediaItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_item, parent, false)
        return MediaViewHolder(view) {
            currentItem = it
        }
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

    override fun onViewRecycled(holder: MediaViewHolder) {
        super.onViewRecycled(holder)
        if (holder.bindingAdapterPosition == currentItem?.first) {
            if (currentItem?.second?.type == MediaType.VIDEO) {
                currentItem?.second?.let {
                    holder.isPlayVideo(false, it)
                    currentItem = null
                }
            } else {
                holder.thumbnail.clearColorFilter()
                currentItem = null
            }
        }
    }
}