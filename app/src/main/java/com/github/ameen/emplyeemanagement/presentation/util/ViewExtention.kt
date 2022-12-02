package com.github.ameen.emplyeemanagement.presentation.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun ImageView.loadEmployeeImage(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .into(this)
}