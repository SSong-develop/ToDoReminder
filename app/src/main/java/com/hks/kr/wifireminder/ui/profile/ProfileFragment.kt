package com.hks.kr.wifireminder.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hks.kr.wifireminder.databinding.FragmentProfileBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding by FragmentBindingDelegate()

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentProfileBinding.inflate(inflater, container, false).also { FragmentProfileBinding ->
            binding = FragmentProfileBinding
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

    }
}