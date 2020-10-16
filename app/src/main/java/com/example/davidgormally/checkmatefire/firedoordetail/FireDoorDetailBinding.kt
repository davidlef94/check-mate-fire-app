package com.example.davidgormally.checkmatefire.firedoordetail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.davidgormally.checkmatefire.database.entity.Media

@BindingAdapter("app:mediaItems")
fun setMediaItems(listView: RecyclerView, mediaItems: List<Media>?) {
    mediaItems?.let {
        (listView.adapter as FireDoorDetailAdapter).submitList(mediaItems)
    }
}

@BindingAdapter("app:mediaImage")
fun loadImage(view: ImageView, path: String) {
    Glide.with(view.context).load(path).centerCrop().into(view)
}