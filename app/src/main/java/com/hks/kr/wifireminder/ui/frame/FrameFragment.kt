package com.hks.kr.wifireminder.ui.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentFrameBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FrameFragment : Fragment() {

    private var binding: FragmentFrameBinding by FragmentBindingDelegate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFrameBinding.inflate(inflater, container, false)
        .also { FragmentFrameBinding ->
            binding = FragmentFrameBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        configureBottomNavigation()
    }

    private fun configureBottomNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}
