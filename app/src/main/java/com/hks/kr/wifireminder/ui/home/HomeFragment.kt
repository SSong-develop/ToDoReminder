package com.hks.kr.wifireminder.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.hks.kr.wifireminder.R
import com.hks.kr.wifireminder.databinding.FragmentHomeBinding
import com.hks.kr.wifireminder.domain.entity.Category
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.ui.common.adapter.HomeTaskAdapter
import com.hks.kr.wifireminder.ui.common.adapter.HomeTaskCategoryAdapter
import com.hks.kr.wifireminder.ui.common.viewholders.CategoryViewHolder
import com.hks.kr.wifireminder.ui.common.viewholders.TaskViewHolder
import com.hks.kr.wifireminder.utils.delegate.FragmentBindingDelegate
import com.hks.kr.wifireminder.utils.setUpSnackBar
import com.hks.kr.wifireminder.vo.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(),TaskViewHolder.Delegate, CategoryViewHolder.Delegate {

    private var binding: FragmentHomeBinding by FragmentBindingDelegate()

    private val viewModel: HomeViewModel by viewModels()

    private var _homeTaskAdapter: HomeTaskAdapter? = null
    private val homeTaskAdapter: HomeTaskAdapter?
        get() = _homeTaskAdapter

    private var _homeTaskCategoryAdapter: HomeTaskCategoryAdapter? = null
    private val homeTaskCategoryAdapter: HomeTaskCategoryAdapter?
        get() = _homeTaskCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(layoutInflater, container, false)
        .also { fragmentHomeBinding -> binding = fragmentHomeBinding }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            fragment = this@HomeFragment
        }

        initializeAdapter()
        configureSnackBar()
        configureTaskList()
        configureCategoryList()
    }

    private fun initializeAdapter() {
        _homeTaskAdapter = HomeTaskAdapter(this)
        _homeTaskCategoryAdapter = HomeTaskCategoryAdapter(this)
    }

    private fun configureSnackBar() {
        view?.setUpSnackBar(viewLifecycleOwner, viewModel.snackBarText, Snackbar.LENGTH_SHORT)
    }

    private fun configureCategoryList() = binding.homeFragmentTodoCategoryList.let { list ->
        list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        list.adapter = homeTaskCategoryAdapter

    }

    private fun configureTaskList() = binding.homeFragmentTodoList.let { list ->
        list.adapter = homeTaskAdapter
        LinearSnapHelper().attachToRecyclerView(list)
    }

    override fun onDestroyView() {
        _homeTaskAdapter = null
        _homeTaskCategoryAdapter = null
        super.onDestroyView()
    }

    override fun onCategoryItemClick(view: View, category: Category) {
        viewModel.fetchTasksByCategory(category.categoryName)
    }

    override fun onTaskItemClick(view: View, task: Task) {
        val bundleArgs = Bundle()
        bundleArgs.putParcelable("task", task)

        findNavController().navigate(R.id.action_homeFragment_to_taskDetailFragment, bundleArgs)
    }
}
