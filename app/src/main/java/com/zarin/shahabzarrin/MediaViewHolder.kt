package com.zarin.shahabzarrin

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MediaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val thumbnail: ImageView = view.findViewById(R.id.preview)
    fun bind(mediaItem: MediaItem) {
        thumbnail.setImageURI(mediaItem.uri)
    }
}