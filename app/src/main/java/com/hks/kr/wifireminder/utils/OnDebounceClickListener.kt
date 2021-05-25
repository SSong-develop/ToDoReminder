package com.hks.kr.wifireminder.utils

import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

/**
 * debounce는 특정시간동안 들어온 이벤트를 하나의 이벤트로 묶어서 처리해주는 기능을 의미하는데 이게 과연 그걸 작동할까요??
 */
fun <T> debounce(
    delayMills : Long = 300L,
    scope : CoroutineScope,
    action : (T) -> Unit
) : (T) -> Unit {
    var debounceJob : Job? = null
    return { param : T ->
        if(debounceJob == null) {
            debounceJob = scope.launch {
                action(param)
                delay(delayMills)
                debounceJob = null
            }
        }
    }
}