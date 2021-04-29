package com.hks.kr.wifireminder.ui.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.domain.entity.TaskEntity
import com.hks.kr.wifireminder.ui.presentation.adapter.HomeTaskAdapter
import com.hks.kr.wifireminder.ui.viewmodel.HomeViewModel
import com.hks.kr.wifireminder.utils.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.shortToast
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
        configureList()
    }

    private fun configureList() = binding.homeFragmentTodoList.let { list ->
        list.adapter = HomeTaskAdapter(onItemClicked = { position, task ->
            onItemClicked(position, task)
        })
        list.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun onItemClicked(position: Int, task: TaskEntity) {
        requireContext().shortToast("$position & $task")
    }

}
