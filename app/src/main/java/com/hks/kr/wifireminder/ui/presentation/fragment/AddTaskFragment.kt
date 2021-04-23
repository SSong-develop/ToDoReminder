package com.hks.kr.wifireminder.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAddTaskBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_task, container, false)

        return binding.root
    }
}