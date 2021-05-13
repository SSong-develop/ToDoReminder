package com.hks.kr.wifireminder.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.hks.kr.wifireminder.api.local.entity.CategoryDTO
import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.api.local.entity.asDomainTaskEntity
import com.hks.kr.wifireminder.domain.entity.CategoryEntity
import com.hks.kr.wifireminder.domain.entity.TaskEntity
import com.hks.kr.wifireminder.domain.repository.CategoryRepository
import com.hks.kr.wifireminder.domain.repository.TaskRepository
import com.hks.kr.wifireminder.workers.TaskNotificationWork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val taskEntityList = MutableLiveData<List<TaskEntity>>(listOf())

    val taskCategoryList = MutableLiveData<List<CategoryEntity>>(listOf())

    val allTaskCount = MutableLiveData<Int>()

    init {
        insertTestData()
        insertTestCategoryData()
        getAllTaskCount()
    }

    private fun testShowWifiConnect() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "homeViewModel",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<TaskNotificationWork>(6, TimeUnit.HOURS).build()
        )
    }

    private fun insertTestData() = viewModelScope.launch {
        runCatching {
            taskRepository.insertTask(TaskDTO(1, "[TEST] 밥먹기", "test1",5, 6))
            taskRepository.insertTask(TaskDTO(2, "[TEST] 산책하기","test2", 8, 3))
            taskRepository.insertTask(TaskDTO(3, "[TEST] 운동하기","test3", 12, 10))
            taskRepository.insertTask(TaskDTO(4, "[TEST] 개발하기","test4", 15, 99))
            taskRepository.insertTask(TaskDTO(5, "[TEST] 롤하기","test1", 19, 1))
            taskRepository.insertTask(TaskDTO(6, "[TEST] 밥먹기2","test2", 52, 62))
            taskRepository.insertTask(TaskDTO(7, "[TEST] 산책하기2","test3", 82, 32))
            taskRepository.insertTask(TaskDTO(8, "[TEST] 운동하기2","test4", 122, 102))
            taskRepository.insertTask(TaskDTO(9, "[TEST] 개발하기2","test1", 152, 992))
            taskRepository.insertTask(TaskDTO(10, "[TEST] 롤하기2","test2", 192, 12))
            taskRepository.insertTask(TaskDTO(11, "[TEST] 밥먹기3","test3", 53, 63))
            taskRepository.insertTask(TaskDTO(12, "[TEST] 산책하기3","test4", 83, 33))
            taskRepository.insertTask(TaskDTO(13, "[TEST] 운동하기3","test1", 123, 103))
            taskRepository.insertTask(TaskDTO(14, "[TEST] 개발하기3","test2", 153, 993))
            taskRepository.insertTask(TaskDTO(15, "[TEST] 롤하기3","test3", 193, 13))
        }.onSuccess {
            taskRepository.fetchAllTaskSortByImportance().let {
                taskEntityList.value = it
            }
            testShowWifiConnect()
        }.onFailure {
            // reTrial or just throw error
        }
    }

    private fun insertTestCategoryData() = viewModelScope.launch {
        runCatching {
            categoryRepository.insertCategory(CategoryDTO(1,"test1",taskRepository.getCategoryCount("test1")))
            categoryRepository.insertCategory(CategoryDTO(2,"test2",taskRepository.getCategoryCount("test2")))
            categoryRepository.insertCategory(CategoryDTO(3,"test3",taskRepository.getCategoryCount("test3")))
            categoryRepository.insertCategory(CategoryDTO(4,"test4",taskRepository.getCategoryCount("test4")))
        }.onSuccess {
            categoryRepository.getAllCategory().let {
                taskCategoryList.value = it
            }
        }.onFailure {
            // reTrial or just throw error
        }
    }

    fun fetchTaskByCategory(categoryName : String) = viewModelScope.launch {
        runCatching {
            taskRepository.fetchTaskByCategory(categoryName)
        }.onSuccess {
            taskEntityList.value = it
        }.onFailure {
            // reTrial or just throw error
        }
    }

    fun fetchAllTask() = viewModelScope.launch {
        runCatching {
            taskRepository.fetchAllTaskSortByImportance()
        }.onSuccess {
            taskEntityList.value = it
        }.onFailure {
            // reTrial or just throw error
        }
    }

    private fun getAllTaskCount() = viewModelScope.launch {
        runCatching {
            taskRepository.getAllTaskCount()
        }.onSuccess {
            allTaskCount.value = it
        }.onFailure {
            // reTrial or just throw error
        }
    }
}