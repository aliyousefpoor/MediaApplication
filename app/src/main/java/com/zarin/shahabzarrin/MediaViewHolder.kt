package com.zarin.shahabzarrin

import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MediaViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {
    private val thumbnail: ImageView = view.findViewById(R.id.preview)
    private val playIcon: ImageView = view.findViewById(R.id.playIcon)
    private val videoView: VideoView = view.findViewById(R.id.mediaVideo)
    private val mediaLayout: ConstraintLayout = view.findViewById(R.id.mediaLayout)
    fun bind(mediaItem: MediaItem) {
        playIcon.isVisible = mediaItem.type == MediaType.VIDEO
        if (mediaItem.type == MediaType.VIDEO) {
            val video = getVideoThumbnail(mediaItem.uri)
            thumbnail.setImageBitmap(video)
            mediaLayout.setOnClickListener {
                if (videoView.isPlaying) {
                    videoView.pause()
                    videoView.isVisible = false
                    playIcon.isVisible = true
                    thumbnail.isVisible = true
                    thumbnail.setImageBitmap(video)
                } else {
                    playIcon.isVisible = false
                    videoView.isVisible = true
                    thumbnail.isVisible = false
                    videoView.setVideoURI(mediaItem.uri)
                    videoView.start()
                }
            }
        } else {
            Glide.with(thumbnail.context)
                .load(mediaItem.uri)
                .override(300, 300) // Resize image to reduce memory usage
                .centerCrop()
                .into(thumbnail)
        }
    }

    private fun getVideoThumbnail(uri: Uri): Bitmap? {
        val videoId = ContentUris.parseId(uri)
        val contentResolver = view.context.contentResolver
        return MediaStore.Video.Thumbnails.getThumbnail(
            contentResolver,
            videoId,
            MediaStore.Video.Thumbnails.MINI_KIND,
            null
        )
    }
}