package com.hks.kr.wifireminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.ui.addTask.AddTaskDialogFragment
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.setUpSnackBar
import com.hks.kr.wifireminder.utils.shortToast
import dagger.hilt.android.AndroidEntryPoint

/**
 * TODO : 재밌는거 생각남 , CATEGORY 리사이클러뷰에 GRADIENT를 넣어두고 스크롤 할떄마다 GRADIENT의 양이 달라지는거지 SCROLL OFFSET에 따라서
 * 그럼 진짜 괜찮을거 같아요!!!
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding by FragmentBindingDelegate()

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var homeTaskAdapter: HomeTaskAdapter

    private lateinit var homeTaskCategoryAdapter: HomeTaskCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater, container, false)
        .also { fragmentHomeBinding -> binding = fragmentHomeBinding }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bindingViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.bindingFragment = this
        homeTaskAdapter =
            HomeTaskAdapter(onItemClicked = { position, task -> onTaskItemClicked(position, task) })
        homeTaskCategoryAdapter = HomeTaskCategoryAdapter(onItemClicked = { position, category ->
            onCategoryItemClicked(
                position,
                category
            )
        })
        configureSnackBar()
        configureTaskList()
        configureCategoryList()
    }

    private fun configureSnackBar() {
        view?.setUpSnackBar(viewLifecycleOwner,viewModel.snackBarText,Snackbar.LENGTH_SHORT)
    }

    private fun configureCategoryList() = binding.homeFragmentTodoCategoryList.let { list ->
        list.adapter = homeTaskCategoryAdapter
    }

    private fun configureTaskList() = binding.homeFragmentTodoList.let { list ->
        list.adapter = homeTaskAdapter
    }

    private fun onTaskItemClicked(position: Int, task: Task) {
        requireContext().shortToast("$position & $task")
    }

    private fun onCategoryItemClicked(position: Int, category: Category) {
        viewModel.fetchTaskByCategory(category.categoryName)
    }

    fun navigateToAddTask() {
        AddTaskDialogFragment().show(childFragmentManager, "TAG")
    }

}
