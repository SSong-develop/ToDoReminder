package com.hks.kr.wifireminder.ui.addTask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hks.kr.wifireminder.databinding.FragmentAddTaskBinding
import com.hks.kr.wifireminder.ui.home.HomeViewModel
import com.hks.kr.wifireminder.utils.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.resizeDialogSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskDialogFragment : DialogFragment() {

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

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        resizeDialogSize(0.875f,0.542f)
    }
}