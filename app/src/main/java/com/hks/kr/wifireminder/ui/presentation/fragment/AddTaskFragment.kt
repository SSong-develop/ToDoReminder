package com.hks.kr.wifireminder.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hks.kr.wifireminder.databinding.FragmentAddTaskBinding
import com.hks.kr.wifireminder.ui.viewmodel.HomeViewModel
import com.hks.kr.wifireminder.utils.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var binding: FragmentAddTaskBinding by FragmentBindingDelegate()
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        .also { fragmentAddTaskBinding -> binding = fragmentAddTaskBinding }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bindingViewModel = viewModel
    }
}