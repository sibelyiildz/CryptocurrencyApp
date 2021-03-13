package com.sibelyildiz.cryptocurrencyapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sibelyildiz.cryptocurrencyapp.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}

@BindingAdapter("tvBackground")
fun loadBackground(textView: TextView, number: String) {
    val firstIndex = number.substring(0, 1)
    if (firstIndex == "-") {
        DrawableCompat.setTint(
            textView.background,
            ContextCompat.getColor(textView.context, R.color.mexican_red)
        )
    } else {
        DrawableCompat.setTint(
            textView.background,
            ContextCompat.getColor(textView.context, R.color.spring_green)
        )
    }
}
