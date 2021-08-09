package com.hks.kr.wifireminder.ui.addTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hks.kr.wifireminder.databinding.FragmentAddTaskBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var binding: FragmentAddTaskBinding by FragmentBindingDelegate()

    private val viewModel: AddTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddTaskBinding.inflate(layoutInflater, container, false)
        .also { fragmentAddTaskBinding ->
            binding = fragmentAddTaskBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bindingViewModel = viewModel

        binding.ivPopBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}