package com.hks.kr.wifireminder.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

/**
 * KeepStateNavigator에 대해서 좀 더 알아볼 필요가 있음
 */

@Navigator.Name("keep_state_fragment")
class KeepStateNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager, // Should pass childFragmentManager
    private val containerId: Int
) : FragmentNavigator(context, fragmentManager, containerId) {
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString()
        val transaction = fragmentManager.beginTransaction()

        var initialNavigate = false
        val currentFragment = fragmentManager.primaryNavigationFragment
        if(currentFragment != null){
            transaction.detach(currentFragment)
        } else {
            initialNavigate = true
        }

        var fragment = fragmentManager.findFragmentByTag(tag)
        if(fragment == null){
            val className = destination.className
            fragment = fragmentManager.fragmentFactory.instantiate(context.classLoader,className)
            transaction.add(containerId,fragment,tag)
        } else {
            transaction.attach(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commitNow()

        return if(initialNavigate){
            destination
        } else {
            null
        }
    }
}