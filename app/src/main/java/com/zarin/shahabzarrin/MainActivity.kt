package com.zarin.shahabzarrin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val adapter: MediaAdapter by lazy {
        MediaAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.mediaList)
        setupAdapter()
    }

    private fun setupAdapter() {
        recyclerView.layoutManager = GridLayoutManager(baseContext, 4)
        recyclerView.adapter = adapter
    }
}