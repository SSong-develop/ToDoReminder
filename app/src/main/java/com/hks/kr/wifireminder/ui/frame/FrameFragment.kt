package com.hks.kr.wifireminder.ui.frame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentFrameBinding
import com.hks.kr.wifireminder.utils.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FrameFragment : Fragment() {

    private var binding: FragmentFrameBinding by FragmentBindingDelegate()

    private val viewModel by viewModels<FrameViewModel>()

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
        observeViewModel()
    }

    private fun configureBottomNavigation() = binding.bottomNavigation.run {
        setOnNavigationItemSelectedListener {
            viewModel.onPageSelected(
                when (it.itemId) {
                    R.id.homeFragment -> 0
                    else -> throwUnknownMenuSelectedException()
                }
            )
            true
        }
    }

    private fun observeViewModel() {
        viewModel.pageIdx.observe(viewLifecycleOwner) { pageIdx ->
            selectBottomNavigationMenu(pageIdx)
        }
    }

    private fun selectBottomNavigationMenu(pageIdx: Int?) {
        binding.bottomNavigation.selectedItemId = when (pageIdx) {
            0 -> R.id.homeFragment
            else -> throwUnknownMenuSelectedException()
        }
    }

    private fun throwUnknownMenuSelectedException(): Nothing =
        throw RuntimeException("How dare you...")
}