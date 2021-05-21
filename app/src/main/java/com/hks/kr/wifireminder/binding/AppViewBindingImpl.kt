package com.hks.kr.wifireminder.binding

import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class AppViewBindingImpl(private val scope: LifecycleCoroutineScope) : AppViewBinding {
    override fun setText(view: TextView, stateFlow: StateFlow<String>) {
        scope.launchWhenStarted {
            stateFlow.collect {
                view.text = it
            }
        }
    }
}