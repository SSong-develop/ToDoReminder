package com.hks.kr.wifireminder.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentTaskDetailBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {

    private var binding : FragmentTaskDetailBinding by FragmentBindingDelegate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTaskDetailBinding.inflate(inflater,container,false).also { FragmentTaskDetailBinding ->
        binding = FragmentTaskDetailBinding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}