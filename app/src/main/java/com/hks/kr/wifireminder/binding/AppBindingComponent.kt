package com.hks.kr.wifireminder.binding

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.LifecycleCoroutineScope

class AppBindingComponent(private val scope: LifecycleCoroutineScope) : DataBindingComponent  {
    override fun getAppViewBinding() : AppViewBinding {
        return AppViewBindingImpl(scope)
    }
}