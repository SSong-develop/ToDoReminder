package com.hks.kr.wifireminder.domain.repository

import com.hks.kr.wifireminder.api.local.entity.TaskDTO
import com.hks.kr.wifireminder.domain.entity.TaskEntity

interface TaskRepository {
    suspend fun insertTask(taskDto: TaskDTO)

    suspend fun deleteTask(taskName: String)

    suspend fun fetchAllTaskSortByImportance(): List<TaskEntity>

    suspend fun deleteAllTask()

    suspend fun getCategoryCount(categoryName : String) : Int
}