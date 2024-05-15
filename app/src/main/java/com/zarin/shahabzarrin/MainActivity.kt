package com.zarin.shahabzarrin

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val mediaList = mutableListOf<MediaItem>()
    private val adapter: MediaAdapter by lazy {
        MediaAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.mediaList)
        setupAdapter()
        checkStoragePermission()
    }

    private fun checkStoragePermission() {
        val videoPermission = Manifest.permission.READ_MEDIA_VIDEO
        val imagePermission = Manifest.permission.READ_MEDIA_IMAGES

        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                imagePermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(imagePermission)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                videoPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(videoPermission)
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            loadMedia()
        }
    }

    private fun loadMedia() {
        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val mediaItems = mutableListOf<MediaItem>()

        val projection = arrayOf(MediaStore.MediaColumns._ID, MediaStore.MediaColumns.MIME_TYPE)
        val imagesCursor = contentResolver.query(imageUri, projection, null, null, null)
        val videosCursor = contentResolver.query(videoUri, projection, null, null, null)

        imagesCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = Uri.withAppendedPath(imageUri, id.toString())
                val mediaItem = MediaItem(uri, MediaType.IMAGE)
                mediaItems.add(mediaItem)
            }
        }

        videosCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = Uri.withAppendedPath(videoUri, id.toString())
                val mediaItem = MediaItem(uri, MediaType.VIDEO)
                mediaItems.add(mediaItem)
            }
        }
        mediaList.addAll(mediaItems)
        adapter.addItems(mediaList)
    }

    private fun setupAdapter() {
        recyclerView.layoutManager = GridLayoutManager(baseContext, 4)
        recyclerView.adapter = adapter
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val readImagesGranted = permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
        val readVideosGranted = permissions[Manifest.permission.READ_MEDIA_VIDEO] ?: false

        if (readImagesGranted && readVideosGranted) {
            loadMedia()
        } else {
            Toast.makeText(this, "اجازه دسترسی را نداده اید.", Toast.LENGTH_SHORT).show()
        }
    }
}