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

    init {
        insertTestData()
    }
    private fun testShowWifiConnect() {
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

    private fun insertTestData() = viewModelScope.launch {
        runCatching {
            taskRepository.insertTask(TaskDTO(1, "[TEST] 밥먹기", 5, 6))
            taskRepository.insertTask(TaskDTO(2, "[TEST] 산책하기", 8, 3))
            taskRepository.insertTask(TaskDTO(3, "[TEST] 운동하기", 12, 10))
            taskRepository.insertTask(TaskDTO(4, "[TEST] 개발하기", 15, 99))
            taskRepository.insertTask(TaskDTO(5, "[TEST] 롤하기", 19, 1))
        }.onSuccess {
            testShowWifiConnect()
        }.onFailure {

        }
    }

}