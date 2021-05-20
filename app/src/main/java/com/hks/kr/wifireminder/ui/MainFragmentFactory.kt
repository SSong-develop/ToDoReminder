package com.hks.kr.wifireminder.ui

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.hks.kr.wifireminder.ui.frame.FrameFragment
import com.hks.kr.wifireminder.ui.home.HomeFragment
import com.hks.kr.wifireminder.ui.profile.ProfileFragment

/**
 * Fragment에 생성자에 parameter가 필요할 경우 Factory Pattern을 사용해서 fragment를 넘겨준다.
 */
class MainFragmentFactory(activity : Activity) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(loadFragmentClass(classLoader, className)){
            FrameFragment::class.java -> FrameFragment()
            HomeFragment::class.java -> HomeFragment()
            ProfileFragment::class.java -> ProfileFragment()
            else -> super.instantiate(classLoader, className)
        }
    }

    companion object {
        fun getInstance(activity : Activity) : MainFragmentFactory = MainFragmentFactory(activity)
    }
}