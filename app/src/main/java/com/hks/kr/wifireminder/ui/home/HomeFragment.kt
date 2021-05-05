package com.hks.kr.wifireminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.domain.entity.TaskCategoryEntity
import com.hks.kr.wifireminder.domain.entity.TaskEntity
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
        configureTaskList()
        configureCategoryList()
    }

    private fun configureCategoryList() = binding.homeFragmentTodoCategoryList.let { list ->
        list.adapter = HomeTaskCategoryAdapter(onItemClicked = { position, category ->
            onCategoryItemClicked(position, category)
        })
    }

    private fun configureTaskList() = binding.homeFragmentTodoList.let { list ->
        list.adapter = HomeTaskAdapter(onItemClicked = { position, task ->
            onTaskItemClicked(position, task)
        })
    }

    private fun onTaskItemClicked(position: Int, task: TaskEntity) {
        requireContext().shortToast("$position & $task")
    }

    private fun onCategoryItemClicked(position: Int, category: TaskCategoryEntity) {
        requireContext().shortToast("$position & $category")
    }

}
