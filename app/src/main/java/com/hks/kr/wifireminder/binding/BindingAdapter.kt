package com.hks.kr.wifireminder.binding

import android.graphics.Color
import android.widget.ImageView
import android.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.hks.kr.wifireminder.ui.home.HomeViewModel

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:img_path_src")
    fun setIconImage(imageView: ImageView, imgPath: Int) {
        imageView.setImageDrawable(imageView.context.getDrawable(imgPath))
    }

    @JvmStatic
    @BindingAdapter("app:background_color")
    fun setCategoryBackgroundColor(imageView: ImageView, colorCode: String) {
        val color = Color.parseColor(colorCode)
        imageView.setBackgroundColor(color)
    }

}