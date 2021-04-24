package com.hks.kr.wifireminder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app : Application,
    private val taskRepository: TaskRepository
) : ViewModel() {

}