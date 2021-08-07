package com.hks.kr.wifireminder.data.source

import androidx.lifecycle.LiveData
import com.hks.kr.wifireminder.data.Result
import com.hks.kr.wifireminder.data.TaskDTO
import com.hks.kr.wifireminder.domain.entity.Task

interface TasksRepository {

    fun observeTasks(): LiveData<Result<List<Task>>>

    fun observeTask(taskId: String): LiveData<Result<Task>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun getTaskByCategory(categoryTitle : String) : Result<List<Task>>

    suspend fun getTaskByImportance() : Result<List<Task>>

    suspend fun getCategoriesCount() : Int

    suspend fun saveTask(task: TaskDTO)

    suspend fun deleteAllTasks()

    suspend fun deleteTaskByEndDate(endDate: String)

    suspend fun getCategoryCount(categoryTitle : String) : Int
}