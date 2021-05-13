package com.hks.kr.wifireminder.utils

import android.view.View
import androidx.databinding.BindingAdapter

typealias OnClickListener = (View) -> Unit

class OnDebounceClickListener(private val listener: OnClickListener) : View.OnClickListener {
    override fun onClick(v: View?) {
        val now = System.currentTimeMillis()
        if (now < lastTime + INTERVAL) return
        lastTime = now
        v?.run(listener)
    }

    companion object {
        private const val INTERVAL: Long = 200L
        private var lastTime: Long = 0
    }
}

@BindingAdapter("android:debounceClick")
infix fun View.setOnDebounceClickListener(listener: View.OnClickListener?) {
    if (listener == null)
        this.setOnClickListener(null)
    else {
        this.setOnClickListener(OnDebounceClickListener {
            listener.onClick(it)
        })
    }
}