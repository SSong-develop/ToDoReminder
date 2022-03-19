package com.hks.kr.wifireminder.domain.repository

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow

/**
 * TODO : 전부 Result로 감싸줄거임 안되면 될때 까지 할거야
 */
interface TasksRepository {

    fun observeTasks(): Flow<List<Task>>

    fun observeTask(taskId: String): LiveData<com.hks.kr.wifireminder.vo.Result<Task>>

    fun observeTaskByLiveData() : LiveData<com.hks.kr.wifireminder.vo.Result<List<Task>>>

    suspend fun getTasks(): com.hks.kr.wifireminder.vo.Result<List<Task>>

    suspend fun getTask(taskId: String): com.hks.kr.wifireminder.vo.Result<Task>

    suspend fun getTaskByCategory(categoryTitle: String): com.hks.kr.wifireminder.vo.Result<List<Task>>

    suspend fun getTaskByImportance(): Result<List<Task>>

    suspend fun saveTask(task: TaskDTO)

    suspend fun deleteAllTasks()

    suspend fun deleteTaskByEndDate(endDate: String)

    suspend fun getCategoryCount(categoryTitle: String): Int
}