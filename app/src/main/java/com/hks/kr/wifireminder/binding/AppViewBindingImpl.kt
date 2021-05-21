package com.hks.kr.wifireminder.binding

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class AppViewBindingImpl(private val scope: LifecycleCoroutineScope) : AppViewBinding , LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun setText(view: TextView, stateFlow: StateFlow<String>) {
        scope.launchWhenStarted {
            stateFlow.collect {
                view.text = it
            }
        }
    }
}