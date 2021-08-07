package com.hks.kr.wifireminder.ui.addTask

import androidx.lifecycle.ViewModel
import com.hks.kr.wifireminder.data.source.TasksRepository
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskRepository: TasksRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

}