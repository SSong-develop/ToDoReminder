package com.hks.kr.wifireminder.binding

import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.ui.addTask.AddTaskViewModel
import com.hks.kr.wifireminder.utils.debugE
import com.hks.kr.wifireminder.view.customizableSpinner.CustomizableSpinner
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:img_path_src")
    fun setIconImage(imageView : ImageView,imgPath : Int) {
        imageView.setImageDrawable(imageView.context.getDrawable(imgPath))
    }

    @JvmStatic
    @BindingAdapter("app:background_color")
    fun setCategoryBackgroundColor(imageView: ImageView,colorCode : String) {
        val color = Color.parseColor(colorCode)
        imageView.setBackgroundColor(color)
    }
}