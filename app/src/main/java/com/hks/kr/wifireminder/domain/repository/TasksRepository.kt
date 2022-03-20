package com.hks.kr.wifireminder.domain.repository

import com.hks.kr.wifireminder.data.source.local.entity.TaskEntity
import com.hks.kr.wifireminder.domain.entity.Task
import com.hks.kr.wifireminder.vo.Result
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun collectTasks(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<List<Task>>>

    fun collectTask(
        taskId: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Result<Task>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>>

    suspend fun getTaskByImportance(): Result<List<Task>>

    suspend fun saveTask(task: TaskEntity)

    suspend fun deleteAllTasks()

    suspend fun deleteTaskByEndDate(endDate: String)

    suspend fun getCategoryCount(categoryTitle: String): Int
}