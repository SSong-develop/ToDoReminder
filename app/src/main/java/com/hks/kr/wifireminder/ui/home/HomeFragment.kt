package com.hks.kr.wifireminder.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.ui.MainActivity
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.setUpSnackBar
import dagger.hilt.android.AndroidEntryPoint

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
        view?.setUpSnackBar(viewLifecycleOwner, viewModel.snackBarText, Snackbar.LENGTH_SHORT)
    }

    private fun configureCategoryList() = binding.homeFragmentTodoCategoryList.let { list ->
        list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        list.adapter = homeTaskCategoryAdapter
        list.scrollToPosition(viewModel.categoryListPosition ?: 0)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position =
                    ((recyclerView.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                viewModel.saveCategoryScrollPosition(position)
            }
        })
    }

    private fun configureTaskList() = binding.homeFragmentTodoList.let { list ->
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = homeTaskAdapter
        LinearSnapHelper().attachToRecyclerView(list)
        list.scrollToPosition(viewModel.taskListPosition ?: 0)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val position =
                    ((recyclerView.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                viewModel.saveTaskScrollPosition(position)
            }
        })
    }

    private fun onTaskItemClicked(position: Int, task: Task) {
        val bundleArgs = Bundle()
        bundleArgs.putParcelable("task", task)

        findNavController().navigate(R.id.action_homeFragment_to_taskDetailFragment, bundleArgs)
    }

    private fun onCategoryItemClicked(position: Int, category: Category) {
        viewModel.getTasksByCategory(category.categoryName)
    }
}
