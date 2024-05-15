package com.zarin.shahabzarrin

import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MediaViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {
    private val thumbnail: ImageView = view.findViewById(R.id.preview)
    private val playIcon: ImageView = view.findViewById(R.id.playIcon)
    private val playView: PlayerView = view.findViewById(R.id.mediaVideo)
    private val mediaLayout: ConstraintLayout = view.findViewById(R.id.mediaLayout)
    var exoPlayer: ExoPlayer? = null
    var isApplyFilter = false

    fun bind(mediaItem: MediaItem) {
        playIcon.isVisible = mediaItem.type == MediaType.VIDEO
        if (mediaItem.type == MediaType.VIDEO) {
            val video = getVideoThumbnail(mediaItem.uri)
            thumbnail.setImageBitmap(video)
            mediaLayout.setOnClickListener {
                if (exoPlayer?.isPlaying == true) {
                    exoPlayer?.release()
                    exoPlayer = null
                    playView.isVisible = false
                    playIcon.isVisible = true
                    thumbnail.isVisible = true
                    thumbnail.setImageBitmap(video)
                } else {
                    playIcon.isVisible = false
                    playView.isVisible = true
                    thumbnail.isVisible = false
                    exoPlayer = ExoPlayer.Builder(view.context).build().apply {
                        setMediaItem(androidx.media3.common.MediaItem.fromUri(mediaItem.uri))
                        prepare()
                        playWhenReady = true
                    }
                    playView.player = exoPlayer
                }
            }
        } else {
            Glide.with(thumbnail.context)
                .load(mediaItem.uri)
                .override(300, 300)
                .centerCrop()
                .into(thumbnail)
            mediaLayout.setOnClickListener {
                isApplyFilter = if (!isApplyFilter) {
                    thumbnail.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN)
                    true
                } else {
                    thumbnail.clearColorFilter()
                    false
                }
            }
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