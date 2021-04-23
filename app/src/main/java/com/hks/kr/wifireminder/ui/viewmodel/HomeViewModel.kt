package com.hks.kr.wifireminder.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

}