package com.hks.kr.wifireminder.binding

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.LifecycleCoroutineScope

/**
 * BindingAdapter는 잘 작동하지만 static 메서드 이기 때문에 상태를 저장하거나 close , clear , dispose 와 같은 메소드를 호출하여 리소스를 정리할 수 없다.
 * 이를 DataBindingComponent를 통해서 해결할 수 있다.
 * 게다가 static을 제거하고 동적으로 bindingAdapter를 바인딩 클래스에 포함시킬 수 있다.
 */
class AppBindingComponent(private val scope: LifecycleCoroutineScope) : DataBindingComponent  {
    override fun getAppViewBinding() : AppViewBinding {
        return AppViewBindingImpl(scope)
    }
}