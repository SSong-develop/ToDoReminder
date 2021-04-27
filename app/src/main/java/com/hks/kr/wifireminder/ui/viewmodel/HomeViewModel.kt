package com.hks.kr.wifireminder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import com.hks.kr.wifireminder.workers.TaskNotificationWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val taskRepository: TaskRepository
) : ViewModel() {

    internal fun testShowWifiConnect() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // PeriodWorkRequest로 변경해서 3시간마다 1번씩 할일들을 보여주는 푸시알림을 보내준다.
        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "homeViewModel",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<TaskNotificationWork>(15, TimeUnit.MINUTES).build()
        )
    }

    fun insertTestData() = viewModelScope.launch {
        runCatching {
            taskRepository.insertTask(TaskDTO(0, "test1", 3, 1))
            taskRepository.insertTask(TaskDTO(1, "test2", 5, 6))
            taskRepository.insertTask(TaskDTO(2, "test3", 8, 3))
            taskRepository.insertTask(TaskDTO(3, "test4", 11, 2))
        }.onSuccess {

        }.onFailure {

        }
    }

}