package com.hks.kr.wifireminder.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.flow.StateFlow

interface AppViewBinding {
    @BindingAdapter("textStateFlow")
    fun setText(view: TextView, stateFlow: StateFlow<String>)

    @BindingAdapter("setOnCoroutineDebounce")
    fun setOnCoroutineDebounce(view: View, listener: View.OnClickListener)
}