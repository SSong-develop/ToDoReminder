package com.hks.kr.wifireminder.ui.certification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentCertificationBinding
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CertificationFragment : Fragment() {

    private var binding : FragmentCertificationBinding by FragmentBindingDelegate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCertificationBinding.inflate(inflater,container,false).also { FragmentCertificationBinding ->
        binding = FragmentCertificationBinding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}