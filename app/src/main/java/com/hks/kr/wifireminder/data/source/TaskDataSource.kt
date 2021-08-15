package com.hks.kr.wifireminder.data.source

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.domain.entity.Task
import kotlinx.coroutines.flow.Flow

/**
 * TODO : 전부 Result로 감싸줄거임 안되면 될때 까지 할거야
 */
interface TaskDataSource {

    fun observeTask(taskId: String): LiveData<Result<Task>>

    fun observeTasks(): Flow<List<Task>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>>

    suspend fun getTaskByImportance(): Result<List<Task>>

    suspend fun saveTask(task: TaskDTO)

    suspend fun deleteAllTasks()

    suspend fun deleteTaskByEndDate(endDate: String)

    suspend fun getCategoryCount(categoryTitle: String): Int
}