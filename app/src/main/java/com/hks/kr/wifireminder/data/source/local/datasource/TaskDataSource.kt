package com.hks.kr.wifireminder.data.source.local.datasource

import com.hks.kr.wifireminder.vo.Result
import com.hks.kr.wifireminder.data.source.local.entity.TaskEntity
import com.hks.kr.wifireminder.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    /**
     * Wraps and returns a specific task in flow
     */
    fun collectTask(taskId: String): Flow<Result<Task>> // 특정 Task 1개를 Flow로 Wrapping해서 반환하는 함수

    /**
     * Return all tasks by wrapping in flow
     */
    fun collectTasks(): Flow<Result<List<Task>>> // task 전부를 Flow로 Wrapping해서 반환하는 함수

    /**
     * Return all tasks
     */
    suspend fun getTasks(): Result<List<Task>>

    /**
     * Return specific task
     */
    suspend fun getTask(taskId: String): Result<Task>

    /**
     * Return tasks by category
     */
    suspend fun getTaskByCategory(categoryTitle: String): Result<List<Task>>

    /**
     * Return tasks by importance
     */
    suspend fun getTaskByImportance(): Result<List<Task>>

    /**
     * Save specific task
     */
    suspend fun saveTask(task: TaskEntity)

    /**
     * Delete All tasks
     */
    suspend fun deleteAllTasks()

    /**
     * Delete task by endDate
     */
    suspend fun deleteTaskByEndDate(endDate: String)

    /**
     * Get category count
     */
    suspend fun getCategoryCount(categoryTitle: String): Int
}