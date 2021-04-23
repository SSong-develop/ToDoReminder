package com.hks.kr.wifireminder.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.ui.viewmodel.HomeViewModel
import com.hks.kr.wifireminder.utils.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by FragmentBindingDelegate()

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater, container, false)
        .also { fragmentHomeBinding -> binding = fragmentHomeBinding }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bindingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
