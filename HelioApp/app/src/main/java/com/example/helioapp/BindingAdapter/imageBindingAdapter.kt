package com.example.helioapp.BindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun setImageResourceAdapter(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}