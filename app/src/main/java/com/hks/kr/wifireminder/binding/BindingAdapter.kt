package com.hks.kr.wifireminder.binding

import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.ui.addTask.AddTaskViewModel
import com.hks.kr.wifireminder.ui.home.HomeViewModel
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

    /**
     * TODO : Test 요망
     */
    @JvmStatic
    @BindingAdapter("task_scroll_listener")
    fun setTaskScrollListener(recyclerView: RecyclerView , viewModel : HomeViewModel){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position = ((recyclerView.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                viewModel.saveTaskScrollPosition(position)
            }
        })
    }

    /**
     * TODO : Test 요망
     */
    @JvmStatic
    @BindingAdapter("category_scroll_listener")
    fun setCategoryScrollListener(recyclerView : RecyclerView , viewModel : HomeViewModel){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position = ((recyclerView.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                viewModel.saveCategoryScrollPosition(position)
            }
        })
    }
}