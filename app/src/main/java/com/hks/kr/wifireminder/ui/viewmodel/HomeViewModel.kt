package com.hks.kr.wifireminder.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.api.local.entity.asDomainTaskEntity
import com.hks.kr.wifireminder.authentication.PrefsStore
import com.hks.kr.wifireminder.domain.entity.TaskEntity
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import com.hks.kr.wifireminder.workers.TaskNotificationWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val taskRepository: TaskRepository,
    private val prefsStore: PrefsStore
) : ViewModel() {

    val taskEntityList = MutableLiveData<List<TaskEntity>>(listOf())

    private val _isAlreadyDoneItBefore = MutableLiveData<Boolean>()
    val isAlreadyDoneItBefore: LiveData<Boolean>
        get() = _isAlreadyDoneItBefore


    init {
        insertTestData()
        doItOnce()
    }

    private fun testShowWifiConnect() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "homeViewModel",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<TaskNotificationWork>(3, TimeUnit.HOURS).build()
        )
    }

    private fun doItOnce(){
        viewModelScope.launch {
            _isAlreadyDoneItBefore.value = prefsStore.isAlreadyWork()
        }
    }

    fun weCallThisCode(){
        viewModelScope.launch {
            prefsStore.workingOnceCode()
        }
    }

    private fun insertTestData() = viewModelScope.launch {
        runCatching {
            taskRepository.insertTask(TaskDTO(1, "[TEST] 밥먹기", 5, 6))
            taskRepository.insertTask(TaskDTO(2, "[TEST] 산책하기", 8, 3))
            taskRepository.insertTask(TaskDTO(3, "[TEST] 운동하기", 12, 10))
            taskRepository.insertTask(TaskDTO(4, "[TEST] 개발하기", 15, 99))
            taskRepository.insertTask(TaskDTO(5, "[TEST] 롤하기", 19, 1))
            taskRepository.insertTask(TaskDTO(6, "[TEST] 밥먹기2", 52, 62))
            taskRepository.insertTask(TaskDTO(7, "[TEST] 산책하기2", 82, 32))
            taskRepository.insertTask(TaskDTO(8, "[TEST] 운동하기2", 122, 102))
            taskRepository.insertTask(TaskDTO(9, "[TEST] 개발하기2", 152, 992))
            taskRepository.insertTask(TaskDTO(10, "[TEST] 롤하기2", 192, 12))
            taskRepository.insertTask(TaskDTO(11, "[TEST] 밥먹기3", 53, 63))
            taskRepository.insertTask(TaskDTO(12, "[TEST] 산책하기3", 83, 33))
            taskRepository.insertTask(TaskDTO(13, "[TEST] 운동하기3", 123, 103))
            taskRepository.insertTask(TaskDTO(14, "[TEST] 개발하기3", 153, 993))
            taskRepository.insertTask(TaskDTO(15, "[TEST] 롤하기3", 193, 13))
        }.onSuccess {
            taskRepository.fetchAllTaskSortByImportance().let {
                taskEntityList.value = it.asDomainTaskEntity()
            }
            testShowWifiConnect()
        }.onFailure {

        }
    }
}