package com.hks.kr.wifireminder.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    @ApplicationContext context : Context,
    private val taskRepository: TaskRepository
) : ViewModel() {

}