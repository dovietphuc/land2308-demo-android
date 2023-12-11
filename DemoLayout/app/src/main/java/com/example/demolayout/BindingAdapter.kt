package com.example.demolayout

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun setValue(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}